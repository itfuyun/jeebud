package com.jeebud.module.upms.controller;

import com.jeebud.core.web.RestEntity;
import com.jeebud.core.log.OpTypeEnum;
import com.jeebud.core.log.Operation;
import com.jeebud.module.upms.model.entity.Role;
import com.jeebud.module.upms.model.param.role.PermissionForm;
import com.jeebud.module.upms.model.param.role.RolePageQuery;
import com.jeebud.module.upms.service.RoleService;
import com.jeebud.module.upms.model.vo.TreeVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
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
@RequestMapping("${jeebud.sys.serverCtx}/sys/role")
public class RoleController {
    /**
     * 模板前缀
     */
    private static final String TPL_PATH = "admin/modules/sys/role";
    @Autowired
    RoleService roleService;

    /**
     * 列表页面
     *
     * @return
     */
    @RequiresPermissions("p:sys:role:list")
    @GetMapping("/pageList")
    public String pageList() {
        return TPL_PATH + "/list";
    }

    /**
     * 获取角色列表
     *
     * @param query
     * @return
     */
    @RequiresPermissions("i:sys:role:list")
    @GetMapping("/list")
    @ResponseBody
    public RestEntity<Page<Role>> roleList(RolePageQuery query) {
        Page<Role> rolePage = roleService.page(query);
        return RestEntity.ok().data(rolePage);
    }

    /**
     * 新增角色
     *
     * @param role
     * @return
     */
    @Operation(module = "角色模块", opType = OpTypeEnum.CREATE, info = "新增角色")
    @RequiresPermissions("i:sys:role:add")
    @PostMapping("/add")
    @ResponseBody
    public RestEntity<String> add(@Validated Role role) {
        roleService.insert(role);
        return RestEntity.ok();
    }

    /**
     * 修改角色
     *
     * @param role
     * @return
     */
    @Operation(module = "角色模块", opType = OpTypeEnum.UPDATE, info = "修改角色")
    @RequiresPermissions("i:sys:role:update")
    @PostMapping("/update")
    @ResponseBody
    public RestEntity<String> update(@Validated Role role) {
        roleService.update(role);
        return RestEntity.ok();
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    @Operation(module = "角色模块", opType = OpTypeEnum.DELETE, info = "删除角色")
    @RequiresPermissions("i:sys:role:delete")
    @PostMapping("/delete")
    @ResponseBody
    public RestEntity<String> delete(Long id) {
        roleService.delete(id);
        return RestEntity.ok();
    }

    /**
     * 获取授权树
     *
     * @param id
     * @return
     */
    @RequiresPermissions("i:sys:role:permission")
    @GetMapping("/permissionTree")
    @ResponseBody
    public RestEntity<List<TreeVO>> permissionTree(Long id) {
        List<TreeVO> treeList = roleService.permissionTree(id);
        return RestEntity.ok().data(treeList);
    }

    /**
     * 授权
     *
     * @param form
     * @return
     */
    @Operation(module = "角色模块", opType = OpTypeEnum.UPDATE, info = "角色授权")
    @RequiresPermissions("i:sys:role:permission")
    @PostMapping("/permission")
    @ResponseBody
    public RestEntity permission(@Validated PermissionForm form) {
        roleService.permission(form);
        return RestEntity.ok();
    }

    /**
     * 角色下拉选择数据
     *
     * @return
     */
    @GetMapping("/selectList")
    @ResponseBody
    public RestEntity<List<Role>> selectList() {
        List<Role> roleList = roleService.listAll();
        return RestEntity.ok().data(roleList);
    }
}
