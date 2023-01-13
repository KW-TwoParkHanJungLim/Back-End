package com.awexomeray.TwoParkHanJungLim.controller;


import com.awexomeray.TwoParkHanJungLim.dto.graphDto.RequestGraphDataDto;
import com.awexomeray.TwoParkHanJungLim.dto.mainDto.SensorInfoDto;
import com.awexomeray.TwoParkHanJungLim.service.GraphService;
import com.awexomeray.TwoParkHanJungLim.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


@RestController //post man에서 사용할 수 있다.
@RequiredArgsConstructor
@RequestMapping("/graph")
public class GraphController {
    private final GraphService graphService;
    private final MainService mainService;

    @GetMapping("/sensors") //센서 리스트 제공
    public ResponseEntity<?> getSensorList(@RequestParam("userId") String userId) {
        List<SensorInfoDto> sensorInfoDtoList = mainService.getAirDataOfSensors(userId);
        return ResponseEntity.ok().body(sensorInfoDtoList);
    }

    @GetMapping//그래프 데이터 반환
    public ResponseEntity<?> getAirData(@Valid RequestGraphDataDto requestGraphDataDto) {
        List<List<Map>> graphAirData = graphService.getAirDataOfGraph(requestGraphDataDto);
        return ResponseEntity.ok().body(graphAirData);
    }
}