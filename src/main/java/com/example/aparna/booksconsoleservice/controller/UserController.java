package com.example.aparna.booksconsoleservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @RequestMapping("/user")
    public String user(@AuthenticationPrincipal OAuth2User principal) {
        System.out.println(principal);
//        return principal.getAttribute("login");
        try{
            String json = new ObjectMapper().writeValueAsString(principal);
            return json;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "Success";
    }
}
