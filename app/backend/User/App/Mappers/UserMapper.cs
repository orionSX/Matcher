using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using App.DTOs.User;
using Domain.Models;
using Domain.Values;

namespace App.Mappers;
    

public static class UserMapper
{
    public static User ToDomain(Infra.Entities.User entity)
    {
        var user = User.Create(
            entity.Oid,
            entity.CreatedAt,
            entity.Nickname,
            entity.Email
            );

        return user;
    }


    public static ResponseUserDTO ToResponseDTO(Infra.Entities.User entity)
    {
        var userDTO = new ResponseUserDTO
        {
            Oid = entity.Oid,
            CreatedAt = entity.CreatedAt,
            Nickname = entity.Nickname,
            Email = entity.Email
        };

        return userDTO;
    }

    public static Infra.Entities.User ToEntity(User domain)
    {
        return new Infra.Entities.User
        {
            Oid = domain.Oid,
            CreatedAt = domain.CreatedAt,
            Nickname = domain.Nickname,
            Email = domain.Email

        };
    }
}

