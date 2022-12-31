package com.awexomeray.TwoParkHanJungLim.dto.mainDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AirDataDto {
    private Double temp;

    private Double humi;

    private Integer co2;

    private Integer tvoc;

    private Integer pm01;

    private Integer pm25;

    private Integer pm10;

}
