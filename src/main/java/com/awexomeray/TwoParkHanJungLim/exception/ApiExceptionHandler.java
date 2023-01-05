package com.awexomeray.TwoParkHanJungLim.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiCustomException.class)
    public ResponseEntity<?> handleNullPointerExcption(ApiCustomException e) {

        //500에러 -> 서버에러
        return ResponseEntity.internalServerError().body(new ApiCustomErrorResponseDto(e.getErrorCodes()));
    }
}
