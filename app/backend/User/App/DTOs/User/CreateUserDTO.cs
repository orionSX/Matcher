using Domain.Models;
using static Domain.Models.Base;

namespace App.DTOs.User;

public record CreateUserDTO
{
    public required string Nickname { get; set; }
    public required string Email { get; set; }
    public required UserType Type { get; set; }
    public required string Gender { get; set; }
    public required int Age { get; set; }
    public required Dictionary<string, Social> Socials { get; set; }


    //Player
    public string[]? Accounts { get; set; }
    public string[]? Roles { get; set; }

    //Media
    public string[]? MediaLinks { get; set; }
}