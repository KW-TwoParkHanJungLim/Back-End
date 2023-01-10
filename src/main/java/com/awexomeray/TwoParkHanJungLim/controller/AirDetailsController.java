package com.awexomeray.TwoParkHanJungLim.controller;

import com.awexomeray.TwoParkHanJungLim.service.AirDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/details")
public class AirDetailsController {

    private final AirDetailsService airDetailsService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> averageController(@RequestParam("userId") String userId,
                                                        @RequestParam("date") String date,
                                                        @RequestParam("id") String id) {
        Map<String, Object> response = new HashMap<>();
        response.put("dayAvg", airDetailsService.getAvgAir(userId, date, id, 1));
        response.put("weekAvg", airDetailsService.getAvgAir(userId, date, id, 7));

        return ResponseEntity.ok().body(response);
    }
}
