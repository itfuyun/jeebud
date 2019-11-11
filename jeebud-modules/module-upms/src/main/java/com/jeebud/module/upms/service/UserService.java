package com.jeebud.module.upms.service;

import com.jeebud.core.data.jpa.domain.PageQuery;
import com.jeebud.module.upms.model.entity.User;
import com.jeebud.module.upms.model.param.user.ResetPwdForm;
import com.jeebud.module.upms.model.param.user.UpdatePwdForm;
import com.jeebud.module.upms.model.param.user.UpdateStatusForm;
import com.jeebud.module.upms.model.param.user.UpdateUserInfoForm;
import com.jeebud.module.upms.model.vo.MenuVO;
import org.springframework.data.domain.Page;

import java.util.*;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
public interface UserService {

    /**
     * 获取用户列表
     *
     * @param query
     * @return
     */
    Page<User> page(PageQuery query);

    /**
     * 插入用户
     *
     * @param user
     * @return
     */
    Long insert(User user);

    /**
     * 修改用户
     *
     * @param user
     */
    void update(User user);

    /**
     * 删除用户
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 修改密码
     *
     * @param form
     */
    void updatePwd(UpdatePwdForm form);

    /**
     * 修改用户状态
     *
     * @param form
     */
    void updateStatus(UpdateStatusForm form);

    /**
     * 重置密码
     *
     * @param form
     */
    void resetPwt(ResetPwdForm form);

    /**
     * 获取用户菜单
     *
     * @param id 用户ID
     * @return
     */
    List<MenuVO> getUserMenu(Long id);

    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    User findById(Long id);

    /**
     * 修改用户部分信息
     *
     * @param form
     */
    void updateUserInfo(UpdateUserInfoForm form);

}
