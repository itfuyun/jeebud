package com.jeebud.module.upms.service;

import com.jeebud.core.data.jpa.domain.Query;
import com.jeebud.module.upms.model.entity.Permission;
import com.jeebud.module.upms.model.vo.TreeVO;

import java.util.List;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun @ gmail.com)
 */
public interface PermissionService {

    /**
     * 获取权限列表
     *
     * @param query
     * @return
     */
    List<Permission> listAll(Query query);

    /**
     * 插入权限
     *
     * @param permission
     * @return
     */
    Long insert(Permission permission);

    /**
     * 修改权限
     *
     * @param permission
     */
    void update(Permission permission);

    /**
     * 删除权限
     *
     * @param id
     */

    void delete(Long id);

    /**
     * 根据角色获取权限
     *
     * @param roleId
     * @return
     */
    List<Permission> getPermissionByRoleId(Long roleId);

    /**
     * 获取所有权限
     *
     * @return
     */
    List<Permission> getPermissionAll();

    /**
     * 获取权限树
     *
     * @return
     */
    List<TreeVO> permissionTree();

    /**
     * 根据ID查询权限
     *
     * @param id
     * @return
     */
    Permission findById(Long id);
}
