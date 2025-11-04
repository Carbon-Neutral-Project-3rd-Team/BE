package com.example.testsecurity.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class UserEntity {

    @Id//entity는 id가 필수로 필요함
    @GeneratedValue(strategy = GenerationType.IDENTITY)//id가 자동으로 생성
    private int id;

    @Column(unique = true)//userName 중복 방지
    private String username;//db에 저장될 것들

    private String password;

    private String role;//로그인 시 권한 저장
}
