package com.homeshoping.homeshoping.Exception;

public class MemberNotFound extends MainException{

    private final static String MESSAGE = "존재하지 않는 회원입니다.";

    public MemberNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}

