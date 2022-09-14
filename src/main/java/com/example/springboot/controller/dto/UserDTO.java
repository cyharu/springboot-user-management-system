package com.example.springboot.controller.dto;

import com.example.springboot.entity.User;
import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String password;
    private String nickname;
    private String avatar;
//    private int reservation;

}
