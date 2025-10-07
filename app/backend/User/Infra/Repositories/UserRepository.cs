using Domain.Repositories;
using Infra.Exceptions;
using MongoDB.Driver;
using UserEntity = Infra.Entities.User;

namespace Infra.Repositories;

public class UserRepository : IUserRepository<UserEntity>
{
    private readonly IMongoCollection<UserEntity> _collection;

    public UserRepository(IMongoDatabase database)
    {
        _collection = database.GetCollection<UserEntity>("users");
    }

    public async Task<UserEntity> GetbyIdAsync(Guid oid)
    {
        var filter = Builders<UserEntity>.Filter.Eq(x => x.Oid, oid);
        var entity = await _collection.Find(filter).FirstOrDefaultAsync();
        if (entity == null) throw new InfraException("Not found");
        return entity;
    }

    public async Task<List<UserEntity>> GetAll()
    {
        var filter = Builders<UserEntity>.Filter.Empty;

        var sort = Builders<UserEntity>.Sort.Descending(x => x.CreatedAt);
        var entities = await _collection.Find(filter).Sort(sort).ToListAsync();
        if (entities == null || entities.Count == 0) throw new InfraException("Not found");
        return entities;
    }

    public async Task<UserEntity> CreateAsync(UserEntity entity)
    {
        // FROM TEMPLATE: TODO find a way to track if its inserted
        await _collection.InsertOneAsync(entity);

        return entity;
    }

    public async Task UpdateAsync(UserEntity entity)
    {
        var filter = Builders<UserEntity>.Filter.Eq(x => x.Oid, entity.Oid);
        var result = await _collection.ReplaceOneAsync(filter, entity);
        if (result.ModifiedCount == 0) throw new InfraException("Not found");
    }


    public async Task DeleteAsync(Guid oid)
    {
        var filter = Builders<UserEntity>.Filter.Eq(x => x.Oid, oid);
        var result = await _collection.DeleteOneAsync(filter);
        if (result.DeletedCount == 0) throw new InfraException("Not found");
    }


}