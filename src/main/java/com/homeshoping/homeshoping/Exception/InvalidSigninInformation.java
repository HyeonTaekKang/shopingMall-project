package com.homeshoping.homeshoping.Exception;

public class InvalidSigninInformation extends MainException{

    private final static String MESSAGE = "아이디/비밀번호가 올바르지 않습니다.";

    public InvalidSigninInformation(){super(MESSAGE);}

    @Override
    public int getStatusCode() {
        return 400;
    }

}

