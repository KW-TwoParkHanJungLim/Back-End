package com.awexomeray.TwoParkHanJungLim.service;

import com.awexomeray.TwoParkHanJungLim.dao.UserDao;
import com.awexomeray.TwoParkHanJungLim.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserDao userDao;

    public List<String> getAllUserId(){
        List<UserEntity> users=userDao.getUsers();
        List<String> usersId=new ArrayList<>();
        for(UserEntity user : users){
            usersId.add(user.getId());
        }

        return usersId;
    }
}
