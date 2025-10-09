using App.DTOs.User;

namespace App.Services.User
{
    public interface IUserService<TUserResponse, in TUserCreate, in TUserUpdate>
    {
        Task<ResponseUserDTO> CreateUserAsync(CreateUserDTO userDTO);
        Task DeleteUserAsync(Guid oid);
        Task<List<ResponseUserDTO>> GetAllUsers();
        Task<ResponseUserDTO> GetUserByIdAsync(Guid oid);
        Task UpdateUserAsync(UpdateUserDTO updateUser);
    }
}