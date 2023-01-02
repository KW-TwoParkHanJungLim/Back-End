package com.awexomeray.TwoParkHanJungLim.controller;

import com.awexomeray.TwoParkHanJungLim.dto.mainDto.AirDataDto;
import com.awexomeray.TwoParkHanJungLim.dto.mainDto.GraphDataDto;
import com.awexomeray.TwoParkHanJungLim.model.AirDataModel;
import com.awexomeray.TwoParkHanJungLim.service.GraphService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController //post man에서 사용할 수 있다.
@RequiredArgsConstructor
@RequestMapping("/graph")
public class GraphController {
    private final GraphService graphService;

    @PostMapping()
    public ResponseEntity<?> getAirData(@RequestBody GraphDataDto graphDataDto) {
          List<AirDataModel> airDataModels = graphService.getAirDataOfGraph(graphDataDto);
//        List<GraphModel> graphModels  ;
//        if (graphModels.size() > 0){
//            return new ResponseEntity<List<GraphModel>>(graphModels, HttpStatus.OK);
//        }
//        else
//        {
//            return new ResponseEntity<>("graphModels Not found", HttpStatus.NOT_FOUND);
//        }
        //List<SensorInfoDto> sensorInfoDtoList = mainService.getAirDataOfSensors(userId);
        return ResponseEntity.ok().body(null);
    }
}