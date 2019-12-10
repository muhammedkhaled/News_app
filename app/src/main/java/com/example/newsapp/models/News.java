package com.example.newsapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class News {

    @SerializedName("articles")
    private List<ArticlesList> articles ;

    public News() {
    }

    public News(List<ArticlesList> articles) {
        this.articles = articles;
    }

    public List<ArticlesList> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticlesList> articles) {
        this.articles = articles;
    }
}
