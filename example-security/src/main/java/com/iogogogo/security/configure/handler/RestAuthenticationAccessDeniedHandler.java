package com.iogogogo.security.configure.handler;

import com.iogogogo.security.util.ResourcesUtils;
import com.iogogogo.security.common.ResponseWrapper;
import com.iogogogo.security.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限不足处理类 403
 * <p>
 * Created by tao.zeng on 2019-03-25.
 */
@Slf4j
@Component("RestAuthenticationAccessDeniedHandler")
public class RestAuthenticationAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        StringBuilder msg = new StringBuilder("请求: ");
        msg.append(request.getRequestURI()).append(" 权限不足，无法访问系统资源.");
        log.info(msg.toString());

        ResponseWrapper<Object> wrapper = ResponseWrapper.builder().code(ResultCode.FORBIDDEN.getCode()).message(ResultCode.FORBIDDEN.getMessage()).build();
        ResourcesUtils.writer(wrapper, request, response);
    }
}