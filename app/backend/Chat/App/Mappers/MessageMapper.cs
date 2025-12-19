using App.DTOs.Message;
using Domain.Models;
using MessageEntity = Infra.Entities.Message;

namespace App.Mappers;

public static class MessageMapper
{
    public static Message ToDomain(MessageEntity entity)
    {
        var readBy = entity
            .ReadBy.Select(rb => new Domain.Values.ReadBy
            {
                 
                UserId = rb.UserId,
                ReadAt = rb.ReadAt,
            })
            .ToList();

        var message = Message.Create(
            entity.Oid,
            entity.CreatedAt,
            entity.Text,
            entity.CreatorId,
            entity.ChatId,
            
            entity.ExpiresAt != default ? entity.ExpiresAt - entity.CreatedAt : null,
            readBy
        );

        return message;
    }

    public static MessageEntity ToEntity(Message domain)
    {
        return new MessageEntity
        {
             
            Oid = domain.Oid,
            CreatedAt = domain.CreatedAt,
            Text = domain.Text,
            CreatorId = domain.CreatorId,
            ChatId = domain.ChatId,
          
            ExpiresAt = domain.ExpiresAt ?? default,
            ReadBy = domain
                .ReadBy.Select(rb => new Infra.Entities.ReadBy
                {
                    UserId = rb.UserId,
                    ReadAt = rb.ReadAt,
                })
                .ToList(),
        };
    }
    public static ResponseMessageDTO ToResponseDTO(MessageEntity entity)
    {
        var readBy = entity
            .ReadBy.Select(rb => new Domain.Values.ReadBy
            {
                 
                UserId = rb.UserId,
                ReadAt = rb.ReadAt,
            })
            .ToList();
        var messageDTO = new ResponseMessageDTO
        {
            Oid = entity.Oid,
            CreatedAt = entity.CreatedAt,
            Text = entity.Text,
            CreatorId = entity.CreatorId,
            ChatId = entity.ChatId,
            ExpiresAt = entity.ExpiresAt ?? default,
            ReadBy = readBy
        };
        
        return messageDTO;
    }
}
