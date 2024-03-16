package com.attendenceapp.attendenceapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {
    private Long id;
    private String userName;
    private String email;
    private String phone;
    private String password;
}