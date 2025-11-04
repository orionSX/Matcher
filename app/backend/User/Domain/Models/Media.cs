using Domain.Exceptions;
using Domain.Values;
using System.Globalization;
using System.Reflection;

namespace Domain.Models;


public class Media : Base
{


    private Media(
    Guid id,
    DateTime createdAt,
    string nickname,
    string email,
    UserType type,
    string gender,
    int age,
    Dictionary<string, Social> socials,
    string[] media_links) : base(id, createdAt, nickname, email, type, gender, age, socials)
    {
        Media_links = media_links;
    }

    public string[] Media_links { get; }


    public static Media Create(
        Guid id,
        DateTime createdAt,
        string nickname,
        string email,
        UserType type,
        string gender,
        int age,
        Dictionary<string, Social> socials,
        string[] media_links)
    {


        if (string.IsNullOrEmpty(nickname))
        {
            throw new DomainException("Nickname cant be empty");
        }

        var media = new Media(id, createdAt, nickname, email, type, gender, age, socials, media_links);
        return media;
    }

    public static Media Update(Media oldMedia,
        string nickname,
        string email,
        UserType type,
        string gender,
        int age,
        Dictionary<string, Social> socials,
        string[] media_links)
    {
        var Media = Create(oldMedia.Oid, oldMedia.CreatedAt, nickname, email, type, gender, age, socials, media_links);
        return Media;
    }


}