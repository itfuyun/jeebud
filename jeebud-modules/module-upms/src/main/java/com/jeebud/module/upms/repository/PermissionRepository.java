package com.jeebud.module.upms.repository;

import com.jeebud.core.data.jpa.BaseRepository;
import com.jeebud.module.upms.model.entity.Permission;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Repository
public interface PermissionRepository extends BaseRepository<Permission, Long> {
    /**
     * 根据权限编码查询权限
     *
     * @param code
     * @return
     */
    Permission findByCode(String code);

    /**
     * 根据权限类型查询
     *
     * @param type
     * @return
     */
    List<Permission> findByType(Integer type);

    /**
     * 根据权限类型和主键查询
     *
     * @param type
     * @param ids
     * @return
     */
    List<Permission> findByTypeAndIdIn(Integer type, Collection<Long> ids);

    /**
     * 根据权限pid查询
     *
     * @param pid
     * @return
     */
    List<Permission> findByPid(Long pid);
}
