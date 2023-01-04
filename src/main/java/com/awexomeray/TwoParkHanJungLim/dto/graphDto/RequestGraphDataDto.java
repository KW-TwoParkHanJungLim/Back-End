package com.awexomeray.TwoParkHanJungLim.dto.graphDto;

import com.awexomeray.TwoParkHanJungLim.dto.mainDto.AirDataDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestGraphDataDto {
    private String collection;
    private String logTime;
    private List<String> sensors;
    private String airData;
}
