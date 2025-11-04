using Domain.Exceptions;
using Domain.Values;
using System.Globalization;
using System.Reflection;

namespace Domain.Models;


public class Player : Base
{


    private Player(
    Guid id,
    DateTime createdAt,
    string nickname,
    string email,
    UserType type,
    string gender,
    int age,
    Dictionary<string, Social> socials,
    string[] accounts,
    string[] roles) : base(id, createdAt, nickname, email, type, gender, age, socials)
    {
        Accounts = accounts;
        Roles = roles;
    }

    public string[] Accounts { get; }
    public string[] Roles { get; }



    public static Player Create(
        Guid id,
        DateTime createdAt,
        string nickname,
        string email,
        UserType type,
        string gender,
        int age,
        Dictionary<string, Social> socials,
        string[] accounts,
        string[] roles)
    {
        if (string.IsNullOrEmpty(nickname)) throw new DomainException("Nickname cant be empty");
        var Player = new Player(id, createdAt, nickname, email, type, gender, age, socials, accounts, roles);
        return Player;
    }

    public static Player Update(Player oldPlayer, 
        string nickname,
        string email,
        UserType type,
        string gender,
        int age,
        Dictionary<string, Social> socials,
        string[] accounts,
        string[] roles)
    {
        var Player = Create(oldPlayer.Oid, oldPlayer.CreatedAt, nickname, email, type, gender, age, socials, accounts, roles);
        return Player;
    }


}