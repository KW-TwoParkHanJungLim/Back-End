package com.awexomeray.TwoParkHanJungLim.exception;

import lombok.Getter;

@Getter
public class ApiCustomErrorResponseDto {
    private final String errorMessage;
    private final Integer errorCode;

    public ApiCustomErrorResponseDto(ErrorCodes errorCodes) {
        errorMessage = errorCodes.getErrorMessage();
        errorCode = errorCodes.getErrorCode();
    }
}
