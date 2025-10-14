using MongoDB.Driver;

namespace Infra;

public class MongoService
{
    private readonly MongoClient _client;
    public IMongoDatabase Database { get; }

    public MongoService(string? connectionString, string? databaseName)
    {
        if (string.IsNullOrEmpty(connectionString))
            throw new ArgumentNullException(nameof(connectionString));

        if (string.IsNullOrEmpty(databaseName))
            throw new ArgumentNullException(nameof(databaseName));

        _client = new MongoClient(connectionString);
        Database = _client.GetDatabase(databaseName);
    }
}
