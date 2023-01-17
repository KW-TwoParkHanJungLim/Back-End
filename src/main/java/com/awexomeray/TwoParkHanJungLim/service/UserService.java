package com.awexomeray.TwoParkHanJungLim.service;

import com.awexomeray.TwoParkHanJungLim.dao.UserDao;
import com.awexomeray.TwoParkHanJungLim.dto.userDto.UserProfileDto;
import com.awexomeray.TwoParkHanJungLim.entity.UserEntity;
import com.awexomeray.TwoParkHanJungLim.exception.ApiCustomException;
import com.awexomeray.TwoParkHanJungLim.exception.ErrorCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    public UserProfileDto getUserInfo(String _id) {
        String name = "", id = "", phone = "", email = "";

        UserEntity userEntity = userDao.getUser(_id);
        if (userEntity == null) throw new ApiCustomException(ErrorCodes.NO_SEARCH_USER);

        if (userEntity.getName() != null) name = userEntity.getName();
        if (userEntity.getId() != null) id = userEntity.getId();
        if (userEntity.getPhone() != null) phone = userEntity.getPhone();
        if (userEntity.getEmail() != null) email = userEntity.getEmail();

        return UserProfileDto.builder()
                .name(name)
                .id(id)
                .phone(phone)
                .email(email)
                .build();
    }
}
