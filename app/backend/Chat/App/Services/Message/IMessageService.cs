namespace App.Services.Message;

public interface IMessageService<TMessageResponse, in TCreateMessage, in TUpdateMessage>
{
    Task<TMessageResponse> GetMessageByIdAsync(Guid oid);
    Task<List<TMessageResponse>> GetMessagesByChatIdAsync(Guid chatId);
    Task<TMessageResponse> CreateMessageAsync(
        TCreateMessage createMessage
    );
    Task UpdateMessageAsync(TUpdateMessage message);
    Task DeleteMessageAsync(Guid oid);
    Task<List<TMessageResponse>> GetMessagesByAuthorAsync(Guid authorId);
}
