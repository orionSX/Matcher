using Domain.Repositories;
using Infra.Exceptions;
using MongoDB.Driver;
using EntityMessage = Infra.Entities.Message;

namespace Infra.Repositories;

public class MessageRepository : IMessageRepository<EntityMessage>
{
    private readonly IMongoCollection<EntityMessage> _collection;

    public MessageRepository(IMongoDatabase database)
    {
        _collection = database.GetCollection<EntityMessage>("messages");
    }

    public async Task<EntityMessage> GetByIdAsync(Guid oid)
    {
        var filter = Builders<EntityMessage>.Filter.Eq(x => x.Oid, oid);
        var entity = await _collection.Find(filter).FirstOrDefaultAsync();
        if (entity == null) throw new InfraException("Not found");
        return entity;
    }

    public async Task<List<EntityMessage>> GetByChatIdAsync(Guid chatId)
    {
        var filter = Builders<EntityMessage>.Filter.Eq(x => x.ChatId, chatId);
        var sort = Builders<EntityMessage>.Sort.Descending(x => x.CreatedAt);
        var entities = await _collection.Find(filter).Sort(sort).ToListAsync();
        if (entities == null || entities.Count == 0) throw new InfraException("Not found");
        return entities;
    }

    public async Task<EntityMessage> CreateAsync(EntityMessage message)
    {
        await _collection.InsertOneAsync(message);
        return message;
    }

    public async Task UpdateAsync(EntityMessage message)
    {
        var filter = Builders<EntityMessage>.Filter.Eq(x => x.Oid, message.Oid);
        var result = await _collection.ReplaceOneAsync(filter, message);
        if (result.ModifiedCount == 0) throw new InfraException("Not found");
    }

    public async Task DeleteAsync(Guid oid)
    {
        var filter = Builders<EntityMessage>.Filter.Eq(x => x.Oid, oid);
        var result = await _collection.DeleteOneAsync(filter);
        if (result.DeletedCount == 0) throw new InfraException("Not found");
    }

    public async Task<List<EntityMessage>> GetByAuthorAsync(Guid authorId)
    {
        var filter = Builders<EntityMessage>.Filter.Eq(x => x.CreatorId, authorId);
        var sort = Builders<EntityMessage>.Sort.Descending(x => x.CreatedAt);
        var entities = await _collection.Find(filter).Sort(sort).ToListAsync();
        if (entities == null || entities.Count == 0) throw new InfraException("Not found");
        return entities;
    }
}