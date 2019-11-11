package com.jeebud.module.upms.repository;

import com.jeebud.core.data.jpa.BaseRepository;
import com.jeebud.module.upms.model.entity.RolePermission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Repository
public interface RolePermissionRepository extends BaseRepository<RolePermission, Long> {
    /**
     * 根据角色ID查询关联
     *
     * @param roleId
     * @return
     */
    List<RolePermission> findByRoleId(Long roleId);

    /**
     * 根据角色ID删除关联
     *
     * @param roleId
     */
    void deleteByRoleId(Long roleId);

    /**
     * 根据权限ID删除关联
     *
     * @param permissionId
     */
    void deleteByPermissionId(Long permissionId);
}
