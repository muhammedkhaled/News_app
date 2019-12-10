package com.example.newsapp.models;

import com.google.gson.annotations.SerializedName;

public class ArticlesList {

    @SerializedName("author")
    private String author;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("urlToImage")
    private String thumbnail;

    @SerializedName("publishedAt")
    private String publishedAt;

    @SerializedName("url")
    private String url;

    public ArticlesList() {
    }

    public ArticlesList(String author, String title, String description, String thumbnail, String publishedAt, String url) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.publishedAt = publishedAt;
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
