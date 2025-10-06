using App.DTOs.Message;
using App.Services.Message;
using Domain.Exceptions;
using Infra.Exceptions;
using Microsoft.AspNetCore.Mvc;

namespace API.Controllers;

[ApiController]
[Route("api/[controller]")]
public class MessagesController : ControllerBase
{
    private readonly IMessageService<ResponseMessageDTO, CreateMessageDTO, UpdateMessageDTO>
        _messageService;

    public MessagesController(
        IMessageService<ResponseMessageDTO, CreateMessageDTO, UpdateMessageDTO> messageService)
    {
        _messageService = messageService;
    }


    [HttpGet("{oid}")]
    public async Task<ActionResult<ResponseMessageDTO>> GetById(Guid oid)
    {
        try
        {
            var message = await _messageService.GetMessageByIdAsync(oid);
            return Ok(message);
        }
        catch (InfraException ie)
        {
            return NotFound(ie.Message);
        }
    }


    [HttpGet("chat/{chatId}")]
    public async Task<ActionResult<List<ResponseMessageDTO>>> GetByChat(Guid chatId)
    {
        try
        {
            var messages = await _messageService.GetMessagesByChatIdAsync(chatId);
            return Ok(messages);
        }
        catch (InfraException ie)
        {
            return NotFound(ie.Message);
        }
    }


    [HttpGet("author/{authorId}")]
    public async Task<ActionResult<List<ResponseMessageDTO>>> GetByAuthor(Guid authorId)
    {
        try
        {
            var messages = await _messageService.GetMessagesByAuthorAsync(authorId);
            return Ok(messages);
        }
        catch (InfraException ie)
        {
            return NotFound(ie.Message);
        }
    }


    [HttpPost]
    public async Task<ActionResult<ResponseMessageDTO>> Create([FromBody] CreateMessageDTO dto)
    {
        try
        {
            var message = await _messageService.CreateMessageAsync(dto);
            return Ok(message);
        }
        catch (DomainException ie)
        {
            return BadRequest(ie.Message);
        }
    }


    [HttpDelete("{oid}")]
    public async Task<ActionResult> Delete(Guid oid)
    {
        try
        {
            await _messageService.DeleteMessageAsync(oid);
            return NoContent();
        }
        catch (InfraException ie)
        {
            return NotFound(ie.Message);
        }
    }
}