package com.example.carbonproject.controller;

import com.example.carbonproject.dto.JoinDTO;
import com.example.carbonproject.service.JoinService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class JoinController {//데이털를 받

    private final JoinService joinService;

    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }

    @PostMapping("/join")
    public String joinP(@Valid JoinDTO joinDTO) {

        joinService.joinP(joinDTO);

        return "200 ok";
    }
}
