using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Domain.Repositories;
using UserEntity = Infra.Entities.User;
using App.DTOs.User;
using App.Mappers;

namespace App.Services.User;

public class UserService : IUserService<ResponseUserDTO, CreateUserDTO, UpdateUserDTO>
{
    private readonly IUserRepository<UserEntity> _userRepository;

    public UserService(IUserRepository<UserEntity> userRepository)
    {
        _userRepository = userRepository;
    }

    public async Task<ResponseUserDTO> GetUserByIdAsync(Guid oid)
    {
        var user = await _userRepository.GetByIdAsync(oid);

        return UserMapper.ToResponseDTO(user);

    }

    public async Task<List<ResponseUserDTO>> GetAllUsers()
    {
        var users = await _userRepository.GetAll();

        return users.Select(UserMapper.ToResponseDTO).ToList();

    }


    public async Task<ResponseUserDTO> CreateUserAsync(CreateUserDTO userDTO)
    {
        var user = Domain.Models.User.Create(Guid.NewGuid(), DateTime.UtcNow, userDTO.Nickname, userDTO.Email);

        return UserMapper.ToResponseDTO(await _userRepository.CreateAsync(UserMapper.ToEntity(user)));

    }

    public async Task UpdateUserAsync(UpdateUserDTO updateUser)
    {
        var oldUser = await _userRepository.GetByIdAsync(updateUser.Oid);
        var newUser = Domain.Models.User.Update(UserMapper.ToDomain(oldUser), updateUser.Nickname, updateUser.Email);
        await _userRepository.UpdateAsync(UserMapper.ToEntity(newUser));
    }

    public async Task DeleteUserAsync(Guid oid)
    {
        await _userRepository.DeleteAsync(oid);
    }

}


