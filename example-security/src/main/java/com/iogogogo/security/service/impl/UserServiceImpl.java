package com.iogogogo.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iogogogo.security.common.ResponseWrapper;
import com.iogogogo.security.common.ResultCode;
import com.iogogogo.security.entity.User;
import com.iogogogo.security.mapper.UserMapper;
import com.iogogogo.security.model.SecurityUser;
import com.iogogogo.security.service.UserService;
import com.iogogogo.security.configure.handler.JwtTokenHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tao.zeng on 2019-03-25.
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenHandler jwtTokenUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    public User findByUserName(String name) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 查询条件
        queryWrapper.lambda().eq(User::getUsername, name);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public ResponseWrapper login(String username, String password) {
        // 用户验证
        final Authentication authentication = authenticate(username, password);
        // 存储认证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 生成token
        final SecurityUser userDetail = (SecurityUser) authentication.getPrincipal();
        final String token = jwtTokenUtil.generateAccessToken(userDetail);
        // 存储token
        jwtTokenUtil.putToken(username, token);
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("user", userDetail);
        return ResponseWrapper.builder().data(map).build();

    }

    @Override
    public ResponseWrapper register(User user) {

        final String username = user.getUsername();
        if (findByUserName(username) != null) {
            log.info("用户已存在");
            return null;
        }
        final String rawPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(rawPassword));

        int i = userMapper.insert(user);

        if (i > 0) {
            return ResponseWrapper.builder().code(ResultCode.SUCCESS.getCode()).message(ResultCode.SUCCESS.getMessage()).build();
        } else {
            return ResponseWrapper.builder().code(ResultCode.SERVER_ERROR.getCode()).message(ResultCode.SERVER_ERROR.getMessage()).build();
        }
    }

    @Override
    public SecurityUser getUserByToken(String token) {
        token = token.substring(tokenHead.length());
        return jwtTokenUtil.getUserFromToken(token);
    }

    private Authentication authenticate(String username, String password) {
        try {
            // 该方法会去调用userDetailsService.loadUserByUsername()去验证用户名和密码，
            // 如果正确，则存储该用户名密码到security 的 context中
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException | BadCredentialsException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
