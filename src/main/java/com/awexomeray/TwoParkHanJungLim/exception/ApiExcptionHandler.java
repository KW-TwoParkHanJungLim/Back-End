package com.awexomeray.TwoParkHanJungLim.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExcptionHandler {

    @ExceptionHandler(ApiCustomExcption.class)
    public ResponseEntity<?> handleNullPointerExcption(ApiCustomExcption e) {

        //500에러 -> 서버에러
        return ResponseEntity.internalServerError().body(new ApiCustomErrorResponseDto(e.getErrorCodes()));
    }
}
