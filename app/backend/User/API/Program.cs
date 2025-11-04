using App.DTOs.User;
using App.Services.User;
using Domain.Repositories;
using Infra;
using Infra.Entities;
using Infra.Repositories;
using MongoDB.Bson;
using MongoDB.Bson.Serialization;
using MongoDB.Bson.Serialization.Serializers;

var builder = WebApplication.CreateBuilder(args);

BsonSerializer.RegisterSerializer(new GuidSerializer(BsonType.String));
BsonSerializer.RegisterSerializer(new DateTimeOffsetSerializer(BsonType.String));

// ========== MongoDB Configuration (Singleton) ==========
builder.Services.AddSingleton<MongoService>(provider =>
{
    var connectionString = builder.Configuration.GetConnectionString("MongoDB");
    var databaseName = builder.Configuration["MongoDB:DatabaseName"];
    return new MongoService(connectionString, databaseName);
});

// ========== Repositories (Scoped) ==========
// Регистрируем универсальный репозиторий для UserEntity
builder.Services.AddScoped<IUserRepository<UserEntity>>(provider =>
{
    var mongoService = provider.GetRequiredService<MongoService>();
    return new UserRepository<UserEntity>(mongoService.Database, "users");
});

// ========== Services (Scoped) ==========
// Теперь используем IUserService без дженериков
builder.Services.AddScoped<IUserService, UserService>();

// ========== Controllers ==========
builder.Services.AddControllers()
    .AddJsonOptions(options =>
    {
        // Для корректной работы с enum как строками в JSON
        options.JsonSerializerOptions.Converters.Add(new System.Text.Json.Serialization.JsonStringEnumConverter());
    });

// ========== Swagger/OpenAPI ==========
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddOpenApiDocument(config =>
{
    config.Title = "User API";
    config.Description = "API for managing users of different types";
});

var app = builder.Build();

// ========== Development Tools ==========
if (app.Environment.IsDevelopment())
{
    app.UseOpenApi();
    app.UseSwaggerUi();
}

app.UseRouting();
app.MapControllers();

app.Run();
app.Run();