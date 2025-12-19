using System.Text;
using API.Clients;
using API.Models;
using API.Services;
using HtmlAgilityPack;

public class OpGgService
{
    private readonly HttpClient _httpClient;
    private readonly OpGgStatsParser _parser;
    private readonly HtmlWeb _web;
    
    public OpGgService()
    {
        _httpClient = ClientFactory.GetClient<OpGgHttpClient>();
        _parser = new OpGgStatsParser();
        _web = OpGgHttpClient.InstanceWeb;
    }
    
   
    public async Task<SummonerStats> GetSummonerStatsAsync(string region, string summonerName, string tagLine = "")
    {
        try
        {
            var url = OpGgHttpClient.BuildSummonerUrl(region, summonerName, tagLine);

           
            var mainTask = Task.Run(() => _web.LoadFromWebAsync(url));
            var champsTask = Task.Run(() => _web.LoadFromWebAsync(url + "/champions"));
    
            await Task.WhenAll(mainTask, champsTask);
            
            var res = _parser.ParseStats(mainTask.Result,champsTask.Result);
         
         
       
            return res;
        }
        catch (Exception ex)
        {
            throw new Exception($"Error parsing summoner stats: {ex.Message}", ex);
        }
    }
    
}