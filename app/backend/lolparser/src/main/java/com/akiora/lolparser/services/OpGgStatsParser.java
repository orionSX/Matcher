package com.akiora.lolparser.services;

import com.akiora.lolparser.models.ChampionStats;
import com.akiora.lolparser.models.RankedStats;
import com.akiora.lolparser.models.SummonerStats;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class OpGgStatsParser {

    public SummonerStats parseStats(Document mainDoc, Document champsDoc) {
        SummonerStats stats = new SummonerStats();

        // Check if queue sections exist
        boolean hasSoloQueue = checkQueueExists(mainDoc, 1);
        boolean hasFlexQueue = checkQueueExists(mainDoc, 2);

        // Parse ranked statistics
        if (hasSoloQueue) {
            stats.setSoloQueue(parseRankedStats(mainDoc, 1));
        }

        if (hasFlexQueue) {
            stats.setFlexQueue(parseRankedStats(mainDoc, 2));
        }

        // Parse champion statistics
        stats.setChampionStats(parseChampionStats(champsDoc));

        return stats;
    }

    private boolean checkQueueExists(Document doc, int queueSection) {
        String queueType = queueSection == 1 ? "Solo/Duo" : "Flex";
        Elements elements = doc.select("aside > section:nth-of-type(" + queueSection + ") span");
        
        for (Element element : elements) {
            if (element.text().contains("Ranked " + queueType)) {
                return true;
            }
        }
        return false;
    }

    private RankedStats parseRankedStats(Document doc, int queueSection) {
        RankedStats stats = new RankedStats();

        // Current rank
        Element rankElement = doc.selectFirst("aside > section:nth-of-type(" + queueSection + ") strong");
        if (rankElement != null) {
            stats.setCurrentRank(rankElement.text().trim());
        }

        // Current LP
        Element lpElement = doc.selectFirst("aside > section:nth-of-type(" + queueSection + ") > div:nth-of-type(2) > div > div:nth-of-type(1) > div:nth-of-type(1) > div > span");
        if (lpElement != null) {
            String lpText = lpElement.ownText().trim();
            stats.setCurrentLP(lpText);
        }

        // Icon ID from profile icon
        Elements iconElements = doc.select("img[alt]");
        for (Element icon : iconElements) {
            String src = icon.attr("src");
            Pattern pattern = Pattern.compile("profileIcon(\\d+)\\.jpg");
            Matcher matcher = pattern.matcher(src);
            if (matcher.find()) {
                stats.setIconId(Integer.parseInt(matcher.group(1)));
                break;
            }
        }

        // Win/Loss
        Elements wlElements = doc.select("aside > section:nth-of-type(" + queueSection + ") > div:nth-of-type(2) > div > div:nth-of-type(1) > div:nth-of-type(2) > span:nth-of-type(1)");
        if (!wlElements.isEmpty()) {
            stats.setWinLoss(wlElements.text().trim());
        }

        // Win Rate
        Elements wrElements = doc.select("aside > section:nth-of-type(" + queueSection + ") > div:nth-of-type(2) > div > div:nth-of-type(1) > div:nth-of-type(2) > span:nth-of-type(2)");
        if (!wrElements.isEmpty()) {
            stats.setWinRate(wrElements.text().trim());
        }

        // Best LP
        Elements bestLpElements = doc.select("aside > section:nth-of-type(" + queueSection + ") > div:nth-of-type(2) > div > div:nth-of-type(2) > div span");
        if (!bestLpElements.isEmpty()) {
            stats.setBestLP(bestLpElements.text().trim());
        }

        // Best Rank
        Elements bestRankElements = doc.select("aside > section:nth-of-type(" + queueSection + ") > div:nth-of-type(2) > div > div:nth-of-type(2) > div strong");
        if (!bestRankElements.isEmpty()) {
            stats.setBestRank(bestRankElements.text().trim());
        }

        return stats;
    }

    private List<ChampionStats> parseChampionStats(Document doc) {
        List<ChampionStats> championStatsList = new ArrayList<>();

        Elements rows = doc.select("section:nth-of-type(2) > div > table > tbody > tr");
        if (rows.isEmpty()) return championStatsList;

        int index = 0;
        for (Element row : rows) {
            // Skip first (index 0) and third (index 2) rows, stop after 6 champions
            if (index == 0 || index == 2) {
                index++;
                continue;
            }
            if (index > 6) break;

            ChampionStats stats = new ChampionStats();
            stats.setPosition(getCellText(row, "td:nth-of-type(1)"));
            stats.setChampion(getCellText(row, "td:nth-of-type(2) > div > strong"));
            stats.setWins(extractWinsLosses(row, "td:nth-of-type(3) > div > div > div:nth-of-type(1) > span"));
            stats.setLosses(extractWinsLosses(row, "td:nth-of-type(3) > div > div > div:nth-of-type(2) > span"));
            stats.setWinRate(getCellText(row, "td:nth-of-type(3) > div > span"));
            stats.setKdaRatio(getCellText(row, "td:nth-of-type(4) > span > div"));
            stats.setKda(getCellText(row, "td:nth-of-type(4) > span > span"));
            stats.setLaning(getCellText(row, "td:nth-of-type(6) > span > span:nth-of-type(1) > span:nth-of-type(1)"));
            stats.setDamagePerMinute(getCellText(row, "td:nth-of-type(7) > span > span:nth-of-type(1)"));
            stats.setDamageShareRatio(getCellText(row, "td:nth-of-type(7) > span > span:nth-of-type(2)"));
            stats.setWardsScore(getCellText(row, "td:nth-of-type(8) > span > span:nth-of-type(1)"));
            stats.setWardsControl(getCellText(row, "td:nth-of-type(8) > span > span:nth-of-type(2)"));
            stats.setCs(getCellText(row, "td:nth-of-type(9) > span > span:nth-of-type(1)"));
            stats.setCsPerMinute(getCellText(row, "td:nth-of-type(9) > span > span:nth-of-type(2)"));
            stats.setGold(getCellText(row, "td:nth-of-type(10) > span > span:nth-of-type(1)"));
            stats.setGoldPerMinute(getCellText(row, "td:nth-of-type(10) > span > span:nth-of-type(2)"));
            stats.setDoubleKills(getCellText(row, "td:nth-of-type(11) > span > span"));
            stats.setTripleKills(getCellText(row, "td:nth-of-type(12) > span > span"));
            stats.setQuadraKills(getCellText(row, "td:nth-of-type(13) > span > span"));
            stats.setPentaKills(getCellText(row, "td:nth-of-type(14) > span > span"));

            championStatsList.add(stats);
            index++;
        }

        return championStatsList;
    }

    private String getCellText(Element row, String selector) {
        Element cell = row.selectFirst(selector);
        return cell != null ? cell.text().trim() : "";
    }

    private String extractWinsLosses(Element row, String selector) {
        Element cell = row.selectFirst(selector);
        if (cell != null) {
            String text = cell.ownText().trim();
            // Extract just the number
            return text.replaceAll("[^0-9]", "");
        }
        return "";
    }
}
