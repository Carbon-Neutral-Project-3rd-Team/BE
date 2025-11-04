package com.example.testsecurity.repository;


import com.example.testsecurity.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    //무조건 interface형태로 생성 , jpaRepository 상속받아야 함 - 인자(entity,entity의 id값의 data 타입)


    boolean existsByUsername(String username);//존재하면 true

    UserEntity findByUsername(String username);
}
