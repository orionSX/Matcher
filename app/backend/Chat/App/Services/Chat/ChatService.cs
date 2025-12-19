using App.DTOs.Chat;
using App.Mappers;
using Domain.Repositories;
using ChatEntity = Infra.Entities.Chat;

namespace App.Services.Chat;

public class ChatService : IChatService<ResponseChatDTO, CreateChatDTO, UpdateChatDTO>
{
    private readonly IChatRepository<ChatEntity> _chatRepository;

    public ChatService(IChatRepository<ChatEntity> chatRepository)
    {
        _chatRepository = chatRepository;
    }

    public async Task<ResponseChatDTO> GetChatByIdAsync(Guid oid)
    {
        var chat = await _chatRepository.GetByIdAsync(oid);

        return ChatMapper.ToResponseDTO(chat);
    }

    public async Task<List<ResponseChatDTO>> GetChatsByOwnerAsync(string ownerOid)
    {
        var chats = await _chatRepository.GetByOwnerAsync(ownerOid);

        return chats.Select(ChatMapper.ToResponseDTO).ToList();
    }

    public async Task<List<ResponseChatDTO>> GetAllChats()
    {
        var chats = await _chatRepository.GetAll();

        return chats.Select(ChatMapper.ToResponseDTO).ToList();
    }

    public async Task<ResponseChatDTO> CreateChatAsync(CreateChatDTO chatDTO)
    {
        var chat = Domain.Models.Chat.Create(Guid.NewGuid(), DateTime.UtcNow, chatDTO.Title, chatDTO.Owner, chatDTO.Type);

        return ChatMapper.ToResponseDTO(await _chatRepository.CreateAsync(ChatMapper.ToEntity(chat)));
    }

    public async Task UpdateChatAsync(UpdateChatDTO updateChat)
    {
        var oldChat = await _chatRepository.GetByIdAsync(updateChat.Oid);
        var newChat = Domain.Models.Chat.Update(ChatMapper.ToDomain(oldChat), updateChat.Title);
        await _chatRepository.UpdateAsync(ChatMapper.ToEntity(newChat));
    }

    public async Task DeleteChatAsync(Guid oid)
    {
        await _chatRepository.DeleteAsync(oid);
    }
}
