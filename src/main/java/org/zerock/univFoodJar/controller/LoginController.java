package org.zerock.univFoodJar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

//    @GetMapping("/loginRegister")
//    public String loginRegister() {
//        return "loginRegister";
//    }

//    @GetMapping("/loginModify")
//    public String loginModify() {
//        return "loginModify";
//    }

//    @GetMapping("/loginDelete")
//    public String loginDelete() {
//        return "loginDelete";
//    }
}