namespace Domain.Repositories;

public interface IUserRepository<TUser>
{
    Task<TUser> GetByIdAsync(Guid oid);
    Task<List<TUser>> GetAll();
    Task<TUser> CreateAsync(TUser chat);
    Task UpdateAsync(TUser chat);
    Task DeleteAsync(Guid oid);
}