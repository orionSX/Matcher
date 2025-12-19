using Domain.Exceptions;
using Domain.Values;

namespace Domain.Models;

public class Message : Base
{
    private const int MaxTextLength = 300;

    private Message(Guid id,
        DateTime createdAt,
        string text,
        Guid creatorId,
        Guid chatId,
        TimeSpan? ttl,
        List<ReadBy>? readBy) : base(id, createdAt)
    {
        Text = text;
        CreatorId = creatorId;
        ChatId = chatId;

        if (ttl is not null) ExpiresAt = DateTime.UtcNow + ttl;

        if (readBy is not null) ReadBy = readBy;
    }

    public string Text { get; }
    public Guid CreatorId { get; }
    public Guid ChatId { get; }

    public DateTime? ExpiresAt { get; }
    public List<ReadBy> ReadBy { get; } = [];

    public static Message Create(Guid id,
        DateTime createdAt,
        string? text,
        Guid creatorId,
        Guid chatId,
        TimeSpan? ttl,
        List<ReadBy> readBy)
    {
        if (string.IsNullOrEmpty(text)) throw new DomainException("Text cant be empty");

        if (text.Length > MaxTextLength) throw new DomainException("Text is too long");

        var msg = new Message(id, createdAt, text, creatorId, chatId, ttl, readBy ?? default);
        return msg;
    }

    public static Message Update(Message oldMessage, string text)
    {
        var message = Create(oldMessage.Oid, oldMessage.CreatedAt, text, oldMessage.CreatorId,
            oldMessage.ChatId, DateTime.UtcNow - oldMessage.ExpiresAt, oldMessage.ReadBy);
        return message;
    }
}