using App.DTOs.User;
using Domain.Models;
using Domain.Repositories;
using static Domain.Models.Base;

namespace App.Services.User;

public class UserService : IUserService
{
    private readonly IUserRepository<Infra.Entities.UserEntity> _userRepository;

    public UserService(IUserRepository<Infra.Entities.UserEntity> userRepository)
    {
        _userRepository = userRepository;
    }

    public async Task<ResponseUserDTO> GetUserByIdAsync(Guid oid)
    {
        var entity = await _userRepository.GetByIdAsync(oid);
        var domainUser = UserMapper.ToDomain(entity);
        return UserMapper.ToResponseDTO(domainUser);
    }

    public async Task<List<ResponseUserDTO>> GetAllUsersAsync()
    {
        var entities = await _userRepository.GetAllAsync();
        return entities.Select(entity =>
        {
            var domainUser = UserMapper.ToDomain(entity);
            return UserMapper.ToResponseDTO(domainUser);
        }).ToList();
    }

    public async Task<List<ResponseUserDTO>> GetUsersByTypeAsync(UserType type)
    {
        var allEntities = await _userRepository.GetAllAsync();
        var filteredEntities = allEntities.Where(e => e.Type == type).ToList();

        return filteredEntities.Select(entity =>
        {
            var domainUser = UserMapper.ToDomain(entity);
            return UserMapper.ToResponseDTO(domainUser);
        }).ToList();
    }

    public async Task<ResponseUserDTO> CreateUserAsync(CreateUserDTO userDTO)
    {
        try
        {
        Console.WriteLine($"DEBUG: UserService: Creating user with Nickname: '{userDTO.Nickname}'");
        Console.WriteLine($"DEBUG: UserService: User Type: {userDTO.Type}");

        dynamic domainUser = userDTO.Type switch
        {
            UserType.Player => Domain.Models.Player.Create(
                Guid.NewGuid(), DateTime.UtcNow,
                userDTO.Nickname, userDTO.Email, userDTO.Type,
                userDTO.Gender, userDTO.Age, userDTO.Socials,
                userDTO.Accounts ?? Array.Empty<string>(),
                userDTO.Roles ?? Array.Empty<string>()
            ),
            UserType.Media => Domain.Models.Media.Create(
                Guid.NewGuid(), DateTime.UtcNow,
                userDTO.Nickname, userDTO.Email, userDTO.Type,
                userDTO.Gender, userDTO.Age, userDTO.Socials,
                userDTO.MediaLinks ?? Array.Empty<string>()
            ),
            _ => Domain.Models.Default.Create(
                Guid.NewGuid(), DateTime.UtcNow,
                userDTO.Nickname, userDTO.Email, userDTO.Type,
                userDTO.Gender, userDTO.Age, userDTO.Socials
            )
        };

            var entity = UserMapper.ToEntity((object)domainUser);
            var createdEntity = await _userRepository.CreateAsync(entity);
            var createdDomainUser = UserMapper.ToDomain(createdEntity);
            return UserMapper.ToResponseDTO(createdDomainUser);

        }
        catch (Exception ex)
        {
            Console.WriteLine($"DEBUG: UserService: Exception caught!");
            Console.WriteLine($"DEBUG: UserService: Exception type: {ex.GetType().FullName}");
            Console.WriteLine($"DEBUG: UserService: Exception message: {ex.Message}");
            Console.WriteLine($"DEBUG: UserService: Stack trace: {ex.StackTrace}");

            throw;
        }
    }

    public async Task<ResponseUserDTO> UpdateUserAsync(UpdateUserDTO userDTO)
    {
        var existingEntity = await _userRepository.GetByIdAsync(userDTO.Oid);
        var existingDomainUser = UserMapper.ToDomain(existingEntity);

        object updatedDomainUser;

        if (existingDomainUser is Domain.Models.Player existingPlayer)
        {
            updatedDomainUser = Domain.Models.Player.Update(
                existingPlayer,
                userDTO.Nickname ?? existingPlayer.Nickname,
                userDTO.Email ?? existingPlayer.Email,
                userDTO.Type ?? existingPlayer.Type,
                userDTO.Gender ?? existingPlayer.Gender,
                userDTO.Age ?? existingPlayer.Age,
                userDTO.Socials ?? existingPlayer.Socials,
                userDTO.Accounts ?? existingPlayer.Accounts,
                userDTO.Roles ?? existingPlayer.Roles
            );
        }
        else if (existingDomainUser is Domain.Models.Media existingMedia)
        {
            updatedDomainUser = Domain.Models.Media.Update(
                existingMedia,
                userDTO.Nickname ?? existingMedia.Nickname,
                userDTO.Email ?? existingMedia.Email,
                userDTO.Type ?? existingMedia.Type,
                userDTO.Gender ?? existingMedia.Gender,
                userDTO.Age ?? existingMedia.Age,
                userDTO.Socials ?? existingMedia.Socials,
                userDTO.MediaLinks ?? existingMedia.Media_links
            );
        }
        else if (existingDomainUser is Domain.Models.Default existingUser)
        {
            updatedDomainUser = Domain.Models.Default.Update(
                existingUser,
                userDTO.Nickname ?? existingUser.Nickname,
                userDTO.Email ?? existingUser.Email,
                userDTO.Type ?? existingUser.Type,
                userDTO.Gender ?? existingUser.Gender,
                userDTO.Age ?? existingUser.Age,
                userDTO.Socials ?? existingUser.Socials
            );
        }
        else
        {
            throw new InvalidOperationException("Unknown user type (Default, Player, Media)");
        }

        var updatedEntity = UserMapper.ToEntity(updatedDomainUser);
        await _userRepository.UpdateAsync(updatedEntity);
        return UserMapper.ToResponseDTO(updatedDomainUser);
    }

    public async Task DeleteUserAsync(Guid oid)
    {
        await _userRepository.DeleteAsync(oid);
    }
}