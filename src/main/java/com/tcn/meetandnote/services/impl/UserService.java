package com.tcn.meetandnote.services.impl;

import com.tcn.meetandnote.dto.RoomDTO;
import com.tcn.meetandnote.dto.UserDTO;
import com.tcn.meetandnote.entity.Room;
import com.tcn.meetandnote.entity.User;
import com.tcn.meetandnote.entity.UserRoom;
import com.tcn.meetandnote.exception.ConflictException;
import com.tcn.meetandnote.exception.NotFoundException;
import com.tcn.meetandnote.repository.UserRepository;
import com.tcn.meetandnote.services.BaseService;
import com.tcn.meetandnote.utils.FileUploadUtils;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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

        // Save room link
        UserRoom userRoom = new UserRoom();
        userRoom.setRead(true);
        userRoom.setFullPermission(true);
        userRoom.setOwner(true);
        userRoom.setRoom(room);
        userRoom.setUser(user);
        userRoom = userRoomService.save(userRoom);

        return modelMapper.map(user, UserDTO.class);
    }

    public UserDTO addRoom(long roomId, long userId) {
        User user = getSingleResultById(userId);
        Room room = new Room(roomId);

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

    public List<UserDTO> getUsersByRoomId(long roomId) {
        List<UserDTO> userDTOS = new ArrayList<>();
        try {
            List<User> users = userRoomService.getUsersByRoomId(roomId);
            for(User user : users) {
                userDTOS.add(modelMapper.map(user, UserDTO.class));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return userDTOS;
    }

    @Override
    protected User update(Long aLong, User model) {
        return null;
    }

    public UserDTO updateUser(long id, User model) {
        User user = getSingleResultById(id);
        user.setAddress(model.getAddress());
        user.setFirstName(model.getFirstName());
        user.setLastName(model.getLastName());
        return modelMapper.map(userRepository.save(user), UserDTO.class);
    }

    public UserDTO updateImage(long id, MultipartFile imageFile) {
        User user = getSingleResultById(id);
        String imageName = FileUploadUtils.generateFileName(imageFile);
        File file = new File(user.getImagePath());
        if(!file.exists()) {
            try {
                Files.createDirectories(Paths.get(user.getImagePath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String path = file.getAbsolutePath();
        try {
            FileUploadUtils.upload(imageName, path, imageFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        if(user.getAvatar() != null && !user.getAvatar().isBlank()) {
            FileUploadUtils.delete(user.getAvatar());
        }
        user.setAvatar(user.getImagePath() + "/" + imageName);
        return modelMapper.map(userRepository.save(user), UserDTO.class);
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
