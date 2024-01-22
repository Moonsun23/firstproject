package com.example.firstproject.controller;

import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Slf4j
@Controller
public class MemberController {
    @Autowired
    private MemberRepository memberRepository;
    @GetMapping("/signup")
    public String newMemberForm() {
        return "members/new";
    }

    @PostMapping("/join/create")
    public String createMember(MemberForm form){
        log.info(form.toString());
        // 1. DTO를 엔티티로 변환
        Member member = form.toEntity();
        log.info(member.toString());
        // 2. 리파지터리로 엔티티를 DB에 저장
        Member saved= memberRepository.save(member);
        log.info(saved.toString());
        return "";
    }

}
