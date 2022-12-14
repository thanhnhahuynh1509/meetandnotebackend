package com.tcn.meetandnote.controller;

import com.tcn.meetandnote.dto.UserDTO;
import com.tcn.meetandnote.entity.User;
import com.tcn.meetandnote.security.JWTProvider;
import com.tcn.meetandnote.services.EmailSenderService;
import com.tcn.meetandnote.services.impl.UserService;
import com.tcn.meetandnote.utils.FormStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin()
@RestController
@RequestMapping("")
public class AccountController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;
    private final EmailSenderService emailSenderService;

    public AccountController(UserService userService, AuthenticationManager authenticationManager, JWTProvider jwtProvider, EmailSenderService emailSenderService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.emailSenderService = emailSenderService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserDTO> save(@RequestBody User user) {
        UserDTO userDTO = userService.saveDTO(user);
        emailSenderService.sendMail(userDTO.getEmail(), "Xác nhận tài khoản Meet&Note",
                FormStringUtils.getMailVerifyForm(userDTO.getVerifyToken()));
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestBody User user) {
        String username = user.getEmail();
        String password = user.getPassword();
        String token = "";
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtProvider.generateToken(authentication);
        } catch (Exception ex) {
            throw new BadCredentialsException("Username or password are not correct!");
        }
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping("/verify")
    public String verify(@RequestParam(defaultValue = "") String verifyToken) {
        try {
            User user = userService.getUserByVerifyToken(verifyToken);
            userService.enabledUser(user.getId());
            return "OK";
        } catch (Exception ex) {
            return "NOT OK";
        }

    }

}
