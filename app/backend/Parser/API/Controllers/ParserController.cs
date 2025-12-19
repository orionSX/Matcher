using API.Services;
using Microsoft.AspNetCore.Mvc;

namespace API.Controllers;

[ApiController]
[Route("api/[controller]")]
public class ParserController : ControllerBase
{
    [ApiController]
    [Route("api/[controller]")]
    public class ParserController : ControllerBase
    {
        [HttpGet("")]
        public async Task<IActionResult> Get(
            [FromQuery] string server,
            [FromQuery] string name,
            [FromQuery] string tag
        )
        {
            if (string.IsNullOrWhiteSpace(server))
                return BadRequest("Server is required");

            if (string.IsNullOrWhiteSpace(name))
                return BadRequest("Name is required");

            if (string.IsNullOrWhiteSpace(tag))
                return BadRequest("Tag is required");

            try
            {
                var parser = new OpGgService();
                var res = await parser.GetSummonerStatsAsync(server, name, tag);

                return Ok(res);
            }
            catch (Exception ex)
            {
                // Логируйте исключение
                return StatusCode(500, $"Error fetching summoner data: {ex.Message}");
            }
        }
    }
}
