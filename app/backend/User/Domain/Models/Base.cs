
using Domain.Exceptions;
using System.Globalization;

namespace Domain.Models;


public record Social(string Platform, string Url);

public class Base
{
    public Guid Oid { get; }

    public DateTime CreatedAt { get; }

    public string Nickname { get; }

    public string Email { get; }

    public enum UserType
    {
        Default,
        Player,
        Media
    }

    public UserType Type { get; }

    public string Gender { get; }

    public int Age { get; }

    public Dictionary<string, Social> Socials { get; }


    protected Base(
        Guid id,
        DateTime createdAt,
        string nickname,
        string email,
        UserType type,
        string gender,
        int age,
        Dictionary<string, Social> socials
        )
    {

        if (string.IsNullOrWhiteSpace(nickname))
        {
            throw new DomainException("Nickname cannot be empty or whitespace");
        }


        Oid = id;
        CreatedAt = createdAt;
        Nickname = nickname;
        Email = email;
        Type = type;
        Gender = gender;
        Age = age;
        Socials = socials;
        
    }

    public override bool Equals(object? obj)
    {
        var item = obj as Base;

        if (item == null)
        {
            return false;
        }

        return Oid.Equals(item.Oid);
    }

    public override int GetHashCode()
    {
        return Oid.GetHashCode();
    }
}