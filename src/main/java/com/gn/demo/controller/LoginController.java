//package com.gn.demo.controller;
//
//import com.gn.demo.entity.User;
//import com.gn.demo.service.IUserService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//
//@Controller
////@CrossOrigin(origins = "*", maxAge = 3600)
//@Slf4j
//public class LoginController {
//
//    @Autowired
//    private IUserService userService;
//
//    @GetMapping("/")
//    public String index() {
//        return "login";
//    }
//
//    @PostMapping("/login")
//    public String login(String username, String password, Model model) {
//        log.info("login.username=[{}], password=[{}]", username, password);
//        User user = User.builder()
//                .username(username)
//                .password(password)
//                .build();
//        boolean isValidUser = userService.authenticateUser(user);
//        if (isValidUser) {
//            model.addAttribute("username", user.getUsername());
//            return "welcome";
//        } else {
//            model.addAttribute("error", "用户名或密码错误!");
//            return "index";
//        }
//    }
//}
