namespace App.DTOs.Message;

public record CreateMessageDTO
{
    public string? Text { get; set; }

    public Guid CreatorId { get; set; }

    public Guid ChatId { get; set; }

    public TimeSpan? Ttl { get; set; }
}
