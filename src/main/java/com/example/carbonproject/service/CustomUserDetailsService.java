package com.example.carbonproject.service;

import com.example.carbonproject.dto.CustomUserDetails;
import com.example.carbonproject.entity.UserEntity;
import com.example.carbonproject.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    //db에 접근하기 위한 repository
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        //username으로 DB에서 조회
        UserEntity userEntity = userRepository.findByUsername(username);

        if(userEntity != null){
            return new CustomUserDetails(userEntity);
        }
        throw new UsernameNotFoundException("해당 사용자 ("+username+")를 찾을 수 없습니다.");
    }

}
