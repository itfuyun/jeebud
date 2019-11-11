package com.jeebud.module.upms.repository;

import com.jeebud.core.data.jpa.BaseRepository;
import com.jeebud.module.upms.model.entity.Role;
import org.springframework.stereotype.Repository;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Repository
public interface RoleRepository extends BaseRepository<Role, Long> {
    /**
     * 根据角色名查询
     *
     * @param roleName
     * @return
     */
    Role findByRoleName(String roleName);
}
