package com.iogogogo.security.model;

import com.iogogogo.security.entity.Role;
import com.iogogogo.security.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by tao.zeng on 2019-03-25.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SecurityUser extends User implements UserDetails {


    private long id;

    private String username;

    private String password;

    private List<Role> roles;

    private LocalDateTime lastPasswordResetTime;


    public SecurityUser(String username, String password, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    /**
     * 分配给用户的角色列表
     *
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(roles)) {
            roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        }
        return authorities;
    }

    /**
     * 账户是否未过期,过期无法验证
     *
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 指定用户是否解锁,锁定的用户无法进行身份验证
     *
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
     *
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否可用 ,禁用的用户不能身份验证
     *
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
