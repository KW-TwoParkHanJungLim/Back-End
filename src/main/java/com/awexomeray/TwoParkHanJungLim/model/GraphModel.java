package com.awexomeray.TwoParkHanJungLim.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "_users")
public class GraphModel {
    @Id
    private String _id;
    //private String status;
    private String id;
    //private String password;
    private String name;
    private String email;
    private String phone;
    private String place;
    //private Date _createTime;
    //private Date _updateTime;
    //private Object _updateUser;
    //private Object _createUser;
    //private String avatar;

//    @Override
//    public String toString()
//    {
//        return "SensorModel : {" +
//                "name='" + name + '\'' +
//                ", u_id='" + u_id + '\'' +
//                ", status='" + status + '\'' +
//                '}';
//    }
}
