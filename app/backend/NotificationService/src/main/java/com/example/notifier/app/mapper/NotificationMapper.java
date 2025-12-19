package com.example.notifier.app.mapper;

import com.example.notifier.api.dto.NotificationResponseDTO;
import com.example.notifier.domain.Notification;
import org.mapstruct.Mapper;

import java.util.List;

import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mappings({
        @Mapping(source = "userId", target = "userId"),
        @Mapping(source = "telegramChatId", target = "telegramChatId")
    })
    NotificationResponseDTO toDto(Notification notification);

    List<NotificationResponseDTO> toDtoList(List<Notification> notifications);
}