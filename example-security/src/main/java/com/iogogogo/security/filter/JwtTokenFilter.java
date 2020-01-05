package com.iogogogo.security.filter;

import com.iogogogo.security.configure.handler.JwtTokenHandler;
import com.iogogogo.security.model.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by tao.zeng on 2019-03-25.
 */
@Slf4j
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String authTokenStart;

    @Autowired
    private JwtTokenHandler jwtTokenUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authToken = request.getHeader(this.tokenHeader);
        if (StringUtils.isNotEmpty(authToken) && authToken.startsWith(authTokenStart)) {
            authToken = authToken.substring(authTokenStart.length());
            log.info("请求" + request.getRequestURI() + "携带的token值：" + authToken);
            // 如果在token过期之前触发接口,我们更新token过期时间，token值不变只更新过期时间
            // String refreshToken = jwtTokenUtil.refreshToken(authToken);
            // 获取token生成时间
            Date createTokenDate = jwtTokenUtil.getCreatedDateFromToken(authToken);
            log.info("createTokenDate: " + createTokenDate);

        } else {
            authToken = null;
        }
        String username = jwtTokenUtil.getUsernameFromToken(authToken);
        log.info("JwtAuthenticationTokenFilter[doFilterInternal] checking authentication " + username);

        if (jwtTokenUtil.containToken(username, authToken) && username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            SecurityUser userDetail = jwtTokenUtil.getUserFromToken(authToken);
            if (jwtTokenUtil.validateToken(authToken, userDetail)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                log.info(String.format("Authenticated userDetail %s, setting security context", username));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } else {
            log.info("getAuthentication is null");
        }
        chain.doFilter(request, response);
    }
}