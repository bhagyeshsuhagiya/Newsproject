package com.example.newsproject;

public class PostModel {
    private String imageUrl;
    private String name;
    private String date;
    private String time;
    private String url;

    // Constructor, Getters, and Setters


    public PostModel() {
    }

    public PostModel(String imageUrl, String name, String date, String time, String url) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.date = date;
        this.time = time;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
