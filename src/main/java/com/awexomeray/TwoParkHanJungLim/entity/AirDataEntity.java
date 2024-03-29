package com.awexomeray.TwoParkHanJungLim.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AirDataEntity {
    private String _id;

    private String day;

    private String s_id;

    private String logtime;

    private Double temp;

    private Double humi;

    private Double co2;

    private Double tvoc;

    private Double pm01;

    private Double pm25;

    private Double pm10;
}