package com.awexomeray.TwoParkHanJungLim.unit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AirUnit {
    private String temp;

    private String humi;

    private String co2;

    private String tvoc;

    private String pm01;

    private String pm25;

    private String pm10;

    public AirUnit() {
        temp = "ºC";
        humi = "%";
        co2 = "ppm";
        tvoc = "ppb";
        pm01 = "㎍/㎡";
        pm25 = "㎍/㎡";
        pm10 = "㎍/㎡";
    }
}
