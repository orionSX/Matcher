using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace App.DTOs.User;

    public record UpdateUserDTO
{
    public Guid Oid { get; set; }

    public string Nickname { get; set; }

    public string Email { get; set; }


}

