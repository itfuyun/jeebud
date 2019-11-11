package com.jeebud.module.upms.controller;

import com.jeebud.core.log.OpTypeEnum;
import com.jeebud.core.log.Operation;
import com.jeebud.core.shiro.util.ShiroUtils;
import com.jeebud.core.web.RestEntity;
import com.jeebud.module.upms.model.entity.User;
import com.jeebud.module.upms.model.param.user.*;
import com.jeebud.module.upms.service.UserService;
import com.jeebud.module.upms.model.vo.MenuVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Controller
@RequestMapping("${jeebud.sys.serverCtx}/sys/user")
public class UserController {
    /**
     * 模板前缀
     */
    private static final String TPL_PATH = "admin/modules/sys/user";
    @Autowired
    UserService userService;

    /**
     * 列表页面
     *
     * @return
     */
    @RequiresPermissions("p:sys:user:list")
    @GetMapping("/pageList")
    public String pageList() {
        return TPL_PATH + "/list";
    }

    /**
     * 修改密码页面
     *
     * @return
     */
    @GetMapping("/updatePwd")
    public String pageUpdatePwd() {
        return TPL_PATH + "/updatePwd";
    }

    /**
     * 修改个人信息
     *
     * @param model
     * @return
     */
    @GetMapping("/userSetting")
    public String setting(Model model) {
        User user = userService.findById(ShiroUtils.getUserId());
        model.addAttribute("user", user);
        return TPL_PATH + "/userSetting";
    }

    /**
     * 用户列表
     *
     * @param query
     * @return
     */
    @RequiresPermissions("i:sys:user:list")
    @GetMapping("/list")
    @ResponseBody
    public RestEntity<Page<User>> userList(UserPageQuery query) {
        Page<User> userPage = userService.page(query);
        return RestEntity.ok().data(userPage);
    }

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    @Operation(module = "用户模块", opType = OpTypeEnum.CREATE, info = "新增用户")
    @RequiresPermissions("i:sys:user:add")
    @PostMapping("/add")
    @ResponseBody
    public RestEntity<String> add(@Validated User user) {
        userService.insert(user);
        return RestEntity.ok();
    }

    /**
     * 修改用户
     *
     * @param user
     * @return
     */
    @Operation(module = "用户模块", opType = OpTypeEnum.UPDATE, info = "修改用户")
    @RequiresPermissions("i:sys:user:update")
    @PostMapping("/update")
    @ResponseBody
    public RestEntity<String> update(@Validated User user) {
        userService.update(user);
        return RestEntity.ok();
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @Operation(module = "用户模块", opType = OpTypeEnum.DELETE, info = "删除用户")
    @RequiresPermissions("i:sys:user:delete")
    @PostMapping("/delete")
    @ResponseBody
    public RestEntity<String> delete(Long id) {
        userService.delete(id);
        return RestEntity.ok();
    }

    /**
     * 修改密码
     *
     * @param form
     * @return
     */
    @Operation(module = "用户模块", opType = OpTypeEnum.UPDATE, info = "修改密码")
    @PostMapping("/updatePwd")
    @ResponseBody
    public RestEntity updatePwd(@Validated UpdatePwdForm form) {
        form.setId(ShiroUtils.getUserId());
        userService.updatePwd(form);
        return RestEntity.ok();
    }

    /**
     * 锁定/解锁用户
     *
     * @param form
     * @return
     */
    @Operation(module = "用户模块", opType = OpTypeEnum.UPDATE, info = "锁定/解锁用户")
    @RequiresPermissions("i:sys:user:lock")
    @PostMapping("/updateStatus")
    @ResponseBody
    public RestEntity updateStatus(UpdateStatusForm form) {
        userService.updateStatus(form);
        return RestEntity.ok();
    }

    /**
     * 重置密码
     *
     * @param form
     * @return
     */
    @Operation(module = "用户模块", opType = OpTypeEnum.UPDATE, info = "重置密码")
    @RequiresPermissions("i:sys:user:resetPwd")
    @PostMapping("/resetPwd")
    @ResponseBody
    public RestEntity resetPwd(@Validated ResetPwdForm form) {
        userService.resetPwt(form);
        return RestEntity.ok();
    }

    /**
     * 获取菜单
     *
     * @return
     */
    @GetMapping("/getUserMenu")
    @ResponseBody
    public RestEntity<List<MenuVO>> getUserMenu() {
        List<MenuVO> menuList = userService.getUserMenu(ShiroUtils.getUserId());
        return RestEntity.ok().data(menuList);
    }

    /**
     * 修改用户自身基本信息
     *
     * @param form
     * @return
     */
    @Operation(module = "用户模块", opType = OpTypeEnum.UPDATE, info = "修改基本信息")
    @PostMapping("/updateUserInfo")
    @ResponseBody
    public RestEntity updateUserInfo(@Validated UpdateUserInfoForm form) {
        form.setId(ShiroUtils.getUserId());
        userService.updateUserInfo(form);
        return RestEntity.ok();
    }
}
