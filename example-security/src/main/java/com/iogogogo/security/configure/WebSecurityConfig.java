package com.iogogogo.security.configure;

import com.iogogogo.security.configure.handler.JwtAuthenticationEntryPoint;
import com.iogogogo.security.entity.Role;
import com.iogogogo.security.entity.User;
import com.iogogogo.security.filter.JwtTokenFilter;
import com.iogogogo.security.model.SecurityUser;
import com.iogogogo.security.service.RoleService;
import com.iogogogo.security.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

/**
 * Created by tao.zeng on 2019-03-25.
 */
@Slf4j
@Configuration
@EnableWebSecurity
// 开启方法级的权限注解  性设置后控制器层的方法前的@PreAuthorize("hasRole('admin')") 注解才能起效
// @EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private JwtTokenFilter authenticationTokenFilter;


    /**
     * 解决 无法直接注入 AuthenticationManager
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    /**
     * 配置策略
     *
     * @param httpSecurity
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // 由于使用的是JWT，我们这里不需要csrf
                .csrf().disable()
                // 权限不足处理类
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler).and()
                // 认证失败处理类
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                // 对于登录login要允许匿名访问
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(HttpMethod.POST, "/login", "/register", "/favicon.ico").permitAll()
                // 访问/user 需要拥有admin权限
                .antMatchers("/user").hasAuthority("admin")
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();

        // 添加JWT filter
        httpSecurity.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // 禁用缓存
        httpSecurity.headers().cacheControl();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // 设置UserDetailsService
        auth.userDetailsService(userDetailsService())
                // 使用BCrypt进行密码的hash
                .passwordEncoder(passwordEncoder());
        auth.eraseCredentials(false);
    }

    /**
     * 装载BCrypt密码编码器 密码加密
     *
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 登陆身份认证
     *
     * @return
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Autowired
            private UserService userService;
            @Autowired
            private RoleService roleService;

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                log.info("登录用户：" + username);
                User user = userService.findByUserName(username);
                if (user == null) {
                    log.info("登录用户：" + username + " 不存在.");
                    throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
                }
                // 获取用户拥有的角色
                List<Role> roles = roleService.findRoleByUserId(user.getId());
                if (CollectionUtils.isNotEmpty(roles)) {
                    return new SecurityUser(username, user.getPassword(), roles);
                } else {
                    throw new RuntimeException("该用户没有权限访问");
                }
            }
        };
    }

}