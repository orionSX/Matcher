package com.akiora.lolparser.services;

import com.akiora.lolparser.models.SummonerStats;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

@Service
public class OpGgService {

    private final OpGgStatsParser parser;

    public OpGgService(OpGgStatsParser parser) {
        this.parser = parser;
    }

    public SummonerStats getSummonerStats(String region, String summonerName, String tagLine) throws IOException {
        String url = buildSummonerUrl(region, summonerName, tagLine);
        return getSummonerStatsByUrl(url);
    }

    public SummonerStats getSummonerStatsByUrl(String url) throws IOException {
        // Validate and normalize URL
       
        if (!url.startsWith("http")) {
            throw new IllegalArgumentException("Invalid URL format. URL must start with http or https");
        }

        try {
            // Parallel fetch of main page and champions page
            String champsUrl = url.endsWith("/") ? url + "champions" : url + "/champions";

            CompletableFuture<Document> mainFuture = CompletableFuture.supplyAsync(() -> {
                try {
                    return Jsoup.connect(url)
                            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                            .referrer("https://op.gg/")
                            .timeout(10000)
                            .get();
                } catch (IOException e) {
                    throw new RuntimeException("Error loading main page: " + e.getMessage(), e);
                }
            });

            CompletableFuture<Document> champsFuture = CompletableFuture.supplyAsync(() -> {
                try {
                    return Jsoup.connect(champsUrl)
                            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                            .referrer("https://op.gg/")
                            .timeout(10000)
                            .get();
                } catch (IOException e) {
                    throw new RuntimeException("Error loading champions page: " + e.getMessage(), e);
                }
            });

            // Wait for both to complete
            CompletableFuture.allOf(mainFuture, champsFuture).join();

            Document mainDoc = mainFuture.get();
            Document champsDoc = champsFuture.get();

            return parser.parseStats(mainDoc, champsDoc);

        } catch (Exception ex) {
            throw new IOException("Error parsing summoner stats: " + ex.getMessage(), ex);
        }
    }

    public static String buildSummonerUrl(String region, String summonerName, String tagLine) {
        try {
            String encodedName = URLEncoder.encode(summonerName, StandardCharsets.UTF_8.toString());
            
            if (tagLine != null && !tagLine.isEmpty() && !tagLine.isBlank()) {
                return String.format("https://op.gg/en/lol/summoners/%s/%s-%s", 
                    region.toLowerCase(), encodedName, tagLine);
            }
            
            return String.format("https://op.gg/en/lol/summoners/%s/%s", 
                region.toLowerCase(), encodedName);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Error encoding summoner name", e);
        }
    }
}
