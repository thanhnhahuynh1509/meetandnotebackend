package com.tcn.meetandnote.controller;

import com.tcn.meetandnote.dto.UserDTO;
import com.tcn.meetandnote.entity.User;
import com.tcn.meetandnote.security.JWTProvider;
import com.tcn.meetandnote.services.impl.UserService;
import com.tcn.meetandnote.utils.FileUploadUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final JWTProvider jwtProvider;
    private final UserService userService;

    public UserController(JWTProvider jwtProvider, UserService userService) {
        this.jwtProvider = jwtProvider;
        this.userService = userService;
    }


    @GetMapping("/rooms/{id}")
    public List<UserDTO> getUsersByRoomId(@PathVariable long id) {
        return userService.getUsersByRoomId(id);
    }

    @GetMapping("/{userId}/rooms/{roomId}")
    public UserDTO getUserByRoomLinkAndUserId(@PathVariable long userId, @PathVariable long roomId) {
        return userService.getUserByRoomAndUserId(userId, roomId);
    }

    @PostMapping("/get-by-token")
    public UserDTO getUserFromToken(@RequestBody String token) {
        String username = jwtProvider.getUsernameFromToken(token);
        return userService.getUserByUsernameAndConvertDTO(username);
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @PutMapping("/permission/{userId}/rooms/{roomId}")
    public UserDTO updatePermission(@PathVariable long userId, @PathVariable long roomId) {
        return userService.updatePermission(userId, roomId);
    }

    @PutMapping("/{id}/update-image")
    public UserDTO updateImage(@PathVariable long id, @RequestPart MultipartFile imageFile) {
        return userService.updateImage(id, imageFile);
    }
}
