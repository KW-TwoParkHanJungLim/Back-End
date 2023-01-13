package com.awexomeray.TwoParkHanJungLim.dto.graphDto;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class RequestGraphDataDto {
    private String collection;
    private String logTime;
    private List<String> sensors;
    private String airData;
}
