package com.homeshoping.homeshoping.Exception;


public class ItemNotFound extends MainException{

    private final static String MESSAGE = "존재하지 않는 상품입니다.";

    public ItemNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
