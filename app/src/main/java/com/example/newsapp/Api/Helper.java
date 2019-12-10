package com.example.newsapp.Api;

import com.example.newsapp.models.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Helper {

    @GET("top-headlines?pageSize=20")
    Call<News> getArticles(@Query("country") String country,
                           @Query("category") String category,
                           @Query("page") int page,
                           @Query("apiKey") String apiKey);
}
