package com.awexomeray.TwoParkHanJungLim.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiCustomException extends RuntimeException{
    private final ErrorCodes errorCodes;
}
