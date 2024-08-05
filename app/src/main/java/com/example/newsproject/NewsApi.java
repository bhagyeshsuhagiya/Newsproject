package com.example.newsproject;

public class NewsApi {
    private static final String BASE_URL = "https://gnews.io/api/v4/top-headlines";
    private static final String API_KEY = "3c92971f9902b282fcccda992fcc0549"; // Replace with your actual API key

    public static String buildUrl(String category, String language, String country) {
        int max = 10; // Number of articles to retrieve

        // Construct the URL with the provided parameters
        return BASE_URL + "?category=" + category + "&lang=" + language + "&country=" + country + "&max=" + max + "&apikey=" + API_KEY;
    }
}
