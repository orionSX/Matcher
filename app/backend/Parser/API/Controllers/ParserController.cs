using API.Services;
using Microsoft.AspNetCore.Mvc;

namespace API.Controllers;

[ApiController]
[Route("api/[controller]")]
public class ParserController : ControllerBase
{

    [HttpGet("")]
    public async Task<IActionResult> Get()
    {
        var parser = new OpGgService();
        var res = await parser.GetSummonerStatsAsync("ru", "ABDUL THE MENACE", "meow");
        
        return Ok(res);
    }
}