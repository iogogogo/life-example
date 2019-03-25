package com.iogogogo.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iogogogo.security.entity.Role;
import com.iogogogo.security.mapper.RoleMapper;
import com.iogogogo.security.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tao.zeng on 2019-03-25.
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> findRoleByUserId(Long userId) {
        return roleMapper.findRoleByUserId(userId);
    }
}
