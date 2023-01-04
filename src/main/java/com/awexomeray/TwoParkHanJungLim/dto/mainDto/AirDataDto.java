package com.awexomeray.TwoParkHanJungLim.dto.mainDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AirDataDto {
    private Double temp;

    private Double humi;

    private Double co2;

    private Double tvoc;

    private Double pm01;

    private Double pm25;

    private Double pm10;

}
