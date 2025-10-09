package com.example.carbonproject.repository;

import com.example.carbonproject.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {//Entity, id값의 타입

    Boolean existsByUsername(String username);

    UserEntity findByUsername(String username);


}
