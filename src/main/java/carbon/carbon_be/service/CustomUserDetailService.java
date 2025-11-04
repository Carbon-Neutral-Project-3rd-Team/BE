package com.example.testsecurity.service;

import com.example.testsecurity.dto.CustomUserDetails;
import com.example.testsecurity.entity.UserEntity;
import com.example.testsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    //userrepository와 연결 - userDetails와 db에서 userName에 대한 데이터
    @Autowired
    private UserRepository userRepository;

    //userDetailService method 구현
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //db에서 가져온 userName 검증하는 메소드

        UserEntity userData = userRepository.findByUsername(username);//jpa 커스텀 메소드 - db에서 username찾는 메소드 생성 후 적용

        if(userData!=null){
            return new CustomUserDetails(userData);
        }

        throw new UsernameNotFoundException(username);
    }
}
