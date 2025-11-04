using Domain.Models;
using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace Infra.Entities;

public class UserEntity
{
    [BsonId]
    [BsonRepresentation(BsonType.String)]
    public Guid Oid { get; set; }

    [BsonElement("created_at")]
    [BsonDateTimeOptions(Kind = DateTimeKind.Utc)]
    public DateTime CreatedAt { get; set; }


    [BsonElement("nickname")]
    public string Nickname { get; set; }



    [BsonElement("email")]
    public string Email { get; set; }



    [BsonElement("type")]
    [BsonRepresentation(BsonType.String)]
    public Base.UserType Type { get; set; }


    [BsonElement("gender")]
    public string Gender { get; set; }



    [BsonElement("age")]
    public int Age { get; set; }



    [BsonElement("socials")]
    public Dictionary<string, Social> Socials { get; set; } = new();



    // Поля для Player
    [BsonElement("accounts")]
    public string[] Accounts { get; set; } = Array.Empty<string>();

    [BsonElement("roles")]
    public string[] Roles { get; set; } = Array.Empty<string>();


    // Поля для Media
    [BsonElement("media_links")]
    public string[] MediaLinks { get; set; } = Array.Empty<string>();
}