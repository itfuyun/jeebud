package com.jeebud.module.upms.controller;

import com.jeebud.common.util.ObjectUtils;
import com.jeebud.core.web.RestEntity;
import com.jeebud.core.data.jpa.domain.Query;
import com.jeebud.core.log.OpTypeEnum;
import com.jeebud.core.log.Operation;
import com.jeebud.module.upms.model.entity.Permission;
import com.jeebud.module.upms.service.PermissionService;
import com.jeebud.module.upms.model.vo.TreeVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("${jeebud.sys.serverCtx}/sys/permission")
public class PermissionController {
    /**
     * 模板前缀
     */
    private static final String TPL_PATH = "admin/modules/sys/permission";
    @Autowired
    PermissionService permissionService;

    /**
     * 列表页面
     *
     * @return
     */
    @RequiresPermissions("p:sys:permission:list")
    @GetMapping("/pageList")
    public String pageList() {
        return TPL_PATH + "/list";
    }

    /**
     * 获取权限列表
     *
     * @param query
     * @return
     */
    @RequiresPermissions("i:sys:permission:list")
    @GetMapping("/list")
    @ResponseBody
    public RestEntity<List<Permission>> permissionList(Query query) {
        List<Permission> permissionList = permissionService.listAll(query);
        return RestEntity.ok().data(permissionList);
    }

    /**
     * modal页面
     *
     * @return
     */
    @GetMapping("/modal")
    public String modal(Long id, Model model) {
        Permission parent = new Permission();
        if(ObjectUtils.isNotNull(id)){
            if(id.equals(0L)){
                parent.setPid(-1L);
                parent.setId(0L);
                parent.setName("顶级");
            }else{
                parent = permissionService.findById(id);
            }
        }
        model.addAttribute("parent",parent);
        return TPL_PATH + "/modal";
    }

    /**
     * 新增权限
     *
     * @param permission
     * @return
     */
    @Operation(module = "权限模块", opType = OpTypeEnum.CREATE, info = "新增权限")
    @RequiresPermissions("i:sys:permission:add")
    @PostMapping("/add")
    @ResponseBody
    public RestEntity<String> add(@Validated Permission permission) {
        permissionService.insert(permission);
        return RestEntity.ok();
    }

    /**
     * 修改权限
     *
     * @param permission
     * @return
     */
    @Operation(module = "权限模块", opType = OpTypeEnum.UPDATE, info = "修改权限")
    @RequiresPermissions("i:sys:permission:update")
    @PostMapping("/update")
    @ResponseBody
    public RestEntity<String> update(@Validated Permission permission) {
        permissionService.update(permission);
        return RestEntity.ok();
    }

    /**
     * 删除权限
     *
     * @param id
     * @return
     */
    @Operation(module = "权限模块", opType = OpTypeEnum.DELETE, info = "删除权限")
    @RequiresPermissions("i:sys:permission:delete")
    @PostMapping("/delete")
    @ResponseBody
    public RestEntity<String> delete(Long id) {
        permissionService.delete(id);
        return RestEntity.ok();
    }

    /**
     * 权限下拉选择数据
     *
     * @return
     */
    @GetMapping("/selectList")
    @ResponseBody
    public RestEntity<List<TreeVO>> selectList(){
        List<TreeVO> permissionTree = permissionService.permissionTree();
        return RestEntity.ok().data(permissionTree);
    }
}
