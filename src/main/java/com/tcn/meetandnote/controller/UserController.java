package com.tcn.meetandnote.controller;

import com.tcn.meetandnote.dto.UserDTO;
import com.tcn.meetandnote.security.JWTProvider;
import com.tcn.meetandnote.services.impl.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final JWTProvider jwtProvider;
    private final UserService userService;

    public UserController(JWTProvider jwtProvider, UserService userService) {
        this.jwtProvider = jwtProvider;
        this.userService = userService;
    }

    @PostMapping("/get-by-token")
    public UserDTO getUserFromToken(@RequestBody String token) {
        String username = jwtProvider.getUsernameFromToken(token);
        return userService.getUserByUsernameAndConvertDTO(username);
    }
}
