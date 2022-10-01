package com.tcn.meetandnote.services.impl;

import com.tcn.meetandnote.dto.UserDTO;
import com.tcn.meetandnote.entity.Room;
import com.tcn.meetandnote.entity.User;
import com.tcn.meetandnote.entity.UserRoom;
import com.tcn.meetandnote.exception.ConflictException;
import com.tcn.meetandnote.exception.NotFoundException;
import com.tcn.meetandnote.repository.UserRepository;
import com.tcn.meetandnote.services.RoomService;
import com.tcn.meetandnote.services.UserRoomService;
import com.tcn.meetandnote.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RoomService roomService;
    private final UserRoomService userRoomService;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoomService roomService, UserRoomService userRoomService, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roomService = roomService;
        this.userRoomService = userRoomService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO save(User user) {
        if(isUsernameExists(user.getEmail())) {
            throw new ConflictException("Email has already use. Please enter another email");
        }
        // Save room
        Room room = new Room();
        room.setTitle("Home");
        room = roomService.save(room);
        // Save user
        user.setPassword(encodePassword(user.getPassword()));
        user.setRoomToken(room.getFullPermissionToken());
        user = userRepository.save(user);
        // Save user-room
        UserRoom userRoom = new UserRoom();
        userRoom.setUser(user);
        userRoom.setRoom(room);
        userRoom.setRead(true);
        userRoom.setFullPermission(true);
        userRoom.setOwner(true);
        userRoomService.save(userRoom);
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO getUserByUsernameAndPassword(String username, String password) {
        User user =  userRepository.findUserByUsernameAndPassword(username, password)
                .orElseThrow(() -> new NotFoundException("email or password are not correct!"));
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Email or Password are not correct!"));
    }

    @Override
    public void delete(long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Not found user with id: " + id));
        roomService.delete(user.getRoomToken());
        userRepository.delete(user);
    }

    private boolean isUsernameExists(String username) {
        return userRepository.findUserByUsername(username).isPresent();
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
