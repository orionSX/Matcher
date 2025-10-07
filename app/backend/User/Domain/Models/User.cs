using Domain.Exceptions;
using Domain.Values;

namespace Domain.Models;


public class User : Base
{


    private User(
        Guid id,
        DateTime createdAt,
        string nickname,
        string email) : base(id, createdAt)
    {
        Nickname = nickname;
        Email = email;

    }

    public string Nickname { get; }

    public string Email { get; }

    public static User Create(
        Guid id,
        DateTime createdAt,
        string nickname,
        string email)
    {
        if (string.IsNullOrEmpty(nickname)) throw new DomainException("Nickname cant be empty");
        var user = new User(id, createdAt, nickname, email);
        return user;
    }

    public static User Update(User oldUser, string nickname, string email)
    {
        var User = Create(oldUser.Oid, oldUser.createdAt, nickname, email);
        return User;
    }


}