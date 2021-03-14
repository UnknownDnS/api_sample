package com.unknown.api.controller;

import com.unknown.api.dto.UserDto;
import com.unknown.api.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.unknown.api.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @GetMapping("/v1/users")
    public List<UserDto> getUserList(){
        List<UserEntity> users = userRepository.findAll();
        List<UserDto> userDtos = users.stream().map(UserDto::fromEntity).collect(Collectors.toList());
        log.info("GET /v1/users");
        return userDtos;
    }

    @GetMapping("/v1/user/{userId}")
    public UserDto getUser(@PathVariable("userId") String userId){
        UserEntity user = userRepository.findByUserId(userId);
        UserDto dto = modelMapper.map(user, UserDto.class);
        log.info("GET /v1/user/{}",userId);
        return dto;
    }

    @PostMapping("/v1/user")
    public UserDto createUser(@RequestBody UserDto userDto){
        UserEntity user = modelMapper.map(userDto,UserEntity.class);
        userRepository.save(user);
        log.info("POST /v1/user");
        return userDto;
    }

    @PutMapping("/v1/user")
    public UserDto editUser(@RequestBody UserDto userDto){
        UserEntity user = userRepository.findByUserId(userDto.getUserId());
        user.setName(userDto.getName());
        if(userDto.getPassword() != null){
            user.setPassword(userDto.getPassword());
        }
        userRepository.save(user);
        log.info("PUT /v1/user");
        return userDto;
    }
}
