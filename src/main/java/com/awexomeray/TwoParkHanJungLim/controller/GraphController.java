package com.awexomeray.TwoParkHanJungLim.controller;

import com.awexomeray.TwoParkHanJungLim.dto.mainDto.SensorInfoDto;
import com.awexomeray.TwoParkHanJungLim.service.GraphService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController //post man에서 사용할 수 있다.
@RequiredArgsConstructor
@RequestMapping("/graph")
public class GraphController {
    private final GraphService graphService;

    @PostMapping("/user")
    public ResponseEntity<?> getAirData(@RequestBody Map<String,String>body) {
//        List<GraphModel> graphModels  ;
//        if (graphModels.size() > 0){
//            return new ResponseEntity<List<GraphModel>>(graphModels, HttpStatus.OK);
//        }
//        else
//        {
//            return new ResponseEntity<>("graphModels Not found", HttpStatus.NOT_FOUND);
//        }
        List<SensorInfoDto> sensorInfoDtoList = mainService.getAirDataOfSensors(userId);
        return ResponseEntity.ok().body(sensorInfoDtoList);
    }
}