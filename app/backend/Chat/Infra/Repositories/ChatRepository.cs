using Domain.Repositories;
using Infra.Exceptions;
using MongoDB.Driver;
using ChatEntity = Infra.Entities.Chat;

namespace Infra.Repositories;

public class ChatRepository : IChatRepository<ChatEntity>
{
    private readonly IMongoCollection<ChatEntity> _collection;

    public ChatRepository(IMongoDatabase database)
    {
        _collection = database.GetCollection<ChatEntity>("chats");
    }

    public async Task<ChatEntity> GetByIdAsync(Guid oid)
    {
        var filter = Builders<ChatEntity>.Filter.Eq(x => x.Oid, oid);
        var entity = await _collection.Find(filter).FirstOrDefaultAsync();
        if (entity == null) throw new InfraException("Not found");
        return entity;
    }

    public async Task<List<ChatEntity>> GetByOwnerAsync(string ownerOid)
    {
        var filter = Builders<ChatEntity>.Filter.Eq(x => x.Owner.Oid, ownerOid);
        var sort = Builders<ChatEntity>.Sort.Descending(x => x.CreatedAt);
        var entities = await _collection.Find(filter).Sort(sort).ToListAsync();
        if (entities == null || entities.Count == 0) throw new InfraException("Not found");
        return entities;
    }

    public async Task<List<ChatEntity>> GetAll()
    {
        var filter = Builders<ChatEntity>.Filter.Empty;

        var sort = Builders<ChatEntity>.Sort.Descending(x => x.CreatedAt);
        var entities = await _collection.Find(filter).Sort(sort).ToListAsync();
        if (entities == null || entities.Count == 0) throw new InfraException("Not found");
        return entities;
    }

    public async Task<ChatEntity> CreateAsync(ChatEntity entity)
    {
        // TODO find a way to track if its inserted
        await _collection.InsertOneAsync(entity);

        return entity;
    }

    public async Task UpdateAsync(ChatEntity entity)
    {
        var filter = Builders<ChatEntity>.Filter.Eq(x => x.Oid, entity.Oid);
        var result = await _collection.ReplaceOneAsync(filter, entity);
        if (result.ModifiedCount == 0) throw new InfraException("Not found");
    }

    public async Task DeleteAsync(Guid oid)
    {
        var filter = Builders<ChatEntity>.Filter.Eq(x => x.Oid, oid);
        var result = await _collection.DeleteOneAsync(filter);
        if (result.DeletedCount == 0) throw new InfraException("Not found");
    }
}