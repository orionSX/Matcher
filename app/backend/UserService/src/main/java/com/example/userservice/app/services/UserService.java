package com.example.userservice.app.services;

import com.example.userservice.app.dtos.auth.AuthResponseDTO;
import com.example.userservice.app.dtos.auth.LoginDTO;
import com.example.userservice.app.dtos.auth.RegisterDTO;
import com.example.userservice.app.dtos.user.CreateUserDTO;
import com.example.userservice.app.dtos.user.ResponseUserDTO;
import com.example.userservice.app.dtos.user.UpdateUserDTO;
import com.example.userservice.app.mappers.UserMapper;
import com.example.userservice.domain.exceptions.DomainException;
import com.example.userservice.domain.models.*;
import com.example.userservice.domain.repositories.UserRepository;
import com.example.userservice.infra.exceptions.InfraException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Override
    public AuthResponseDTO register(RegisterDTO dto) {
        // Check if user already exists
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new InfraException("User with email " + dto.getEmail() + " already exists");
        }

        // Create new user with DEFAULT type
        UUID oid = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now();
        
        BaseUser user = DefaultUser.create(
            oid, 
            createdAt, 
            dto.getNickname(), 
            dto.getEmail(), 
            dto.getPassword(),
            UserType.DEFAULT, 
            "", 
            0, 
            new HashMap<>()
        );
        
        BaseUser saved = userRepository.save(user);
        notifierRestClient.createUserInNotifier(saved.getOid().toString(), saved.getNickname());
        
        return new AuthResponseDTO(
            saved.getOid(), 
            saved.getNickname(), 
            saved.getEmail(), 
            "User registered successfully"
        );
    }

    @Override
    public AuthResponseDTO login(LoginDTO dto) {
        BaseUser user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new InfraException("Invalid email or password"));

        if (!user.getPassword().equals(dto.getPassword())) {
            throw new InfraException("Invalid email or password");
        }

        return new AuthResponseDTO(
            user.getOid(), 
            user.getNickname(), 
            user.getEmail(), 
            "Login successful"
        );
    }

    @Override
    public ResponseUserDTO updateNickname(UUID userId, String nickname) {
        BaseUser user = userRepository.findByOid(userId)
                .orElseThrow(() -> new InfraException("User not found with id: " + userId));

        BaseUser updatedUser = switch (user.getType()) {
            case DEFAULT -> DefaultUser.update(
                (DefaultUser) user, nickname, user.getEmail(), user.getPassword(), 
                user.getType(), user.getGender(), user.getAge(), user.getSocials()
            );
            case PLAYER -> PlayerUser.update(
                (PlayerUser) user, nickname, user.getEmail(), user.getPassword(), 
                user.getType(), user.getGender(), user.getAge(), user.getSocials(),
                ((PlayerUser) user).getAccounts(), ((PlayerUser) user).getRoles()
            );
            case MEDIA -> MediaUser.update(
                (MediaUser) user, nickname, user.getEmail(), user.getPassword(), 
                user.getType(), user.getGender(), user.getAge(), user.getSocials(),
                ((MediaUser) user).getMediaLinks()
            );
        };

        return userMapper.toDTO(userRepository.save(updatedUser));
    }

    @Override
    public ResponseUserDTO updateEmail(UUID userId, String email) {
        BaseUser user = userRepository.findByOid(userId)
                .orElseThrow(() -> new InfraException("User not found with id: " + userId));

        BaseUser updatedUser = switch (user.getType()) {
            case DEFAULT -> DefaultUser.update(
                (DefaultUser) user, user.getNickname(), email, user.getPassword(), 
                user.getType(), user.getGender(), user.getAge(), user.getSocials()
            );
            case PLAYER -> PlayerUser.update(
                (PlayerUser) user, user.getNickname(), email, user.getPassword(), 
                user.getType(), user.getGender(), user.getAge(), user.getSocials(),
                ((PlayerUser) user).getAccounts(), ((PlayerUser) user).getRoles()
            );
            case MEDIA -> MediaUser.update(
                (MediaUser) user, user.getNickname(), email, user.getPassword(), 
                user.getType(), user.getGender(), user.getAge(), user.getSocials(),
                ((MediaUser) user).getMediaLinks()
            );
        };

        return userMapper.toDTO(userRepository.save(updatedUser));
    }

    @Override
    public ResponseUserDTO updatePassword(UUID userId, String password) {
        BaseUser user = userRepository.findByOid(userId)
                .orElseThrow(() -> new InfraException("User not found with id: " + userId));

        BaseUser updatedUser = switch (user.getType()) {
            case DEFAULT -> DefaultUser.update(
                (DefaultUser) user, user.getNickname(), user.getEmail(), password, 
                user.getType(), user.getGender(), user.getAge(), user.getSocials()
            );
            case PLAYER -> PlayerUser.update(
                (PlayerUser) user, user.getNickname(), user.getEmail(), password, 
                user.getType(), user.getGender(), user.getAge(), user.getSocials(),
                ((PlayerUser) user).getAccounts(), ((PlayerUser) user).getRoles()
            );
            case MEDIA -> MediaUser.update(
                (MediaUser) user, user.getNickname(), user.getEmail(), password, 
                user.getType(), user.getGender(), user.getAge(), user.getSocials(),
                ((MediaUser) user).getMediaLinks()
            );
        };

        return userMapper.toDTO(userRepository.save(updatedUser));
    }

    @Override
    public ResponseUserDTO updateGender(UUID userId, String gender) {
        BaseUser user = userRepository.findByOid(userId)
                .orElseThrow(() -> new InfraException("User not found with id: " + userId));

        BaseUser updatedUser = switch (user.getType()) {
            case DEFAULT -> DefaultUser.update(
                (DefaultUser) user, user.getNickname(), user.getEmail(), user.getPassword(), 
                user.getType(), gender, user.getAge(), user.getSocials()
            );
            case PLAYER -> PlayerUser.update(
                (PlayerUser) user, user.getNickname(), user.getEmail(), user.getPassword(), 
                user.getType(), gender, user.getAge(), user.getSocials(),
                ((PlayerUser) user).getAccounts(), ((PlayerUser) user).getRoles()
            );
            case MEDIA -> MediaUser.update(
                (MediaUser) user, user.getNickname(), user.getEmail(), user.getPassword(), 
                user.getType(), gender, user.getAge(), user.getSocials(),
                ((MediaUser) user).getMediaLinks()
            );
        };

        return userMapper.toDTO(userRepository.save(updatedUser));
    }

    @Override
    public ResponseUserDTO updateAge(UUID userId, Integer age) {
        BaseUser user = userRepository.findByOid(userId)
                .orElseThrow(() -> new InfraException("User not found with id: " + userId));

        BaseUser updatedUser = switch (user.getType()) {
            case DEFAULT -> DefaultUser.update(
                (DefaultUser) user, user.getNickname(), user.getEmail(), user.getPassword(), 
                user.getType(), user.getGender(), age, user.getSocials()
            );
            case PLAYER -> PlayerUser.update(
                (PlayerUser) user, user.getNickname(), user.getEmail(), user.getPassword(), 
                user.getType(), user.getGender(), age, user.getSocials(),
                ((PlayerUser) user).getAccounts(), ((PlayerUser) user).getRoles()
            );
            case MEDIA -> MediaUser.update(
                (MediaUser) user, user.getNickname(), user.getEmail(), user.getPassword(), 
                user.getType(), user.getGender(), age, user.getSocials(),
                ((MediaUser) user).getMediaLinks()
            );
        };

        return userMapper.toDTO(userRepository.save(updatedUser));
    }

    @Override
    public ResponseUserDTO updateSocials(UUID userId, Map<String, Social> socials) {
        BaseUser user = userRepository.findByOid(userId)
                .orElseThrow(() -> new InfraException("User not found with id: " + userId));

        BaseUser updatedUser = switch (user.getType()) {
            case DEFAULT -> DefaultUser.update(
                (DefaultUser) user, user.getNickname(), user.getEmail(), user.getPassword(), 
                user.getType(), user.getGender(), user.getAge(), socials
            );
            case PLAYER -> PlayerUser.update(
                (PlayerUser) user, user.getNickname(), user.getEmail(), user.getPassword(), 
                user.getType(), user.getGender(), user.getAge(), socials,
                ((PlayerUser) user).getAccounts(), ((PlayerUser) user).getRoles()
            );
            case MEDIA -> MediaUser.update(
                (MediaUser) user, user.getNickname(), user.getEmail(), user.getPassword(), 
                user.getType(), user.getGender(), user.getAge(), socials,
                ((MediaUser) user).getMediaLinks()
            );
        };

        return userMapper.toDTO(userRepository.save(updatedUser));
    }
}
