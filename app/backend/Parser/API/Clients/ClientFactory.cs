using System.Collections.Concurrent;
using System.Reflection;

namespace API.Clients;

public static class ClientFactory
{
    private static readonly ConcurrentDictionary<Type, HttpClient> _clients = new();
    
    public static HttpClient GetClient<T>() where T : BaseClient
    {
        return _clients.GetOrAdd(typeof(T), _ =>
        {
            var property = typeof(T).GetProperty("Instance", BindingFlags.Public | BindingFlags.Static);
            return (HttpClient)property.GetValue(null);
        });
    }
    
    public static void DisposeAll()
    {
        foreach (var client in _clients.Values)
        {
            client.Dispose();
        }
        _clients.Clear();
    }
}