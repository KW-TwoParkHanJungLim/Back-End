package com.awexomeray.TwoParkHanJungLim.testMVC.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    private String _id;

    private String status;

    @Field("id")
    private String id;

    private String password;

    private String name;

    private String email;

    private String phone;

    private String place;

    private String _createTime;

    private Date _updateTime;

    private Object _updateUser;

    private String _createUser;

    private String avatar;

}