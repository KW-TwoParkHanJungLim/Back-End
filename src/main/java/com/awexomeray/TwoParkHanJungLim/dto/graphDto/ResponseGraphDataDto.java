package com.awexomeray.TwoParkHanJungLim.dto.graphDto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@Builder
public class ResponseGraphDataDto {
    private List<Map> sensorData;
}
