namespace App.DTOs.Message;

public record UpdateMessageDTO
{
    public Guid Oid { get; set; }
    public string Text { get; set; }
}
