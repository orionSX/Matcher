using Domain.Values;

namespace App.DTOs.Chat;

public record ResponseChatDTO
{
    public Guid Oid { get; set; }
    public DateTime CreatedAt { get; set; }
    public string? Title { get; set; }
    public ChatOwner Owner { get; set; } = new();
    public ChatType Type { get; set; }
}
