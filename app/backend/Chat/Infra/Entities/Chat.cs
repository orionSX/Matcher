using Domain.Values;
using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace Infra.Entities;

public record ChatOwner
{
    [BsonElement("owner_oid")]
    [BsonRepresentation(BsonType.String)]
    public string Oid { get; set; } = Guid.Empty.ToString();

    [BsonElement("owner_type")]
    [BsonRepresentation(BsonType.String)]
    public OwnerType Type { get; set; } = OwnerType.System;
}

public record Chat
{
    [BsonId]
    [BsonRepresentation(BsonType.String)]
    public Guid Oid { get; set; }

    [BsonElement("created_at")]
    [BsonDateTimeOptions(Kind = DateTimeKind.Utc)]
    public DateTime CreatedAt { get; set; }

    [BsonElement("title")]
    [BsonRepresentation(BsonType.String)]
    public string? Title { get; set; }

    [BsonElement("owner")] public ChatOwner Owner { get; set; } = new();


    [BsonElement("type")]
    [BsonRepresentation(BsonType.String)]
    public ChatType Type { get; set; }
}