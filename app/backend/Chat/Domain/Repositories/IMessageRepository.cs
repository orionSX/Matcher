namespace Domain.Repositories;

public interface IMessageRepository<TMessage>
{
    Task<TMessage> GetByIdAsync(Guid oid);
    Task<List<TMessage>> GetByChatIdAsync(Guid chatId);
    Task<TMessage> CreateAsync(TMessage message);
    Task UpdateAsync(TMessage message);
    Task DeleteAsync(Guid oid);
    Task<List<TMessage>> GetByAuthorAsync(Guid authorId);
}