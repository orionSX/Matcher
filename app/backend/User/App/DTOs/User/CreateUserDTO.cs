using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Domain.Values;

namespace App.DTOs.User;

   public record CreateUserDTO
{
    

    public required string Nickname { get; set; }

    public required string Email { get; set; }


}

