package com.awexomeray.TwoParkHanJungLim.controller;

import com.awexomeray.TwoParkHanJungLim.service.AirDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Details")
public class AirDetailsController {

    private final AirDetailsService airDetailsService;

    @GetMapping
    public ResponseEntity<Map<String, Object> > response(@RequestParam("date") Date date, @RequestParam("id") String id){
        Map<String, Object> response = new HashMap<>();
        response.put("dayAvg",airDetailsService.getAvgDayAir(date,id));
        response.put("weekAvg",airDetailsService.getAvgWeekAir(date,id));

        return ResponseEntity.ok().body(response);
    }
}
