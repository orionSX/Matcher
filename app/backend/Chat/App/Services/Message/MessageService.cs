using App.DTOs.Message;
using App.Mappers;
using Domain.Repositories;
using MessageModel = Domain.Models.Message;
using MessageEntity = Infra.Entities.Message;

namespace App.Services.Message;

public class
    MessageService : IMessageService<ResponseMessageDTO, CreateMessageDTO, UpdateMessageDTO>
{
    private readonly IMessageRepository<MessageEntity> _messageRepository;

    public MessageService(IMessageRepository<MessageEntity> messageRepository)
    {
        _messageRepository = messageRepository;
    }

    public async Task<ResponseMessageDTO> GetMessageByIdAsync(Guid oid)
    {
        return MessageMapper.ToResponseDTO(await _messageRepository.GetByIdAsync(oid));
    }

    public async Task<List<ResponseMessageDTO>> GetMessagesByChatIdAsync(Guid chatId)
    {
        var messageEntites = await _messageRepository.GetByChatIdAsync(chatId);
        return messageEntites.Select(MessageMapper.ToResponseDTO).ToList();
    }

    public async Task<ResponseMessageDTO> CreateMessageAsync(CreateMessageDTO createMessage
    )
    {
        var message = MessageModel.Create(IdGenerator.GetId(), DateTime.UtcNow, createMessage.Text,
            createMessage.CreatorId,
            createMessage.ChatId,
            createMessage.Ttl,
            []
        );

        return MessageMapper.ToResponseDTO(
            await _messageRepository.CreateAsync(MessageMapper.ToEntity(message)));
    }

    public async Task UpdateMessageAsync(UpdateMessageDTO updateMessage)
    {
        var oldMessage =
            MessageMapper.ToDomain(await _messageRepository.GetByIdAsync(updateMessage.Oid));
        var newMessage =
            MessageMapper.ToEntity(MessageModel.Update(oldMessage, updateMessage.Text));
        await _messageRepository.UpdateAsync(newMessage);
    }

    public async Task DeleteMessageAsync(Guid oid)
    {
        await _messageRepository.DeleteAsync(oid);
    }

    public async Task<List<ResponseMessageDTO>> GetMessagesByAuthorAsync(Guid authorId)
    {
        var messageEntities = await _messageRepository.GetByAuthorAsync(authorId);

        return messageEntities.Select(MessageMapper.ToResponseDTO).ToList();
    }
}