package com.lim.core.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserCreateReq {

    private final String name;
    private final String email;
    private final String password;
    private final LocalDateTime birthday;
}
