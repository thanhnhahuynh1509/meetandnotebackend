package com.tcn.meetandnote.services.impl;

import com.tcn.meetandnote.dto.UserDTO;
import com.tcn.meetandnote.entity.Room;
import com.tcn.meetandnote.entity.User;
import com.tcn.meetandnote.entity.UserRoom;
import com.tcn.meetandnote.exception.ConflictException;
import com.tcn.meetandnote.exception.NotFoundException;
import com.tcn.meetandnote.repository.UserRepository;
import com.tcn.meetandnote.services.BaseService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService<User, Long> {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RoomService roomService;
    private final UserRoomService userRoomService;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoomService roomService, UserRoomService userRoomService, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        super(userRepository, "user");
        this.userRepository = userRepository;
        this.roomService = roomService;
        this.userRoomService = userRoomService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO saveDTO(User user) {
        if(isUsernameExists(user.getEmail())) {
            throw new ConflictException("Email has already use. Please enter another email");
        }

        // Save room
        Room room = new Room();
        room.setTitle("Home");
        room = roomService.save(room);

        // Save user
        user.setPassword(encodePassword(user.getPassword()));
        user.setRoom(room);
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
    protected User update(Long aLong, User model) {
        return null;
    }

    public UserDTO getUserByUsernameAndConvertDTO(String username) {
        User user =  userRepository.findUserByUsername(username)
                .orElseThrow(() -> new NotFoundException("Not found user with username or password above"));
        UserDTO userResult = modelMapper.map(user, UserDTO.class);
        userResult.setRoomLink(user.getRoom().getLink());
        return modelMapper.map(userResult, UserDTO.class);
    }

    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Email or Password are not correct!"));
    }

    @Override
    public void delete(Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Not found user with id: " + id));
        roomService.delete(user.getRoom().getLink(), user.getRoom().getFullPermissionToken());
        userRepository.delete(user);
    }

    private boolean isUsernameExists(String username) {
        return userRepository.findUserByUsername(username).isPresent();
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
