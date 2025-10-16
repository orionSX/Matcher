using App.DTOs.Chat;
using App.DTOs.Message;
using App.Services.Chat;
using App.Services.Message;
using Domain.Repositories;
using Infra;
using Infra.Entities;
using Infra.Repositories;

var builder = WebApplication.CreateBuilder(args);

// ========== MongoDB Configuration (Singleton) ==========
// MongoClient использует внутренний пул соединений и является thread-safe
builder.Services.AddSingleton<MongoService>(provider =>
{
    var connectionString = builder.Configuration.GetConnectionString("MongoDB");
    var databaseName = builder.Configuration["MongoDB:DatabaseName"];
    return new MongoService(connectionString, databaseName);
});

// ========== Repositories (Scoped) ==========
builder.Services.AddScoped<IMessageRepository<Message>>(provider =>
{
    var mongoService = provider.GetRequiredService<MongoService>();
    return new MessageRepository(mongoService.Database);
});

builder.Services.AddScoped<IChatRepository<Chat>>(provider =>
{
    var mongoService = provider.GetRequiredService<MongoService>();
    return new ChatRepository(mongoService.Database);
});

// ========== Services (Scoped) ==========
builder.Services
    .AddScoped<IMessageService<ResponseMessageDTO, CreateMessageDTO, UpdateMessageDTO>,
        MessageService>();
builder.Services
    .AddScoped<IChatService<ResponseChatDTO, CreateChatDTO, UpdateChatDTO>, ChatService>();

// ========== Controllers ==========
builder.Services.AddControllers();

// ========== Swagger/OpenAPI ==========
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddOpenApiDocument();

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