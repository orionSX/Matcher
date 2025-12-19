package com.example.userservice.app.services;

import com.example.userservice.app.dtos.user.CreateUserDTO;
import com.example.userservice.app.dtos.user.ResponseUserDTO;
import com.example.userservice.app.dtos.user.UpdateUserDTO;
import com.example.userservice.domain.models.UserType;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    List<ResponseUserDTO> getAllUsers();
    List<ResponseUserDTO> getUsersByType(UserType type);
    ResponseUserDTO getUserById(UUID oid);
    ResponseUserDTO createUser(CreateUserDTO dto);
    ResponseUserDTO updateUser(UpdateUserDTO dto);
    void deleteUser(UUID oid);
}
