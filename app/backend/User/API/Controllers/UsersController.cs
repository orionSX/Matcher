using App.DTOs.User;
using App.Services.User;
using Domain.Exceptions;
using Infra.Exceptions;
using Microsoft.AspNetCore.Mvc;

namespace API.Controllers;

[ApiController]
[Route("api/[controller]")]
public class UsersController : ControllerBase
{
    private readonly IUserService<ResponseUserDTO, CreateUserDTO, UpdateUserDTO> _UserService;

    public UsersController(IUserService<ResponseUserDTO, CreateUserDTO, UpdateUserDTO> UserService)
    {
        _UserService = UserService;
    }


    [HttpGet("")]
    public async Task<ActionResult<ResponseUserDTO>> GetAll()
    {
        try
        {
            var Users = await _UserService.GetAllUsers();
            return Ok(Users);
        }
        catch (InfraException ie)
        {
            return NotFound(ie.Message);
        }
    }

    [HttpGet("{oid}")]
    public async Task<ActionResult<ResponseUserDTO>> GetById(Guid oid)
    {
        try
        {
            var User = await _UserService.GetUserByIdAsync(oid);
            return Ok(User);
        }
        catch (InfraException ie)
        {
            return NotFound(ie.Message);
        }
    }

    /// <summary>
    ///     Создать нового пользователя
    /// </summary>
    [HttpPost]
    public async Task<ActionResult<ResponseUserDTO>> Create([FromBody] CreateUserDTO dto)
    {
        try
        {
            var User = await _UserService.CreateUserAsync(dto);
            return Ok(User);
        }
        catch (DomainException de)
        {
            return BadRequest(de.Message);
        }
    }

    /// <summary>
    ///     Удалить пользователя
    /// </summary>
    [HttpDelete("{oid}")]
    public async Task<ActionResult> Delete(Guid oid)
    {
        try
        {
            await _UserService.DeleteUserAsync(oid);
            return NoContent();
        }
        catch (InfraException ie)
        {
            return NotFound(ie.Message);
        }
    }
}