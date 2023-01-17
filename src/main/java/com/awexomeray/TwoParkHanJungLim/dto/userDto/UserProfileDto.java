package com.awexomeray.TwoParkHanJungLim.dto.userDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileDto {
    private String name;

    private String id;

    private String phone;

    private String email;
}
