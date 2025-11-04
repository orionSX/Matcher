using Domain.Exceptions;
using Domain.Values;
using System.Reflection;

namespace Domain.Models;


public class Default : Base
{


    private Default(
    Guid id, 
    DateTime createdAt, 
    string nickname, 
    string email,
    UserType type, 
    string gender, 
    int age, 
    Dictionary<string, Social> socials) : base(id, createdAt, nickname, email, type, gender, age, socials)
    { }

    public static Default Create(
        Guid id,
        DateTime createdAt,
        string nickname,
        string email,
        UserType type,
        string gender,
        int age,
        Dictionary<string, Social> socials)
    {
        if (string.IsNullOrEmpty(nickname)) throw new DomainException("Nickname cant be empty");
        var user = new Default(id, createdAt, nickname, email, type, gender, age, socials);
        return user;
    }

    public static Default Update(Default oldUser, string nickname, string email, UserType type, string gender, int age, Dictionary<string, Social> socials)
    {
        var User = Create(oldUser.Oid, oldUser.CreatedAt, nickname, email, type, gender, age, socials);
        return User;
    }


}