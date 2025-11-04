package com.example.testsecurity.dto;

import lombok.Getter;
import lombok.Setter;

@Setter//데이터를 설정
@Getter//데이터를 뽑음
public class JoinDTO {//회원가입 폼 데이터를 받음

    private String userName;
    private String password;

}
