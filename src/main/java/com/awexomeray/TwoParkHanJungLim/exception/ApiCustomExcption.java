package com.awexomeray.TwoParkHanJungLim.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class ApiCustomExcption extends RuntimeException{
    private final ErrorCodes errorCodes;
}
