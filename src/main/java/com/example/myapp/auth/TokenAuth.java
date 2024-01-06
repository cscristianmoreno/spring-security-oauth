package com.example.myapp.auth;

import com.example.myapp.dto.UserDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TokenAuth {
    String token;
    UserDTO user;
}
