package com.awexomeray.TwoParkHanJungLim.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodes {
    NO_SEARCH_SENSOR_ID("최신 데이터에 지정된 센서아이디가 axr-sensor collection에 존재하지 않습니다.", 1000),
    NO_SEARCH_COLLECTION("해당 userId는 존재하지 않습니다.", 1001),
    NO_DATA_COLLECTION("해당 collection은 공기질 데이터를 저장하지 않습니다.", 1002),
    NO_SEARCH_AIR_TYPE("해당 공기질 종류는 존재하지 않습니다.", 1003),
    NO_SEARCH_DATE("해당 날짜의 데이터는 존재하지 않습니다.", 1004),
    NO_SEARCH_USER("해당 유저는 존재하지 않습니다.", 1005);

    private final String errorMessage;
    private final Integer errorCode;

}