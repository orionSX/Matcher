using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace Infra.Entities;

public record ReadBy
{
    [BsonElement("user_id")]
    [BsonRepresentation(BsonType.String)]
    public Guid UserId { get; set; }

    [BsonElement("read_at")] public DateTime? ReadAt { get; set; }
}

public record Message
{
    [BsonId]
    [BsonRepresentation(BsonType.String)]
    public Guid Oid { get; set; }

    [BsonElement("created_at")] public DateTime CreatedAt { get; set; }

    [BsonElement("text")] public string Text { get; set; }

    [BsonElement("creator_id")]
    [BsonRepresentation(BsonType.String)]
    public Guid CreatorId { get; set; }

    [BsonElement("chat_id")]
    [BsonRepresentation(BsonType.String)]
    public Guid ChatId { get; set; }


    [BsonElement("expires_at")] public DateTime? ExpiresAt { get; set; }

    [BsonElement("read_by")] public List<ReadBy> ReadBy { get; set; }
}