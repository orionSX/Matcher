namespace API.Clients;

public class DpmLolClient : BaseClient
{
    private static readonly HttpClient _httpClient;
    
    static DpmLolClient()
    {
        _httpClient = CreateClient("https://dpmlol.com/", "en-US");
        
        // Специфичные для DpmLol заголовки
        _httpClient.DefaultRequestHeaders.Add("Referer", "https://dpmlol.com/");
      
    }
    
    public static HttpClient Instance => _httpClient;
    
    public static string BuildSummonerUrl(string region, string summonerName)
    {
        var encodedName = Uri.EscapeDataString(summonerName);
        return $"summoner/{region.ToLower()}/{encodedName}";
    }
    
    public static string BuildTierListUrl(string patch = "current")
    {
        return $"tier-list/{patch}";
    }
}