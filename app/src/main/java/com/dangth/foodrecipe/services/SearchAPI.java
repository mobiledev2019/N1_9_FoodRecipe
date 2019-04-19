package com.dangth.foodrecipe.services;

import com.dangth.foodrecipe.services.model.Compilation;
import com.dangth.foodrecipe.services.model.SearchRecipeResponse;
import com.dangth.foodrecipe.services.model.SuggestResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SearchAPI {
    @GET("/search/recipes?from=0&size=10")
    @Headers("x-auth-token: 6e8bf895-59aa-444b-b846-beec53f0e007")
    Call<SearchRecipeResponse> searchRecipes(@Query("q") String query);

    @GET("/suggest")
    @Headers("x-auth-token: 6e8bf895-59aa-444b-b846-beec53f0e007")
    Call<SuggestResponse> suggest(@Query("prefix") String prefix);

    @GET("/search/compilations/{id}")
    @Headers("x-auth-token: 6e8bf895-59aa-444b-b846-beec53f0e007")
    Call<Compilation> searchCompilation(@Path("id") int id);
}
