package com.example.carbonproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JoinDTO {

    //사용자 입력 필수, 미입력시 오류메시지
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String username;

    @NotBlank(message = "비밀번호는 필수 입력값입니다. ")
    @Size(min=4, message = "비밀번호는 최소 4자 이상이어야 합니다.")
    private String password;
}
