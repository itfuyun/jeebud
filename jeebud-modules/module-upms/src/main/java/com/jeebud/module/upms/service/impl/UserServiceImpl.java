package com.jeebud.module.upms.service.impl;

import com.jeebud.common.constant.PermissionEnum;
import com.jeebud.common.exception.JeebudException;
import com.jeebud.common.util.Md5Utils;
import com.jeebud.common.util.ObjectUtils;
import com.jeebud.common.util.StringUtils;
import com.jeebud.core.data.jpa.domain.PageQuery;
import com.jeebud.core.shiro.util.ShiroUtils;
import com.jeebud.module.upms.model.entity.Permission;
import com.jeebud.module.upms.model.entity.RolePermission;
import com.jeebud.module.upms.model.entity.User;
import com.jeebud.module.upms.model.param.user.ResetPwdForm;
import com.jeebud.module.upms.model.param.user.UpdatePwdForm;
import com.jeebud.module.upms.model.param.user.UpdateStatusForm;
import com.jeebud.module.upms.model.param.user.UpdateUserInfoForm;
import com.jeebud.module.upms.model.vo.MenuVO;
import com.jeebud.module.upms.repository.PermissionRepository;
import com.jeebud.module.upms.repository.RolePermissionRepository;
import com.jeebud.module.upms.repository.UserRepository;
import com.jeebud.module.upms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PermissionRepository permissionRepository;
    @Autowired
    RolePermissionRepository rolePermissionRepository;
    @Value("${jeebud.sys.serverCtx}")
    private String serverCtx;

    @Override
    public Page<User> page(PageQuery query) {
        return userRepository.search(query);
    }

    @Override
    public Long insert(User user) {
        user.setId(null);
        User oUser = userRepository.findByUsername(user.getUsername());
        if (ObjectUtils.isNotNull(oUser)) {
            throw new JeebudException("用户名已存在");
        }
        user.setAvatar("");
        //非超级管理员
        user.setAdminFlag(0);
        //正常
        user.setStatus(0);
        user.setCreateBy(ShiroUtils.getUsername());
        user.setCreateTime(new Date());
        userRepository.save(user);
        return user.getId();
    }

    @Override
    public void update(User user) {
        if (ObjectUtils.isNull(user.getId())) {
            throw new JeebudException("用户ID不能为空");
        }
        User oUser = userRepository.findByUsername(user.getUsername());
        if (ObjectUtils.isNotNull(oUser) && !oUser.getId().equals(user.getId())) {
            throw new JeebudException("用户名已存在");
        }
        user.setUpdateBy(ShiroUtils.getUsername());
        user.setUpdateTime(new Date());
        user.setPassword(oUser.getPassword());
        user.setStatus(oUser.getStatus());
        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (ObjectUtils.isNull(user)) {
            throw new JeebudException("用户不存在");
        }
        if (user.getAdminFlag() == 1) {
            throw new JeebudException("超级管理员不能删除");
        }
        Long userId = ShiroUtils.getUserId();
        if (userId.equals(id)) {
            throw new JeebudException("不能删除自己");
        }
        userRepository.deleteById(id);
    }

    @Override
    public void updatePwd(UpdatePwdForm form) {
        User user = userRepository.findById(form.getId())
                .orElseThrow(()-> new JeebudException("用户不存在"));
        if (user.getStatus() == 1) {
            throw new JeebudException("用户被锁定");
        }
        if (!Md5Utils.encrypt(form.getOldPwd()).equals(user.getPassword())) {
            throw new JeebudException("原密码错误");
        }
        user.setUpdateBy(ShiroUtils.getUsername());
        user.setUpdateTime(new Date());
        user.setPassword(Md5Utils.encrypt(form.getNewPwd()));
        userRepository.save(user);
    }

    @Override
    public void updateStatus(UpdateStatusForm form) {
        User user = userRepository.findById(form.getId())
                .orElseThrow(()->new JeebudException("用户不存在"));
        if (user.getAdminFlag() == 1) {
            throw new JeebudException("超级管理员不能进行此项修改");
        }
        user.setUpdateBy(ShiroUtils.getUsername());
        user.setUpdateTime(new Date());
        user.setStatus(form.getStatus());
        userRepository.save(user);
    }

    @Override
    public void resetPwt(ResetPwdForm form) {
        User user = userRepository.findById(form.getId())
                .orElseThrow(()->new JeebudException("用户不存在"));
        if (user.getAdminFlag() == 1) {
            throw new JeebudException("超级管理员不能进行此项修改");
        }
        user.setUpdateBy(ShiroUtils.getUsername());
        user.setUpdateTime(new Date());
        user.setPassword(Md5Utils.encrypt(form.getPassword()));
        userRepository.save(user);
    }

    @Override
    public List<MenuVO> getUserMenu(Long id) {
        User user = userRepository.findById(ShiroUtils.getUserId())
                .orElseThrow(()->new JeebudException("用户不存在"));
        List<Permission> permissionList;
        if (user.getAdminFlag() == 1) {
            //超级管理员
            permissionList = permissionRepository.findByType(PermissionEnum.MENU.getCode());
        } else {
            //普通管理员
            List<RolePermission> rolePermissionList = rolePermissionRepository.findByRoleId(user.getRoleId());
            Set<Long> ids = new HashSet<>();
            rolePermissionList.forEach(rolePermission -> {
                ids.add(rolePermission.getPermissionId());
            });
            permissionList = permissionRepository.findByTypeAndIdIn(PermissionEnum.MENU.getCode(), ids);
        }
        //按排序码降序排序
        permissionList.sort(Comparator.comparing(Permission::getSortNum).reversed());
        return tree(permissionList);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void updateUserInfo(UpdateUserInfoForm form) {
        User user = userRepository.findById(form.getId())
                .orElseThrow(()-> new JeebudException("用户不存在"));
        user.setAvatar(StringUtils.trim(form.getAvatar()));
        user.setName(StringUtils.trim(form.getName()));
        user.setMobile(StringUtils.trim(form.getMobile()));
        user.setEmail(StringUtils.trim(form.getEmail()));
        user.setProfile(StringUtils.trim(form.getProfile()));
        userRepository.save(user);
    }

    private List<MenuVO> tree(List<Permission> permissionList) {
        List<MenuVO> childList = new ArrayList<>();
        //根节点
        List<MenuVO> rootList = new ArrayList<>();
        permissionList.forEach(permission -> {
            MenuVO menuVO = convert(permission);
            if (permission.getPid() == 0) {
                //父节点是0的，为根节点。
                rootList.add(menuVO);
            } else {
                //所有非顶级节点放到集合
                childList.add(menuVO);
            }
        });
        //为根菜单设置子菜单，getClild递归调用
        rootList.forEach(menuVO -> {
            List<MenuVO> childTreeList = getChild(menuVO.getId(), childList);
            //给根节点设置子节点
            menuVO.setSubMenus(childTreeList);
        });
        return rootList;
    }

    private MenuVO convert(Permission permission) {
        String url = permission.getUrl();
        if(ObjectUtils.isNotNull(url)&&url.startsWith("/")){
            url = serverCtx + url;
        }
        MenuVO menuVO = new MenuVO();
        menuVO.setName(permission.getName());
        menuVO.setIcon(permission.getIcon());
        menuVO.setUrl(url);
        menuVO.setId(permission.getId());
        menuVO.setPid(permission.getPid());
        return menuVO;
    }

    private List<MenuVO> getChild(Long id, List<MenuVO> allList) {
        //子菜单
        List<MenuVO> childList = new ArrayList<>();
        //剩余的集合
        List<MenuVO> remainList = new ArrayList<>();
        allList.forEach(menuVO -> {
            // 遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
            if (menuVO.getPid().equals(id)) {
                childList.add(menuVO);
            } else {
                remainList.add(menuVO);
            }
        });
        //递归
        childList.forEach(menuVO -> menuVO.setSubMenus(getChild(menuVO.getId(), remainList)));
        //如果节点下没有子节点，返回一个空List（递归退出）
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }
}
