package com.jeebud.module.upms.service.impl;

import com.jeebud.common.exception.JeebudException;
import com.jeebud.common.util.ObjectUtils;
import com.jeebud.common.util.StringUtils;
import com.jeebud.core.data.jpa.domain.Query;
import com.jeebud.core.shiro.util.ShiroUtils;
import com.jeebud.module.upms.model.entity.Permission;
import com.jeebud.module.upms.model.entity.RolePermission;
import com.jeebud.module.upms.model.vo.TreeVO;
import com.jeebud.module.upms.repository.PermissionRepository;
import com.jeebud.module.upms.repository.RolePermissionRepository;
import com.jeebud.module.upms.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @author Tanxh(itfuyun @ gmail.com)
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    PermissionRepository permissionRepository;
    @Autowired
    RolePermissionRepository rolePermissionRepository;

    @Override
    public List<Permission> listAll(Query query) {
        return permissionRepository.search(query);
    }

    @Override
    public Long insert(Permission permission) {
        String code = permission.getCode();
        if (StringUtils.isNotEmpty(code)) {
            Permission dbPermission = permissionRepository.findByCode(code);
            if (ObjectUtils.isNotNull(dbPermission)) {
                throw new JeebudException("权限编码已存在");
            }
        }
        permission.setId(null);
        permission.setCreateBy(ShiroUtils.getUsername());
        permission.setCreateTime(new Date());
        permissionRepository.save(permission);
        return permission.getId();
    }

    @Override
    public void update(Permission permission) {
        if (ObjectUtils.isNull(permission.getId())) {
            throw new JeebudException("用户ID不能为空");
        }
        String code = permission.getCode();
        if (StringUtils.isNotEmpty(code)) {
            Permission dbPermission = permissionRepository.findByCode(code);
            if (ObjectUtils.isNotNull(dbPermission) && !dbPermission.getId().equals(permission.getId())) {
                throw new JeebudException("权限编码已存在");
            }
        }
        permission.setUpdateBy(ShiroUtils.getUsername());
        permission.setUpdateTime(new Date());
        permissionRepository.save(permission);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void delete(Long id) {
        List<Permission> permissionList = permissionRepository.findByPid(id);
        if (!ObjectUtils.isNullList(permissionList)) {
            throw new JeebudException("权限下存在其他数据");
        }
        //删除关联表，保持数据清爽
        rolePermissionRepository.deleteByPermissionId(id);
        permissionRepository.deleteById(id);
    }

    @Override
    public List<Permission> getPermissionByRoleId(Long roleId) {
        Set<Long> ids = rolePermissionRepository.findByRoleId(roleId)
                .stream()
                .map(RolePermission::getPermissionId)
                .collect(Collectors.toSet());
        List<Permission> permissionList = permissionRepository.findAllById(ids);
        return permissionList;
    }

    @Override
    public List<Permission> getPermissionAll() {
        List<Permission> permissionList = permissionRepository.findAll();
        return permissionList;
    }

    @Override
    public List<TreeVO> permissionTree() {
        List<TreeVO> treeList = permissionRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Permission::getSortNum).reversed())
                .map(permission -> {
                    TreeVO treeVO = new TreeVO();
                    treeVO.setId(permission.getId());
                    treeVO.setPid(permission.getPid());
                    treeVO.setName(permission.getName());
                    treeVO.setOpen(false);
                    treeVO.setChecked(false);
                    return treeVO;
                })
                .collect(Collectors.toList());
        TreeVO treeVO = new TreeVO();
        treeVO.setId(0L);
        treeVO.setPid(-1L);
        treeVO.setName("顶级");
        treeVO.setOpen(true);
        treeVO.setChecked(false);
        treeList.add(treeVO);
        return treeList;
    }

    @Override
    public Permission findById(Long id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new JeebudException("父级权限未找到"));
        return permission;
    }
}
