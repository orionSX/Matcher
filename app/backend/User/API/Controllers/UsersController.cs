using App.DTOs.User;
using App.Services.User;
using Domain.Exceptions;
using Domain.Models;
using Infra.Exceptions;
using Microsoft.AspNetCore.Mvc;

namespace API.Controllers;

[ApiController]
[Route("api/[controller]")]
public class UsersController : ControllerBase
{
    private readonly IUserService _userService;

    public UsersController(IUserService userService)
    {
        _userService = userService;
    }

    [HttpGet("")]
    public async Task<ActionResult<List<ResponseUserDTO>>> GetAll()
    {
        try
        {
            var users = await _userService.GetAllUsersAsync();
            return Ok(users);
        }
        catch (InfraException ie)
        {
            return NotFound(ie.Message);
        }
    }

    [HttpGet("type/{type}")]
    public async Task<ActionResult<List<ResponseUserDTO>>> GetByType(Base.UserType type)
    {
        try
        {
            var users = await _userService.GetUsersByTypeAsync(type);
            return Ok(users);
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
            var user = await _userService.GetUserByIdAsync(oid);
            return Ok(user);
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
            var user = await _userService.CreateUserAsync(dto);
            return Ok(user);
        }
        catch (DomainException de)
        {
            return BadRequest(de.Message);
        }
    }

    /// <summary>
    ///     Обновить пользователя
    /// </summary>
    [HttpPut("")]
    public async Task<ActionResult<ResponseUserDTO>> Update([FromBody] UpdateUserDTO dto)
    {
        try
        {
            var user = await _userService.UpdateUserAsync(dto);
            return Ok(user);
        }
        catch (DomainException de)
        {
            return BadRequest(de.Message);
        }
        catch (InfraException ie)
        {
            return NotFound(ie.Message);
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
            await _userService.DeleteUserAsync(oid);
            return NoContent();
        }
        catch (InfraException ie)
        {
            return NotFound(ie.Message);
        }
    }
}