package com.lim.api.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class LoginReq {

    private final String email;

    @Size(min = 6, message = "최소 6자리 이상")
    private final String password;
}
