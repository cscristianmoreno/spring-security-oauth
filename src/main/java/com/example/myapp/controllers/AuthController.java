package com.example.myapp.controllers;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.myapp.auth.LoginAuth;
import com.example.myapp.auth.TokenAuth;
import com.example.myapp.dto.UserDTO;
import com.example.myapp.models.User;
import com.example.myapp.repository.UserRepository;
import com.example.myapp.service.JwtService;

import io.jsonwebtoken.JwtException;

@Controller
@ResponseBody
@RequestMapping(value = "/auth")
public class AuthController {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;

    public AuthController(final UserRepository userRepository, final PasswordEncoder passwordEncoder,
        final AuthenticationManager authenticationManager, final JwtService jwtService, final ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<String> authRegister(@RequestBody User user) {
        System.out.println(user.toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        this.userRepository.save(user);
        return new ResponseEntity<String>("Te has registrado con exito", HttpStatus.OK);
    }

    @PostMapping("/login")
    public TokenAuth authLogin(@RequestBody LoginAuth auth) throws Exception, JwtException {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(auth.getEmail(), auth.getPassword());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        Optional<User> user = this.userRepository.findByEmail(auth.getEmail());
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        String token = jwtService.generateToken(userDTO);

        TokenAuth tokenAuth = TokenAuth.builder().token(token).user(userDTO).build();
        return tokenAuth;
    }

    @PostMapping("/authentication")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Void> authAuthentication() {
        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }
}
