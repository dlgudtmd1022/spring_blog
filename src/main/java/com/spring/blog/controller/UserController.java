package com.spring.blog.controller;

import com.spring.blog.entity.User;
import com.spring.blog.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
@Controller
public class UserController {

    private final UsersService usersService;

    @Autowired
    public UserController(UsersService usersService){
        this.usersService = usersService;
    }

    // Get방식으로 로그인 창으로 넘어가는 로직
    // /user/login.jsp로 넘어가게
    @GetMapping("/login")
    public String login(){
        return "user/login";
    }

    //Get방식으로 회원가입 폼으로 넘어가는 로직
    // /user/signup.jsp로 넘어가게
    @GetMapping("/signup")
    public String signUpForm(){
        return "user/signup";
    }

    // Post방식으로 회원가입 요청을 처리하게
    // 주소는 localhost:8080/signup
    // 커맨트객체로 User Entity를 선언헤서, 가입정보를 받아 .save()를 호출
    // 실행결과는 리다이렉트로
    @PostMapping("/signup")
    public String signUpForm(User user){

        usersService.save(user);

        return "redirect:/login";
    }

}
