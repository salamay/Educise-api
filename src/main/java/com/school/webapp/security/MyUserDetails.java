package com.school.webapp.security;

import com.school.webapp.Repository.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MyUserDetails  implements UserDetails {
    private User user;
    private String username;
    private String password;
    private List<SimpleGrantedAuthority> authorities;

    public MyUserDetails (User user) {
        this.user=user;
        this.username=user.getUsername();
        this.password=user.getPassword();
        this.authorities= Arrays.stream(user.getRole().split(",")).map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
