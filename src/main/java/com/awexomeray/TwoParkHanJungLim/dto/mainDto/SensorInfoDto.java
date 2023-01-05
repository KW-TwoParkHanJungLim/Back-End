package com.awexomeray.TwoParkHanJungLim.dto.mainDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SensorInfoDto {
    private String sensorName;

    private String sensorId;

    private String logTime;

    private AirDataDto airData;
}
