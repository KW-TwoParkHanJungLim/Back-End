package com.awexomeray.TwoParkHanJungLim.config.security;

import com.awexomeray.TwoParkHanJungLim.entity.UserEntity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class PrincipalDetails implements UserDetails {

    private final UserEntity userEntity;

    public PrincipalDetails(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    // 해당 유저의 권한을 리턴한다.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        class Inner implements GrantedAuthority{
            public String role;

            public void setRole(String role) {
                this.role = role;
            }

            @Override
            public String getAuthority() {
                return this.role;
            }
        }

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        Inner inner = new Inner();
        if(this.userEntity.getStatus().equals("admin")){
            inner.setRole("ROLE_ADMIN");
            authorities.add(inner);
        }
        else {
            inner.setRole("ROLE_USER");
            authorities.add(inner);
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getId();
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
