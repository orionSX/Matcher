using System.ComponentModel.DataAnnotations;
using Domain.Values;

namespace App.DTOs.Chat;

public record CreateChatDTO
{
    public string Title { get; set; } = "Chat";

    public ChatOwner Owner { get; set; } = new();

    public ChatType Type { get; set; }
}

