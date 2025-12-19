using System.ComponentModel.DataAnnotations;

namespace App.DTOs.Chat;

public record UpdateChatDTO
{
    public Guid Oid { get; set; }
    public string Title { get; set; }
}
