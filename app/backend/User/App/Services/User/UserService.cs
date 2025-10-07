using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Domain.Repositories;
using UserEntity = Infra.Entities.User;

namespace App.Services.User;

    public class UserService : IUserService<ResponseUserDTO, CreateUserDTO, UpdateUserDTO>
{
    private readonly IUserRepository<UserEntity> _userRepository;

    public UserService (IUserRepository<UserEntity> userRepository)
    {  
        _userRepository = userRepository;
    }

    public async Task<ResponseUserDTO> GetUserByIdAsync(Guid oid)
    {
        var user = await _userRepository.GetByIdAsync(oid);

        return UserMapper.ToResponseDTO(user);

    }












}


