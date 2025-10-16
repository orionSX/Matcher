namespace API.Clients;

public class LeagueOfGraphsClient : BaseClient
{
    private static readonly HttpClient _httpClient;
    
    static LeagueOfGraphsClient()
    {
        _httpClient = CreateClient("https://www.leagueofgraphs.com/", "en-US");
        
        
        _httpClient.DefaultRequestHeaders.Add("Referer", "https://www.leagueofgraphs.com/");
       
    }
    
    public static HttpClient Instance => _httpClient;
    
    public static string BuildSummonerUrl(string region, string summonerName)
    {
        var encodedName = Uri.EscapeDataString(summonerName.ToLower().Replace(" ", "+"));
        return $"summoner/{region.ToLower()}/{encodedName}";
    }
    
   
}