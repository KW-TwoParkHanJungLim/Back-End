package com.awexomeray.TwoParkHanJungLim.dto.mainDto;

import com.awexomeray.TwoParkHanJungLim.unit.AirUnit;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AirUnitWithSensorInfoDto {
    private List<AirUnit> unit;

    private List<SensorInfoDto> sensorInfoList;

    public AirUnitWithSensorInfoDto(List<SensorInfoDto> sensorInfoDtoList) {
        unit = new ArrayList<>();
        unit.add(new AirUnit("Temperature", "ºC"));
        unit.add(new AirUnit("Humidity", "%"));
        unit.add(new AirUnit("CO2", "ppm"));
        unit.add(new AirUnit("TVOC", "ppb"));
        unit.add(new AirUnit("PM01", "㎍/㎡"));
        unit.add(new AirUnit("PM2.5", "㎍/㎡"));
        unit.add(new AirUnit("PM10", "㎍/㎡"));

        this.sensorInfoList = sensorInfoDtoList;
    }
}