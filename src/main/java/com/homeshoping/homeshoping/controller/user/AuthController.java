package com.homeshoping.homeshoping.controller.user;

import com.homeshoping.homeshoping.request.member.Login;
import com.homeshoping.homeshoping.service.member.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/login")
    public void login(@RequestBody Login login){
        authService.signIn(login);
    }
}
