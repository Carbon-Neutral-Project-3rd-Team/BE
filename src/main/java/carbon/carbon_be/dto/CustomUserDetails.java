package com.example.testsecurity.dto;

import com.example.testsecurity.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private UserEntity userEntity;//customUserDetails에서 userEntity값을 받기 위함

    public CustomUserDetails(UserEntity userEntity) {
        this.userEntity=userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {//사용자의 권한(role 값) return
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {

                return userEntity.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }


    //이 부분 구현 -> data base table에 만료 여부 등 체크하는 field 삽입 후 값 가져옴
    @Override
    public boolean isAccountNonExpired() {//만료 확인
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {//잠겨있는지
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {//사용가능한지
        return true;
    }
}
