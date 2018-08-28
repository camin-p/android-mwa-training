package com.maxile.lesson.myapplication2.services.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsModel {
    @SerializedName("new_list")
    public List<NewsItem> new_list;
}
