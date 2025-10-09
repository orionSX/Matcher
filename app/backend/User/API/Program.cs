using App.DTOs.User;
using App.Services.User;
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
builder.Services.AddScoped<IUserRepository<User>>(provider =>
{
    var mongoService = provider.GetRequiredService<MongoService>();
    return new UserRepository(mongoService.Database);
});

// ========== Services (Scoped) ==========!!!
builder.Services
    .AddScoped<IUserService<ResponseUserDTO, CreateUserDTO, UpdateUserDTO>,
        UserService>();

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