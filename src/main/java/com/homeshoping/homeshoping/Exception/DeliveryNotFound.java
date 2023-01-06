package com.homeshoping.homeshoping.Exception;

public class DeliveryNotFound extends MainException{

    private final static String MESSAGE = "배달 정보를 찾을 수 없습니다.";

    public DeliveryNotFound(){super(MESSAGE);}

    @Override
    public int getStatusCode() {
        return 404;
    }
}
