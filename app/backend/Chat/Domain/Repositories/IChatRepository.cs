namespace Domain.Repositories;

public interface IChatRepository<TChat>
{
    Task<TChat> GetByIdAsync(Guid oid);
    Task<List<TChat>> GetByOwnerAsync(string ownerOid);
    Task<List<TChat>> GetAll();
    Task<TChat> CreateAsync(TChat chat);
    Task UpdateAsync(TChat chat);
    Task DeleteAsync(Guid oid);
}