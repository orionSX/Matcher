namespace API.Models;

public class RankedStats
{
    public int IconId { get; set; }
    public string CurrentRank { get; set; }
    public string CurrentLP { get; set; }
    public string WinLoss { get; set; }
    public string WinRate { get; set; }
    public string BestRank { get; set; }
    public string BestLP { get; set; }
 
}



public class ChampionStats
{
    public string Position { get; set; }
    public string Champion { get; set; }
    public string Wins { get; set; }
    public string Losses { get; set; }
    public string WinRate { get; set; }
    public string KdaRatio { get; set; }
    public string Kda { get; set; }
    public string Laning { get; set; }
    public string DamagePerMinute { get; set; }
    public string DamageShareRatio { get; set; }
    public string WardsScore { get; set; }
    public string WardsControl { get; set; }
    public string CS { get; set; }
    public string CSPerMinute { get; set; }
    public string Gold { get; set; }
    public string GoldPerMinute { get; set; }
    public string DoubleKills { get; set; }
    public string TripleKills { get; set; }
    public string QuadraKills { get; set; }
    public string PentaKills { get; set; }
}

public class SummonerStats
{
    public RankedStats SoloQueue { get; set; } = new();
    public RankedStats FlexQueue { get; set; } = new();
    public List<ChampionStats> ChampionStats { get; set; } = new();
}