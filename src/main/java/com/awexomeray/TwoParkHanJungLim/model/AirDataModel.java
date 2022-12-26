package com.awexomeray.TwoParkHanJungLim.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AirDataModel {
    private String _id;

    private String day;

    private String s_id;

    private String logtime;

    private Double temp;

    private Double humi;

    private Integer co2;

    private Integer tvoc;

    private Integer pm01;

    private Integer pm25;

    private Integer pm10;
}