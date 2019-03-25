package com.iogogogo.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iogogogo.security.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tao.zeng on 2019-03-25.
 */
@Service
public interface RoleService extends IService<Role> {

    List<Role> findRoleByUserId(Long userId);
}
