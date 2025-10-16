using Domain.Values;

namespace App.DTOs.Message;

public record ResponseMessageDTO
{
    public Guid Oid { get; set; }
    public DateTime CreatedAt { get; set; }
    public string? Text { get; set; }
    public Guid CreatorId { get; set; }
    public Guid ChatId { get; set; }

    public DateTime? ExpiresAt { get; set; } = null;
    public List<ReadBy> ReadBy { get; set; } = [];
}

