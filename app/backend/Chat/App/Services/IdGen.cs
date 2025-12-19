using MassTransit;

namespace App.Services;



public static class IdGenerator
{
    public static Guid GetId() => NewId.Next().ToGuid();

    public static string GetStringId() => NewId.Next().ToString();

    
}