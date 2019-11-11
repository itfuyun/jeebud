package com.jeebud.module.upms.service.impl;

import com.jeebud.common.exception.JeebudException;
import com.jeebud.common.util.ObjectUtils;
import com.jeebud.core.data.jpa.domain.PageQuery;
import com.jeebud.core.shiro.util.ShiroUtils;
import com.jeebud.module.upms.model.entity.Permission;
import com.jeebud.module.upms.model.entity.Role;
import com.jeebud.module.upms.model.entity.RolePermission;
import com.jeebud.module.upms.model.entity.User;
import com.jeebud.module.upms.model.param.role.PermissionForm;
import com.jeebud.module.upms.model.vo.TreeVO;
import com.jeebud.module.upms.repository.PermissionRepository;
import com.jeebud.module.upms.repository.RolePermissionRepository;
import com.jeebud.module.upms.repository.RoleRepository;
import com.jeebud.module.upms.repository.UserRepository;
import com.jeebud.module.upms.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    RolePermissionRepository rolePermissionRepository;
    @Autowired
    PermissionRepository permissionRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public Page<Role> page(PageQuery query) {
        return roleRepository.search(query);
    }

    @Override
    public List<Role> listAll() {
        return roleRepository.findAll();
    }

    @Override
    public Long insert(Role role) {
        Role oRole = roleRepository.findByRoleName(role.getRoleName());
        if (ObjectUtils.isNotNull(oRole)) {
            throw new JeebudException("角色名已存在");
        }
        role.setId(null);
        role.setCreateBy(ShiroUtils.getUsername());
        role.setCreateTime(new Date());
        roleRepository.save(role);
        return role.getId();
    }

    @Override
    public void update(Role role) {
        if (ObjectUtils.isNull(role.getId())) {
            throw new JeebudException("角色ID不能为空");
        }
        Role oRole = roleRepository.findByRoleName(role.getRoleName());
        if (ObjectUtils.isNotNull(oRole) && !oRole.getId().equals(role.getId())) {
            throw new JeebudException("角色名已存在");
        }
        role.setUpdateBy(ShiroUtils.getUsername());
        role.setUpdateTime(new Date());
        roleRepository.save(role);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void delete(Long id) {
        List<User> userList = userRepository.findByRoleId(id);
        if (!ObjectUtils.isNullList(userList)) {
            throw new JeebudException("角色下存在用户");
        }
        //删除关联
        rolePermissionRepository.deleteByRoleId(id);
        roleRepository.deleteById(id);
    }

    @Override
    public List<TreeVO> permissionTree(Long roleId) {
        Set<Long> ids = rolePermissionRepository.findByRoleId(roleId)
                .stream()
                .map(RolePermission::getPermissionId)
                .collect(Collectors.toSet());
        List<TreeVO> treeList = permissionRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Permission::getSortNum).reversed())
                .map(permission -> {
                    TreeVO treeVO = new TreeVO();
                    treeVO.setId(permission.getId());
                    treeVO.setPid(permission.getPid());
                    treeVO.setName(permission.getName());
                    treeVO.setOpen(true);
                    treeVO.setChecked(ids.contains(permission.getId()));
                    return treeVO;
                }).collect(Collectors.toList());
        return treeList;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void permission(PermissionForm form) {
        rolePermissionRepository.deleteByRoleId(form.getId());
        List<RolePermission> rolePermissionList =form.getPermissionIdList().stream()
                .map(roleId -> {
                    RolePermission rolePermission = new RolePermission();
                    rolePermission.setRoleId(form.getId());
                    rolePermission.setPermissionId(roleId);
                    return rolePermission;
                })
                .collect(Collectors.toList());
        rolePermissionRepository.saveAll(rolePermissionList);
    }
}
