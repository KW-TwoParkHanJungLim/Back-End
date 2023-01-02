package com.awexomeray.TwoParkHanJungLim.dto.mainDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GraphDataDto {
    private String collection;
    private String date;
    private List<String> sensors;
    private String airData;
}
