package com.iogogogo.security.configure.handler;

import com.iogogogo.security.common.ResponseWrapper;
import com.iogogogo.security.common.ResultCode;
import com.iogogogo.security.util.ResourcesUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * 认证失败处理类 401
 * <p>
 * Created by tao.zeng on 2019-03-25.
 */
@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        log.info("请求访问: " + request.getRequestURI() + " 接口， 经jwt 认证失败，无法访问系统资源.");
        log.info(e.toString());
        ResponseWrapper<Object> wrapper;
        // 用户登录时身份认证未通过
        if (e instanceof BadCredentialsException) {
            log.info("用户登录时身份认证失败.");
            wrapper = ResponseWrapper.builder().code(ResultCode.UNAUTHORIZED.getCode()).message(ResultCode.UNAUTHORIZED.getMessage()).build();
        } else if (e instanceof InsufficientAuthenticationException) {
            log.info("缺少请求头参数,Authorization传递是token值所以参数是必须的.");
            wrapper = ResponseWrapper.builder().code(ResultCode.NO_TOKEN.getCode()).message(ResultCode.NO_TOKEN.getMessage()).build();
        } else if (e instanceof InternalAuthenticationServiceException) {
            wrapper = ResponseWrapper.builder().code(ResultCode.NO_ROLE_RESOURCES.getCode()).message(ResultCode.NO_ROLE_RESOURCES.getMessage()).build();
        } else {
            log.info("用户token无效.");
            wrapper = ResponseWrapper.builder().code(ResultCode.TOKEN_INVALID.getCode()).message(ResultCode.TOKEN_INVALID.getMessage()).build();
        }
        ResourcesUtils.writer(wrapper, request, response);
    }
}