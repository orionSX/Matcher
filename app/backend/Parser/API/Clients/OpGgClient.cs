using HtmlAgilityPack;

namespace API.Clients;

public class OpGgHttpClient : BaseClient
{
    private static readonly HttpClient _httpClient;
    private static readonly HtmlWeb _web;
    
    static OpGgHttpClient()
    {
        _httpClient = CreateClient("https://op.gg/", "en-US");
        _httpClient.DefaultRequestHeaders.Add("Referer", "https://op.gg/");
        _web = new HtmlWeb();
    }
    
    public static HttpClient Instance => _httpClient;
    public static HtmlWeb InstanceWeb => _web;
    public static string BuildSummonerUrl(string region, string summonerName, string tagLine = "")
    {
        var encodedName = Uri.EscapeDataString(summonerName);
        if (!string.IsNullOrEmpty(tagLine))
        {
            return $"https://op.gg/en/lol/summoners/{region.ToLower()}/{encodedName}-{tagLine}";
        }
        return $"https://op.gg/en/lol/summoners/{region.ToLower()}/{encodedName}";
    }
}