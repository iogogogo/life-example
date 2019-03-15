package com.iogogogo.vertica.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iogogogo.vertica.entity.User;
import com.iogogogo.vertica.mapper.UserMapper;
import com.iogogogo.vertica.service.UserService;
import org.springframework.stereotype.Service;

/**
 * Created by tao.zeng on 2019-03-15.
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
