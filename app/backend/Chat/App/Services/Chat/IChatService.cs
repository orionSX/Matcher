namespace App.Services.Chat;

public interface IChatService<TChatResponse, in TChatCreate,in TChatUpdate>
{
    Task<TChatResponse> GetChatByIdAsync(Guid oid);
    Task<List<TChatResponse>> GetChatsByOwnerAsync(string ownerOid);
    Task<List<TChatResponse>> GetAllChats();
    Task<TChatResponse> CreateChatAsync(TChatCreate chat);
    Task UpdateChatAsync(TChatUpdate chat);
    Task DeleteChatAsync(Guid oid);
}
