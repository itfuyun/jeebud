package com.jeebud.module.upms.service;

import com.jeebud.core.data.jpa.domain.PageQuery;
import com.jeebud.module.upms.model.entity.Role;
import com.jeebud.module.upms.model.param.role.PermissionForm;
import com.jeebud.module.upms.model.vo.TreeVO;
import org.springframework.data.domain.Page;

import java.util.*;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
public interface RoleService {

    /**
     * 获取角色列表
     *
     * @param query
     * @return
     */
    Page<Role> page(PageQuery query);

    /**
     * 获取所有角色
     *
     * @return
     */
    List<Role> listAll();

    /**
     * 插入角色
     *
     * @param role
     * @return
     */
    Long insert(Role role);

    /**
     * 修改角色
     *
     * @param role
     */
    void update(Role role);

    /**
     * 删除角色
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 根据角色ID获取权限树
     *
     * @param roleId
     * @return
     */
    List<TreeVO> permissionTree(Long roleId);

    /**
     * 角色授权
     *
     * @param form
     */
    void permission(PermissionForm form);
}
