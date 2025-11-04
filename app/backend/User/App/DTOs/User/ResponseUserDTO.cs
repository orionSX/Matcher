using Domain.Models;
using static Domain.Models.Base;

namespace App.DTOs.User;

public record ResponseUserDTO
{
    public Guid Oid { get; set; }
    public DateTime CreatedAt { get; set; }
    public string Nickname { get; set; }
    public string Email { get; set; }
    public UserType Type { get; set; }
    public string Gender { get; set; }
    public int Age { get; set; }
    public Dictionary<string, Social> Socials { get; set; }


    //Player
    public string[]? Accounts { get; set; }
    public string[]? Roles { get; set; }

    //Media
    public string[]? MediaLinks { get; set; }
}