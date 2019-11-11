package com.jeebud.module.upms.repository;

import com.jeebud.core.data.jpa.BaseRepository;
import com.jeebud.module.upms.model.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Repository
public interface UserRepository extends BaseRepository<User, Long> {
    /**
     * 根据用户名查询
     *
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 查询roleId下的用户
     *
     * @param roleId
     * @return
     */
    List<User> findByRoleId(Long roleId);
}
