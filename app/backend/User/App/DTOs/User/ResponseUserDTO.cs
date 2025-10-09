using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Domain.Values;


namespace App.DTOs.User;

    public record ResponseUserDTO
{
    public Guid Oid { get; set; }

    public DateTime CreatedAt  { get; set; }

    public string Nickname { get; set; }

    public string Email { get; set; }
}

