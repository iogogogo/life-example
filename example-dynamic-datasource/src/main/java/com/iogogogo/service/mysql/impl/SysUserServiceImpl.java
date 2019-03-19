package com.iogogogo.service.mysql.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iogogogo.entity.SysUser;
import com.iogogogo.mapper.mysql.SysUserMapper;
import com.iogogogo.service.mysql.SysUserService;
import org.springframework.stereotype.Service;

/**
 * Created by tao.zeng on 2019-03-19.
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
}
