package com.awexomeray.TwoParkHanJungLim.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodes {
    NO_SEARCH_SENSOR_ID("최신 데이터에 지정된 센서아이디가 axr-sensor collection에 존재하지 않습니다.", 1000),
    NO_SEARCH_COLLECTION("해당 userId는 존재하지 않습니다.", 1001);

    private final String errorMessage;
    private final Integer errorCode;

}