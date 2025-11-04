package com.example.testsecurity.controller;

import com.example.testsecurity.dto.JoinDTO;
import com.example.testsecurity.service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JoinController {


    @Autowired
    private JoinService joinService;//필드 주입방식 but 나중에 생성자 형태로 주입하길 권장

    @GetMapping("/join")
    public String joinP(){
        return "join";
    }

    @PostMapping("/joinProc")//joinProc에서 정보 받음
    public String joinProcess(JoinDTO joinDTO){//데이터를 받아올 수 있는 dto
        System.out.println(joinDTO.getUserName());

        joinService.joinProcess(joinDTO);//정보 db에 저장

        return "redirect:/login";//login으로
    }

}
