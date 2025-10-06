using Domain.Exceptions;
using Domain.Values;

namespace Domain.Models;

public class Chat : Base
{
    private const int MaxTitleLength = 50;

    private Chat(
        Guid id,
        DateTime createdAt,
        string title,
        ChatOwner owner,
        ChatType type)
        : base(id, createdAt)
    {
        Title = title;
        Owner = owner;
        Type = type;
    }

    public string Title { get; }
    public ChatOwner Owner { get; }
    public ChatType Type { get; }

    public static Chat Create(
        Guid id,
        DateTime createdAt,
        string title,
        ChatOwner owner,
        ChatType type)
    {
        if (string.IsNullOrEmpty(title)) throw new DomainException("Title cant be empty");
        if (title.Length > MaxTitleLength) throw new DomainException("Title is too long");
        var chat = new Chat(id, createdAt, title, owner, type);
        return chat;
    }

    public static Chat Update(Chat oldChat, string title)
    {
        var chat = Create(oldChat.Oid, oldChat.CreatedAt, title, oldChat.Owner, oldChat.Type);
        return chat;
    }
}