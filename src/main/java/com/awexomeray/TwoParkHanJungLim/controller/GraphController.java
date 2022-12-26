package com.awexomeray.TwoParkHanJungLim.controller;

import com.awexomeray.TwoParkHanJungLim.model.GraphModel;
import com.awexomeray.TwoParkHanJungLim.repository.GraphRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController //post man에서 사용할 수 있다.
public class GraphController {
    @Autowired
    private GraphRepository graphRepository;

    @GetMapping("/user")
    public ResponseEntity<?> getUser(){
        List<GraphModel> graphModels  = graphRepository.findAll();
        if (graphModels.size() > 0){
            return new ResponseEntity<List<GraphModel>>(graphModels, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>("graphModels Not found", HttpStatus.NOT_FOUND);
        }
    }
}