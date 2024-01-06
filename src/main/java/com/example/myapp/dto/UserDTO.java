package com.example.myapp.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDTO {
    private int id;
    private String name;
    private String lastname;
    private String email;
}
