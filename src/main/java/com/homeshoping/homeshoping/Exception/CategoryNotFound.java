package com.homeshoping.homeshoping.Exception;

public class CategoryNotFound extends MainException{

    private final static String MESSAGE = "존재하지 않는 카테고리입니다.";

    public CategoryNotFound(){super(MESSAGE);}

    @Override
    public int getStatusCode() {
        return 404;
    }
}
