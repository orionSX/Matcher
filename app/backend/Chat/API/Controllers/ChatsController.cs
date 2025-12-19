using App.DTOs.Chat;
using App.Services.Chat;
using Domain.Exceptions;
using Infra.Exceptions;
using Microsoft.AspNetCore.Mvc;

namespace API.Controllers;

[ApiController]
[Route("api/[controller]")]
public class ChatsController : ControllerBase
{
    private readonly IChatService<ResponseChatDTO, CreateChatDTO, UpdateChatDTO> _chatService;

    public ChatsController(IChatService<ResponseChatDTO, CreateChatDTO, UpdateChatDTO> chatService)
    {
        _chatService = chatService;
    }


    [HttpGet("")]
    public async Task<ActionResult<ResponseChatDTO>> GetAll()
    {
        try
        {
            var chats = await _chatService.GetAllChats();
            return Ok(chats);
        }
        catch (InfraException ie)
        {
            return NotFound(ie.Message);
        }
    }

    [HttpGet("{oid}")]
    public async Task<ActionResult<ResponseChatDTO>> GetById(Guid oid)
    {
        try
        {
            var chat = await _chatService.GetChatByIdAsync(oid);
            return Ok(chat);
        }
        catch (InfraException ie)
        {
            return NotFound(ie.Message);
        }
    }


    [HttpGet("owner/{ownerOid}")]
    public async Task<ActionResult<List<ResponseChatDTO>>> GetByOwner(string ownerOid)
    {
        try
        {
            var chats = await _chatService.GetChatsByOwnerAsync(ownerOid);
            return Ok(chats);
        }
        catch (InfraException ie)
        {
            return NotFound(ie.Message);
        }
    }

    /// <summary>
    ///     Создать новый чат
    /// </summary>
    [HttpPost]
    public async Task<ActionResult<ResponseChatDTO>> Create([FromBody] CreateChatDTO dto)
    {
        try
        {
            var chat = await _chatService.CreateChatAsync(dto);
            return Ok(chat);
        }
        catch (DomainException de)
        {
            return BadRequest(de.Message);
        }
    }

    /// <summary>
    ///     Удалить чат
    /// </summary>
    [HttpDelete("{oid}")]
    public async Task<ActionResult> Delete(Guid oid)
    {
        try
        {
            await _chatService.DeleteChatAsync(oid);
            return NoContent();
        }
        catch (InfraException ie)
        {
            return NotFound(ie.Message);
        }
    }
}