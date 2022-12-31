package com.awexomeray.TwoParkHanJungLim.controller;

import com.awexomeray.TwoParkHanJungLim.dto.mainDto.SensorInfoDto;
import com.awexomeray.TwoParkHanJungLim.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor//final이 붙거나 @NotNull이 붙은 필드의 생성자를 자동으로 생성해주는 롬복 어노테이션
@RequestMapping("/main")
public class MainController {
    private final MainService mainService;

    //collection이름을 받아서 센서별 공기질 데이터를 제공
    @GetMapping
    public ResponseEntity<?> findSensorsData(@RequestParam("userId") String userId) {
        List<SensorInfoDto> sensorInfoDtoList = mainService.getAirDataOfSensors(userId);
        return ResponseEntity.ok().body(sensorInfoDtoList);
    }
}
