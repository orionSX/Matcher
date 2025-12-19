namespace API.Clients;

public class DeepLolClient: BaseClient
{
    private static readonly HttpClient _httpClient;
    
    static DeepLolClient()
    {
        _httpClient = CreateClient("https://deeplol.gg/", "en-US");
        
        
        _httpClient.DefaultRequestHeaders.Add("Referer", "https://deeplol.gg/");
        
    }
    
    public static HttpClient Instance => _httpClient;
    
    public static string BuildSummonerUrl(string region, string summonerName, string tagLine = "")
    {
        var encodedName = Uri.EscapeDataString(summonerName);
        var regionMap = new Dictionary<string, string>
        {
            ["euw"] = "europe", ["eune"] = "europe", ["na"] = "americas",
            ["kr"] = "asia", ["br"] = "americas", ["lan"] = "americas",
            ["las"] = "americas", ["oce"] = "americas", ["ru"] = "asia",
            ["tr"] = "asia", ["jp"] = "asia"
        };
        
        var mappedRegion = regionMap.GetValueOrDefault(region.ToLower(), region.ToLower());
        
        if (!string.IsNullOrEmpty(tagLine))
        {
            return $"en/summoner/{mappedRegion}/{encodedName}-{tagLine}";
        }
        return $"en/summoner/{mappedRegion}/{encodedName}";
    }
    
    public static string BuildMatchAnalysisUrl(string matchId)
    {
        return $"en/match/{matchId}/analysis";
    }
}