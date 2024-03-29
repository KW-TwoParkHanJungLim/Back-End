package com.awexomeray.TwoParkHanJungLim.controller;

import com.awexomeray.TwoParkHanJungLim.config.security.JwtTokenProvider;
import com.awexomeray.TwoParkHanJungLim.dao.UserDao;
import com.awexomeray.TwoParkHanJungLim.entity.UserEntity;
import com.awexomeray.TwoParkHanJungLim.exception.ApiCustomException;
import com.awexomeray.TwoParkHanJungLim.exception.ErrorCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final UserDao userDao;

    private final JwtTokenProvider jwtTokenProvider;

    //로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        UserEntity user;

        try {
            user = userDao.getUser(body.get("id"));

            if (!body.get("password").equals(user.getPassword())) {
                throw new ApiCustomException(ErrorCodes.FAIL_LOGIN);
            }

        } catch (NullPointerException exception) {
            throw new ApiCustomException(ErrorCodes.FAIL_LOGIN);
        }

        Map response = new HashMap<>();
        response.put("role", user.getStatus());
        response.put("token", jwtTokenProvider.createToken(user.getId()));

        return ResponseEntity.ok().body(response);
    }
}
