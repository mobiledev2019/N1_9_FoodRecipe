package com.dangth.foodrecipe.services;

import com.dangth.foodrecipe.services.model.FeedResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface FeedAPI {
    @GET("/users/feed?&timezone=%2B0700")
    @Headers({
            "x-auth-token: 6e8bf895-59aa-444b-b846-beec53f0e007",
            "x-tasty-auth-type: anonymous",
            "if-none-match: a0786c1cbbeb075e7080eec53ae71be8b5ab0dc8"
    })
    Call<FeedResponse> getFeedData(@Query("vegetarian") Boolean vegetarian,@Query("size") Integer size,@Query("from") Integer from);
}
