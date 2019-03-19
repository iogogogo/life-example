package com.iogogogo.service.vertica.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iogogogo.entity.User;
import com.iogogogo.mapper.vertica.UserMapper;
import com.iogogogo.service.vertica.UserService;
import org.springframework.stereotype.Service;

/**
 * Created by tao.zeng on 2019-03-15.
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
