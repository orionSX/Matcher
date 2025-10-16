using System.Text.RegularExpressions;
using API.Models;

namespace API.Services;

using HtmlAgilityPack;

public class OpGgStatsParser
{
    public SummonerStats ParseStats(HtmlDocument doc, HtmlDocument champs)
    {
        var stats = new SummonerStats();
        
        // Проверяем наличие секций рейтингов
        bool hasSoloQueue = CheckQueueExists(doc, 1);
        bool hasFlexQueue = CheckQueueExists(doc, 2);
        
        // Парсим статистику
        if (hasSoloQueue)
        {
            stats.SoloQueue = ParseRankedStats(doc, 1);
        }
        
        if (hasFlexQueue)
        {
            stats.FlexQueue = ParseRankedStats(doc, 2);
        }
        
        // Парсим статистику по чемпионам
        stats.ChampionStats = ParseChampionStats(champs);
        
        return stats;
    }
    
    private bool CheckQueueExists(HtmlDocument doc, int queueSection)
    {
        string xpath = $"//aside/section[{queueSection}]//span[text()='Ranked {(queueSection == 1 ? "Solo/Duo" : "Flex")}']";
        return doc.DocumentNode.SelectSingleNode(xpath) != null;
    }
    
    private RankedStats ParseRankedStats(HtmlDocument doc, int queueSection)
    {
        string queueType = queueSection == 1 ? "Solo/Duo" : "Flex";
        string queueCondition = $"count(//aside/section[{queueSection}]/div[1]/div//span[text()='Ranked {queueType}']) > 0";
        
        var stats = new RankedStats();
        
        // Текущий ранг и LP
        stats.CurrentRank = GetNodeText(doc, $"//aside/section[{queueSection}]//strong");
        
        stats.CurrentLP = GetNodeText(doc, $"//aside/section[{queueSection}]/div[2]/div/div[1]/div[1]/div/span[{queueCondition}]/text()[1]");
        
        // Win/Loss и Win Rate
        
        var icon = doc.DocumentNode.SelectNodes($"//*[@alt='ABDUL THE MENACE#meow']");
        if (icon != null)
        {
            
           var url= string.Join(" ", icon.Select(n => n.Attributes["src"].Value));
           var match = Regex.Match(url, @"profileIcon(\d+)\.jpg");
           if (match.Success && int.TryParse(match.Groups[1].Value, out int iconId))
           {
               stats.IconId = iconId;
           }
           
        }
        var wlNodes = doc.DocumentNode.SelectNodes($"//aside/section[{queueSection}]/div[2]/div/div[1]/div[2]/span[1]/text()");
        if (wlNodes != null)
        {
            stats.WinLoss = string.Join(" ", wlNodes.Select(n => n.InnerText.Trim()));
        }
        var zxcv = doc.DocumentNode.SelectNodes($"//aside/section[{queueSection}]/div[2]/div/div[1]/div[2]/span[2]/text()");
        if (zxcv != null)
        {
            stats.WinRate = string.Join(" ", zxcv.Select(n => n.InnerText.Trim()));
        }
        
        var aaa = doc.DocumentNode.SelectNodes($"//aside/section[{queueSection}]/div[2]/div/div[2]/div//span/text()");
        if (aaa != null)
        {
            stats.BestLP = string.Join(" ", aaa.Select(n => n.InnerText.Trim()));
            
        }
        var bbb = doc.DocumentNode.SelectNodes($"//aside/section[{queueSection}]/div[2]/div/div[2]/div//strong/text()");
        if (bbb != null)
        {
            stats.BestRank = string.Join(" ", bbb.Select(n => n.InnerText.Trim()));
            
        }
        
        
        
        
        return stats;
    }
    
  
    
    private List<ChampionStats> ParseChampionStats(HtmlDocument doc)
    {
       
        var championStats = new List<ChampionStats>();
        
        var rows = doc.DocumentNode.SelectNodes("//section[2]/div/table/tbody/tr");
        if (rows == null) return championStats;
        
        foreach (var row in rows)
        {
            if (rows.IndexOf(row)==0 || rows.IndexOf(row)==2)continue;
            if (rows.IndexOf(row) > 6) break;
            var stats = new ChampionStats
            {
                Position = GetCellText(row, "td[1]"),
                Champion = GetCellText(row, "td[2]/div/strong"),
                Wins = ExtractWinsLosses(row, "td[3]/div/div/div[1]/span/text()[1]"),
                Losses = ExtractWinsLosses(row, "td[3]/div/div/div[2]/span/text()[1]"),
                WinRate = GetCellText(row, "td[3]/div/span"),
                KdaRatio = GetCellText(row, "td[4]/span/div"),
                Kda = GetCellText(row, "td[4]/span/span"),
                Laning = GetCellText(row, "td[6]/span/span[1]/span[1]"),
                DamagePerMinute = GetCellText(row, "td[7]/span/span[1]"),
                DamageShareRatio = GetCellText(row, "td[7]/span/span[2]"),
                WardsScore = GetCellText(row, "td[8]/span/span[1]"),
                WardsControl = GetCellText(row, "td[8]/span/span[2]"),
                CS = GetCellText(row, "td[9]/span/span[1]"),
                CSPerMinute = GetCellText(row, "td[9]/span/span[2]"),
                Gold = GetCellText(row, "td[10]/span/span[1]"),
                GoldPerMinute = GetCellText(row, "td[10]/span/span[2]"),
                DoubleKills = GetCellText(row, "td[11]/span/span"),
                TripleKills = GetCellText(row, "td[12]/span/span"),
                QuadraKills = GetCellText(row, "td[13]/span/span"),
                PentaKills = GetCellText(row, "td[14]/span/span")
            };
            
            championStats.Add(stats);
        }
        
        return championStats;
    }
    
    private string GetNodeText(HtmlDocument doc, string xpath)
    {
        var node = doc.DocumentNode.SelectSingleNode(xpath);
        return node?.InnerText.Trim() ?? string.Empty;
    }
    
    private string GetCellText(HtmlNode row, string cellXpath)
    {
        var cell = row.SelectSingleNode("./"+cellXpath);
        return cell?.InnerText.Trim() ?? string.Empty;
    }
    
    private string ExtractWinsLosses(HtmlNode row, string xpath)
    {
        var node = row.SelectSingleNode(xpath);
        if (node == null) return string.Empty;
        
        // Извлекаем только цифры из текста
        var text = node.InnerText.Trim();
        var match = System.Text.RegularExpressions.Regex.Match(text, @"\d+");
        return match.Success ? match.Value : string.Empty;
    }
}