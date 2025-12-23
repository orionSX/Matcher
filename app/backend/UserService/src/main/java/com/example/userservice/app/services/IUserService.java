package com.example.userservice.app.services;

import com.example.userservice.app.dtos.auth.AuthResponseDTO;
import com.example.userservice.app.dtos.auth.LoginDTO;
import com.example.userservice.app.dtos.auth.RegisterDTO;
import com.example.userservice.app.dtos.user.*;
import com.example.userservice.domain.models.Social;
import com.example.userservice.domain.models.UserType;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IUserService {
    List<ResponseUserDTO> getAllUsers();
    List<ResponseUserDTO> getUsersByType(UserType type);
    ResponseUserDTO getUserById(UUID oid);
    ResponseUserDTO createUser(CreateUserDTO dto);
    ResponseUserDTO updateUser(UpdateUserDTO dto);
    void deleteUser(UUID oid);
    
    // Auth methods
    AuthResponseDTO register(RegisterDTO dto);
    AuthResponseDTO login(LoginDTO dto);
    
    // Individual field update methods
    ResponseUserDTO updateNickname(UUID userId, String nickname);
    ResponseUserDTO updateEmail(UUID userId, String email);
    ResponseUserDTO updatePassword(UUID userId, String password);
    ResponseUserDTO updateGender(UUID userId, String gender);
    ResponseUserDTO updateAge(UUID userId, Integer age);
    ResponseUserDTO updateSocials(UUID userId, Map<String, Social> socials);
}
