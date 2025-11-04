using App.DTOs.User;
using static Domain.Models.Base;

namespace App.Services.User;

public interface IUserService
{
    Task<ResponseUserDTO> GetUserByIdAsync(Guid oid);
    Task<List<ResponseUserDTO>> GetAllUsersAsync();
    Task<List<ResponseUserDTO>> GetUsersByTypeAsync(UserType type);
    Task<ResponseUserDTO> CreateUserAsync(CreateUserDTO userDTO);
    Task<ResponseUserDTO> UpdateUserAsync(UpdateUserDTO userDTO);
    Task DeleteUserAsync(Guid oid);
}