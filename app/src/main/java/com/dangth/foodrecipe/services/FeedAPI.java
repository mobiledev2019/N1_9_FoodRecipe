package com.dangth.foodrecipe.services;

import com.dangth.foodrecipe.services.model.FeedResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface FeedAPI {
    @GET("/users/feed?vegetarian=false&timezone=%2B0700&size=15&from=0")
    @Headers({
            "x-auth-token: 6e8bf895-59aa-444b-b846-beec53f0e007",
            "x-tasty-auth-type: anonymous",
            "if-none-match: a0786c1cbbeb075e7080eec53ae71be8b5ab0dc8"
    })
    Call<FeedResponse> getFeedData();
}
