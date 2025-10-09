package com.example.carbonproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter//변수 설정 및 접근
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//identity설정 -> 중복 방지
    private int id;

    @Column(unique = true)//username의 중복을 db정도에서 방지
    private String username;

    @Column(length = 100)//비밀번호 해시값 복잡-> 충분히 길게 설정
    private String password;

    private String role;
}
