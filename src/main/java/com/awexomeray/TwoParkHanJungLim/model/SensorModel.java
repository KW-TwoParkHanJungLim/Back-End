package com.awexomeray.TwoParkHanJungLim.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@Document(collection = "axr-sensor")
public class SensorModel {
    @Id
    private String _id;

    private String s_id;

    private Object vender;

    private String type;

    private String version;

    private String name;

    private String desc;

    private Date _createTime;

    private Date _updateTime;

    private Object _createUser;

    private Object _updateUser;

    private String station;

    private String status;

    private String alias;
}