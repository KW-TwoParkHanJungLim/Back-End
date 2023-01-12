package com.awexomeray.TwoParkHanJungLim.dto.mainDto;

import com.awexomeray.TwoParkHanJungLim.unit.AirUnit;
import lombok.Getter;

import java.util.List;

@Getter
public class AirUnitWithSensorInfoDto {
    private AirUnit unit;

    private List<SensorInfoDto> sensorInfoList;

    public AirUnitWithSensorInfoDto(List<SensorInfoDto> sensorInfoDtoList) {
        unit = new AirUnit();
        this.sensorInfoList = sensorInfoDtoList;
    }
}