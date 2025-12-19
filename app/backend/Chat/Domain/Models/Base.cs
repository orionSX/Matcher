namespace Domain.Models;

public class Base
{
    public Guid Oid { get; }
    public DateTime CreatedAt { get; }

    protected Base(Guid id, DateTime createdAt)
    {
        Oid = id;
        CreatedAt = createdAt;
    }

    public override bool Equals(object? obj)
    {
        var item = obj as Base;

        if (item == null)
        {
            return false;
        }

        return Oid.Equals(item.Oid);
    }

    public override int GetHashCode()
    {
        return Oid.GetHashCode();
    }
}
