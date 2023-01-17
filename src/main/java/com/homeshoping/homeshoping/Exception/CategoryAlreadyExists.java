package com.homeshoping.homeshoping.Exception;

public class CategoryAlreadyExists extends MainException {
    private final static String MESSAGE = "이미 존재하는 카테고리입니다.";

    public CategoryAlreadyExists(){super(MESSAGE);}

    @Override
    public int getStatusCode() {
        return 400;
    }
}
