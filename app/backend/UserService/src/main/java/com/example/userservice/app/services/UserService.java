package com.example.userservice.app.services;

import com.example.userservice.app.dtos.user.CreateUserDTO;
import com.example.userservice.app.dtos.user.ResponseUserDTO;
import com.example.userservice.app.dtos.user.UpdateUserDTO;
import com.example.userservice.app.mappers.UserMapper;
import com.example.userservice.domain.exceptions.DomainException;
import com.example.userservice.domain.models.BaseUser;
import com.example.userservice.domain.models.UserType;
import com.example.userservice.domain.repositories.UserRepository;
import com.example.userservice.infra.exceptions.InfraException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final NotifierRestClient notifierRestClient;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.notifierRestClient = new NotifierRestClient();
    }

    @Override
    public List<ResponseUserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResponseUserDTO> getUsersByType(UserType type) {
        return userRepository.findByType(type).stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseUserDTO getUserById(UUID oid) {
        return userRepository.findByOid(oid)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new InfraException("User not found with id: " + oid));
    }

    @Override
    public ResponseUserDTO createUser(CreateUserDTO dto) {
        BaseUser user = userMapper.toDomain(dto);
        BaseUser saved = userRepository.save(user);
        // Создать пользователя в NotificatorService
        notifierRestClient.createUserInNotifier(saved.getOid().toString(), saved.getNickname());
        return userMapper.toDTO(saved);
    }

    @Override
    public ResponseUserDTO updateUser(UpdateUserDTO dto) {
        BaseUser existingUser = userRepository.findByOid(dto.getOid())
                .orElseThrow(() -> new InfraException("User not found with id: " + dto.getOid()));

        BaseUser updatedUser = userMapper.updateDomain(existingUser, dto);

        return userMapper.toDTO(userRepository.save(updatedUser));
    }

    @Override
    public void deleteUser(UUID oid) {
        if (!userRepository.existsById(oid)) {
            throw new InfraException("User not found with id: " + oid);
        }
        userRepository.deleteById(oid);
    }
}
