package com.agungwicaksono.co.id.auth.security.implement;

import com.agungwicaksono.co.id.auth.model.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@AllArgsConstructor
@Builder
public class UserDetailsImplement implements UserDetails {
    private String id;
    private String userName;
    private String password;
    private String email;
    private String verificationCode;
    private Boolean isEnable;

    public static UserDetailsImplement build(Customer input){
        return UserDetailsImplement.builder().id(input.getId())
                .password(input.getPassword())
                .userName(input.getUserName())
                .isEnable(input.getIsEnable())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return userName;
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
        return isEnable;
    }
}
