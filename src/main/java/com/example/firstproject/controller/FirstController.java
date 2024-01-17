package com.example.firstproject.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    @GetMapping("/hi")   // url 주소 /hi 에 페이지를 반환해달라는 url 요청접수
    public String niceToMeetYou(Model model){
        // model  객체가 "홍팍" 값을 "username"에 연결해 웹 브라우저로 보냄
        // addAttribute() 메서드: 모델에서 변수를 등록할 때 사용
        model.addAttribute("username", "hongpark");
        return "greetings";  // greetings.mustache 파일 반환
    }

    @GetMapping("/bye")
    public String seeYouNext(Model model){
        model.addAttribute("nickname","hongpark");
        return "goodbye";
    }
}
