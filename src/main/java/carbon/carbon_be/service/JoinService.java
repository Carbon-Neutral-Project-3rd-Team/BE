package com.example.testsecurity.service;


import com.example.testsecurity.dto.JoinDTO;
import com.example.testsecurity.entity.UserEntity;
import com.example.testsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service//Service로 인식되기 위함
public class JoinService {//데이터를 db에 저장

    @Autowired
    private UserRepository userRepository;//현재는 필드이지만 추후 생성자 방식으로

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    //회원가입을 위한 정보 - joinDTO에서 정보 받음
    //joinDTO를 entity로 갈아탄 후 db에 저장
    public void joinProcess(JoinDTO joinDTO){//데이터를 받아서 회원가입 진행


        //db에 이미 동일한 username을 가진 회원이 존재하는지 확인 필요
        boolean isUser=userRepository.existsByUsername(joinDTO.getUserName());
        if(isUser){return;}


        //dto의 데이터를 entity로 변환
        UserEntity data=new UserEntity();

        data.setUsername(joinDTO.getUserName());
        data.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));//data는 암호화 필요
        data.setRole("ROLE_ADMIN");//회원 스스로가 role설정 불가 -> 우리가 명시해야 함


        userRepository.save(data);//userrepository에 user의 정보 저장
    }
    //user 정보 저장
}
