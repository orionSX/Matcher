package com.example.userservice.app.mappers;

import com.example.userservice.app.dtos.user.CreateUserDTO;
import com.example.userservice.app.dtos.user.ResponseUserDTO;
import com.example.userservice.app.dtos.user.UpdateUserDTO;
import com.example.userservice.domain.models.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@Component
public class UserMapper {

    public BaseUser toDomain(CreateUserDTO dto) {
        UUID oid = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now();

        Integer age = dto.getAge() != null ? dto.getAge() : 0;
        String password = dto.getPassword() != null ? dto.getPassword() : "";

        return switch (dto.getType()) {
            case DEFAULT -> DefaultUser.create(oid, createdAt, dto.getNickname(), dto.getEmail(), password, dto.getType(), dto.getGender(), age, dto.getSocials());
            case PLAYER -> PlayerUser.create(oid, createdAt, dto.getNickname(), dto.getEmail(), password, dto.getType(), dto.getGender(), age, dto.getSocials(), dto.getAccounts(), dto.getRoles());
            case MEDIA -> MediaUser.create(oid, createdAt, dto.getNickname(), dto.getEmail(), password, dto.getType(), dto.getGender(), age, dto.getSocials(), dto.getMediaLinks());
        };
    }

    public ResponseUserDTO toDTO(BaseUser user) {
        ResponseUserDTO dto = new ResponseUserDTO();
        dto.setOid(user.getOid());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setNickname(user.getNickname());
        dto.setEmail(user.getEmail());
        dto.setType(user.getType());
        dto.setGender(user.getGender());
        dto.setAge(user.getAge());
        dto.setSocials(user.getSocials());

        if (user instanceof PlayerUser playerUser) {
            dto.setAccounts(playerUser.getAccounts());
            dto.setRoles(playerUser.getRoles());
        } else if (user instanceof MediaUser mediaUser) {
            dto.setMediaLinks(mediaUser.getMediaLinks());
        }
        return dto;
    }

    public BaseUser updateDomain(BaseUser existingUser, UpdateUserDTO dto) {
        return switch (existingUser.getType()) {
            case DEFAULT -> DefaultUser.update(
                    (DefaultUser) existingUser,
                    dto.getNickname() != null ? dto.getNickname() : existingUser.getNickname(),
                    dto.getEmail() != null ? dto.getEmail() : existingUser.getEmail(),
                    dto.getPassword() != null ? dto.getPassword() : existingUser.getPassword(),
                    dto.getType() != null ? dto.getType() : existingUser.getType(),
                    dto.getGender() != null ? dto.getGender() : existingUser.getGender(),
                    dto.getAge() != null ? dto.getAge() : existingUser.getAge(),
                    dto.getSocials() != null ? dto.getSocials() : existingUser.getSocials()
            );
            case PLAYER -> PlayerUser.update(
                    (PlayerUser) existingUser,
                    dto.getNickname() != null ? dto.getNickname() : existingUser.getNickname(),
                    dto.getEmail() != null ? dto.getEmail() : existingUser.getEmail(),
                    dto.getPassword() != null ? dto.getPassword() : existingUser.getPassword(),
                    dto.getType() != null ? dto.getType() : existingUser.getType(),
                    dto.getGender() != null ? dto.getGender() : existingUser.getGender(),
                    dto.getAge() != null ? dto.getAge() : existingUser.getAge(),
                    dto.getSocials() != null ? dto.getSocials() : existingUser.getSocials(),
                    dto.getAccounts() != null ? dto.getAccounts() : ((PlayerUser) existingUser).getAccounts(),
                    dto.getRoles() != null ? dto.getRoles() : ((PlayerUser) existingUser).getRoles()
            );
            case MEDIA -> MediaUser.update(
                    (MediaUser) existingUser,
                    dto.getNickname() != null ? dto.getNickname() : existingUser.getNickname(),
                    dto.getEmail() != null ? dto.getEmail() : existingUser.getEmail(),
                    dto.getPassword() != null ? dto.getPassword() : existingUser.getPassword(),
                    dto.getType() != null ? dto.getType() : existingUser.getType(),
                    dto.getGender() != null ? dto.getGender() : existingUser.getGender(),
                    dto.getAge() != null ? dto.getAge() : existingUser.getAge(),
                    dto.getSocials() != null ? dto.getSocials() : existingUser.getSocials(),
                    dto.getMediaLinks() != null ? dto.getMediaLinks() : ((MediaUser) existingUser).getMediaLinks()
            );
        };
    }
}
