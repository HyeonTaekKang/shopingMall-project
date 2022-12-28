package com.homeshoping.homeshoping.response.ErrorResponse;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;


/**
 * {
 * "code": "400",
 * "message": "잘못된 요청입니다.",
 * "errorFieldNameAndErrorMessage": {
 * "title": "상품명을 입력해주세요!"
 * }
 * }
 */
@Getter
public class ErrorResponse {

    // 에러코드
    private final String code;

    // 에러메세지
    private final String message;

    // 어떤 필드에서 에러가 났고, 그 필드에서 던지는 에러 메시지는 무엇인지
    private Map<String,String> errorFieldNameAndErrorMessage;

    @Builder
    public ErrorResponse(String code, String message, Map<String, String> errorFieldNameAndErrorMessage) {
        this.code = code;
        this.message = message;
        this.errorFieldNameAndErrorMessage = errorFieldNameAndErrorMessage != null ? errorFieldNameAndErrorMessage : new HashMap<>();
    }

    public void addErrorFieldNameAndErrorMessage(String fieldName , String errorMessage){
        errorFieldNameAndErrorMessage.put(fieldName,errorMessage);
    }
}
