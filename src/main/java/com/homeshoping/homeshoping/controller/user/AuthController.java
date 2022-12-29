package com.homeshoping.homeshoping.controller.user;

import com.homeshoping.homeshoping.request.user.Login;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @PostMapping("/auth/login")
    public void login(@RequestBody Login login){
        // json 아이디 / 비번
        // DB에서 조회
        // 토큰을 응답
    }

}
