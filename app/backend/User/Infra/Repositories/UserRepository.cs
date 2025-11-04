using Domain.Repositories;
using Infra.Exceptions;
using MongoDB.Driver;

namespace Infra.Repositories;

public class UserRepository<TEntity> : IUserRepository<TEntity>
{
    private readonly IMongoCollection<TEntity> _collection;

    public UserRepository(IMongoDatabase database, string collectionName)
    {
        _collection = database.GetCollection<TEntity>(collectionName);
    }

    public async Task<TEntity> GetByIdAsync(Guid oid)
    {
        var filter = Builders<TEntity>.Filter.Eq("_id", oid);
        var entity = await _collection.Find(filter).FirstOrDefaultAsync();
        if (entity == null) throw new InfraException($"{typeof(TEntity).Name} with id {oid} not found");
        return entity;
    }

    public async Task<List<TEntity>> GetAllAsync()
    {
        var filter = Builders<TEntity>.Filter.Empty;
        var sort = Builders<TEntity>.Sort.Descending("created_at");
        var entities = await _collection.Find(filter).Sort(sort).ToListAsync();
        if (entities == null || entities.Count == 0) throw new InfraException($"No {typeof(TEntity).Name} found");
        return entities;
    }

    public async Task<TEntity> CreateAsync(TEntity entity)
    {
        await _collection.InsertOneAsync(entity);
        return entity;
    }

    public async Task UpdateAsync(TEntity entity)
    {
        var oid = GetOid(entity);
        var filter = Builders<TEntity>.Filter.Eq("_id", oid);
        var result = await _collection.ReplaceOneAsync(filter, entity);
        if (result.ModifiedCount == 0) throw new InfraException($"{typeof(TEntity).Name} not found for update");
    }

    public async Task DeleteAsync(Guid oid)
    {
        var filter = Builders<TEntity>.Filter.Eq("_id", oid);
        var result = await _collection.DeleteOneAsync(filter);
        if (result.DeletedCount == 0) throw new InfraException($"{typeof(TEntity).Name} not found for deletion");
    }

    private Guid GetOid(TEntity entity)
    {
        var property = typeof(TEntity).GetProperty("Oid");
        return (Guid)property.GetValue(entity);
    }
}