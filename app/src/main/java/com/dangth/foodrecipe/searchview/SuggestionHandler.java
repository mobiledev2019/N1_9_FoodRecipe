package com.dangth.foodrecipe.searchview;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.dangth.foodrecipe.services.RetrofitClientInstance;
import com.dangth.foodrecipe.services.SearchAPI;
import com.dangth.foodrecipe.services.model.SuggestResponse;
import com.dangth.foodrecipe.services.model.Suggestion;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuggestionHandler implements FloatingSearchView.OnQueryChangeListener {
    private FloatingSearchView searchView;
    private SearchAPI searchAPI;

    public SuggestionHandler(FloatingSearchView searchView) {
        this.searchView = searchView;
        createSearchAPI();
    }

    private void createSearchAPI() {
        searchAPI = RetrofitClientInstance.getRetrofitInstance().create(SearchAPI.class);
    }
    @Override
    public void onSearchTextChanged(String oldQuery, String newQuery) {
        if (!oldQuery.equals("") && newQuery.equals("")) {
            searchView.clearSuggestions();
        } else {
            searchView.showProgress();
            Call<SuggestResponse> call = searchAPI.suggest(newQuery);
            call.enqueue(new Callback<SuggestResponse>() {
                @Override
                public void onResponse(Call<SuggestResponse> call, Response<SuggestResponse> response) {
                    searchView.hideProgress();
                    SuggestResponse suggestResponse = response.body();
                    if (suggestResponse != null) {
                        List<Suggestion> suggestionList = suggestResponse.getResults();
                        searchView.swapSuggestions(suggestionList);
                    }
                }

                @Override
                public void onFailure(Call<SuggestResponse> call, Throwable t) {
                    searchView.hideProgress();
                    searchView.clearSearchFocus();
                }
            });
        }
    }
}
