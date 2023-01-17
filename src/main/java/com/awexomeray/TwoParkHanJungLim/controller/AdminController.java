package com.awexomeray.TwoParkHanJungLim.controller;

import com.awexomeray.TwoParkHanJungLim.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;


    @GetMapping("/userList")
    public ResponseEntity<?> getUserList(){
        List<String> users=adminService.getAllUserId();
        return ResponseEntity.ok().body(users);
    }
}
