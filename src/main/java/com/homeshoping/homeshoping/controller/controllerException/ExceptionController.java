package com.homeshoping.homeshoping.controller.controllerException;

import com.homeshoping.homeshoping.Exception.ItemNotFound;
import com.homeshoping.homeshoping.Exception.MainException;
import com.homeshoping.homeshoping.response.ErrorResponse.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;


@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    // 컨트롤러로 유효하지못한 객체(Request)가 매개변수로 들어와서 에러를 던지면 이 메서드가 그 에러를 처리함.
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e){

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청입니다.")
                .build();

        // 에러정보가 담긴 리스트
        List<FieldError> fieldErrors = e.getFieldErrors(); // [{},{},{}]


        // ErrorResponse 객체에 오류난 필드 이름 + 오류 메세지 저장.
        for(FieldError fieldError : fieldErrors){
            errorResponse.addErrorFieldNameAndErrorMessage(fieldError.getField(),fieldError.getDefaultMessage());
        }

        return errorResponse;
    }

    @ExceptionHandler(MainException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> MainException(MainException e){
        int statusCode = e.getStatusCode();

        ErrorResponse body = ErrorResponse.builder()
                .code(String.valueOf(statusCode))
                .message(e.getMessage())
                .build();

        ResponseEntity<ErrorResponse> response = ResponseEntity.status(statusCode)
                .body(body);

        return response;
    }

}
