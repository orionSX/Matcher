using App.DTOs.User;
using Domain.Models;
using Infra.Entities;

public static class UserMapper
{
    public static object ToDomain(UserEntity entity)
    {
        return entity.Type switch
        {
            //Player
            Base.UserType.Player => Player.Create(
                entity.Oid, entity.CreatedAt, entity.Nickname, entity.Email,
                entity.Type, entity.Gender, entity.Age, entity.Socials,
                entity.Accounts, entity.Roles),

            //Media
            Base.UserType.Media => Media.Create(
                entity.Oid, entity.CreatedAt, entity.Nickname, entity.Email,
                entity.Type, entity.Gender, entity.Age, entity.Socials,
                entity.MediaLinks),

            //Other (Default)
            _ => Default.Create(
                entity.Oid, entity.CreatedAt, entity.Nickname, entity.Email,
                entity.Type, entity.Gender, entity.Age, entity.Socials)
        };
    }

    public static ResponseUserDTO ToResponseDTO(object domainObj)
    {
        var dto = new ResponseUserDTO();

        // Other
        if (domainObj is Base baseObj)
        {
            dto.Oid = baseObj.Oid;
            dto.CreatedAt = baseObj.CreatedAt;
            dto.Nickname = baseObj.Nickname;
            dto.Email = baseObj.Email;
            dto.Type = baseObj.Type;
            dto.Gender = baseObj.Gender;
            dto.Age = baseObj.Age;
            dto.Socials = baseObj.Socials;
        }

        // Player
        if (domainObj is Player player)
        {
            dto.Accounts = player.Accounts;
            dto.Roles = player.Roles;
        }

        // Media
        if (domainObj is Media media)
        {
            dto.MediaLinks = media.Media_links;
        }

        return dto;
    }

    public static UserEntity ToEntity(object domainObj)
    {
        var entity = new UserEntity();

        // Other
        if (domainObj is Base baseObj)
        {
            entity.Oid = baseObj.Oid;
            entity.CreatedAt = baseObj.CreatedAt;
            entity.Nickname = baseObj.Nickname;
            entity.Email = baseObj.Email;
            entity.Type = baseObj.Type;
            entity.Gender = baseObj.Gender;
            entity.Age = baseObj.Age;
            entity.Socials = baseObj.Socials;
        }

        // Player
        if (domainObj is Player player)
        {
            entity.Accounts = player.Accounts;
            entity.Roles = player.Roles;
        }

        // Media
        if (domainObj is Media media)
        {
            entity.MediaLinks = media.Media_links;
        }

        return entity;
    }
}