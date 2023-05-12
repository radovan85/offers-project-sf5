package com.radovan.spring.service;

import java.util.List;

import com.radovan.spring.dto.UserDto;
import com.radovan.spring.entity.UserEntity;



public interface UserService {

	UserDto addUser(UserDto user);
    void deleteUser(Integer id);
    UserEntity getUserById(Integer id);
    List<UserDto> listAllUsers();
    UserDto getUser(Integer id);
    UserEntity getUserByEmail(String email);
    List<UserEntity>listAll();
    UserDto getCurrentUser();
    void suspendUser(Integer participantId);
    
    
    
}
