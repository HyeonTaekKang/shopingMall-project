package com.homeshoping.homeshoping.request.member;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class Login {

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

}
