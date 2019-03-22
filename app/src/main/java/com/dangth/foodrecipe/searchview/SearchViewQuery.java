package com.dangth.foodrecipe.searchview;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.dangth.foodrecipe.activity.SearchResultActivity;

public class SearchViewQuery implements FloatingSearchView.OnSearchListener {
    public static String SEARCH_QUERY = "SEARCH_QUERY";
    private Context context;
    private FloatingSearchView searchView;
    public SearchViewQuery(Context context, FloatingSearchView searchView) {
        this.searchView = searchView;
        this.context = context;
    }

    @Override
    public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
        searchView.clearSearchFocus();
        searchView.clearQuery();
        Toast.makeText(context, searchSuggestion.getBody(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, SearchResultActivity.class);
        intent.putExtra(SEARCH_QUERY, searchSuggestion.getBody());
        context.startActivity(intent);
    }

    @Override
    public void onSearchAction(String currentQuery) {
        searchView.clearSearchFocus();
        searchView.clearQuery();
        Toast.makeText(context, currentQuery, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, SearchResultActivity.class);
        intent.putExtra(SEARCH_QUERY, currentQuery);
        context.startActivity(intent);
    }
}