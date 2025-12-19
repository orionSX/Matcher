using App.DTOs.Chat;
using Domain.Models;
using Domain.Values;
using ChatOwnerEntity = Infra.Entities.ChatOwner;

namespace App.Mappers;

public static class ChatMapper
{
    public static Chat ToDomain(Infra.Entities.Chat entity)
    {
        var owner = new ChatOwner
        {
            Oid = Guid.Parse(entity.Owner.Oid),
            Type = entity.Owner.Type
        };

        var chat = Chat.Create(
            entity.Oid,
            entity.CreatedAt,
            entity.Title,
            owner,
            entity.Type
        );

        return chat;
    }

    public static ResponseChatDTO ToResponseDTO(Infra.Entities.Chat entity)
    {
        var chatDTO = new ResponseChatDTO
        {
            Oid = entity.Oid,
            CreatedAt = entity.CreatedAt,
            Title = entity.Title,
            Owner = new ChatOwner
            {
                Oid = Guid.Parse(entity.Owner.Oid),
                Type = entity.Owner.Type
            },
            Type = entity.Type
        };
        return chatDTO;
    }

    public static Infra.Entities.Chat ToEntity(Chat domain)
    {
        return new Infra.Entities.Chat
        {
            Oid = domain.Oid,
            CreatedAt = domain.CreatedAt,
            Title = domain.Title,

            Type = domain.Type,
            Owner = new ChatOwnerEntity
                { Oid = domain.Owner.Oid.ToString(), Type = domain.Owner.Type }
        };
    }
}