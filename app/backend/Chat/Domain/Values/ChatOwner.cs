namespace Domain.Values;

public record ChatOwner
{
    public Guid Oid = Guid.Empty;
    public OwnerType Type = OwnerType.System;
}