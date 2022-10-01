package com.tcn.meetandnote.services;

import com.tcn.meetandnote.dto.UserDTO;
import com.tcn.meetandnote.entity.User;

public interface UserService {
    UserDTO save(User user);
    UserDTO getUserByUsernameAndPassword(String username, String password);
    User getUserByUsername(String username);

    void delete(long id);
}
