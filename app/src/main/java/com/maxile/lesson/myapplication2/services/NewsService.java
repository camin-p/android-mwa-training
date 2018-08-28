package com.maxile.lesson.myapplication2.services;

import com.maxile.lesson.myapplication2.services.model.NewsModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsService {
    @GET("eServiceNews.php")
    Call<NewsModel> listNews();
}