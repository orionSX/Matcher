namespace Domain.Repositories;

public interface IUserRepository<TEntity>
{
    Task<TEntity> GetByIdAsync(Guid oid);
    Task<List<TEntity>> GetAllAsync();
    Task<TEntity> CreateAsync(TEntity entity);
    Task UpdateAsync(TEntity entity);
    Task DeleteAsync(Guid oid);
}