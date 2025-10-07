using Domain.Values;
using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace Infra.Entities;

public record User
{

    [BsonId]
    [BsonRepresentation(BsonType.String)]
    public Guid Oid { get; set; }


    [BsonElement("created_at")]
    [BsonDateTimeOptions(Kind = DateTimeKind.Utc)]
    public DateTime CreatedAt { get; set; }

    public string Nickname { get; set; }

    public string Email { get; set; }


}