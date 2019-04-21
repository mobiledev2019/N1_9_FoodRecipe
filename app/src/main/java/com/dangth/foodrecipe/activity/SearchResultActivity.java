package com.dangth.foodrecipe.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.dangth.foodrecipe.R;
import com.dangth.foodrecipe.adapter.RecipeListAdapter;
import com.dangth.foodrecipe.fragment.OptionDialogFragment;
import com.dangth.foodrecipe.searchview.SearchViewQuery;
import com.dangth.foodrecipe.searchview.SuggestionHandler;
import com.dangth.foodrecipe.services.RetrofitClientInstance;
import com.dangth.foodrecipe.services.SearchAPI;
import com.dangth.foodrecipe.services.model.SearchRecipeResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultActivity extends AppCompatActivity implements
        SwipeRefreshLayout.OnRefreshListener, AppBarLayout.OnOffsetChangedListener {
    private String searchQuery;
    private FloatingSearchView searchView;
    private RecipeListAdapter adapter;
    private SearchAPI searchAPI;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout noMatchLayout;
    private AppBarLayout appBarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        bindView();
        swipeRefreshLayout.setRefreshing(true);
        Intent intent = getIntent();
        searchQuery = intent.getStringExtra(SearchViewQuery.SEARCH_QUERY);
        searchView.setSearchText(searchQuery);
        loadSearchData();
    }

    /**
     * bind view for widget
     */

    private void bindView() {
        /* SearchAPI */
        searchAPI = RetrofitClientInstance.getRetrofitInstance().create(SearchAPI.class);

        /* SearchView */
        searchView = findViewById(R.id.floating_search_view);
        searchView.setLeftActionMode(FloatingSearchView.LEFT_ACTION_MODE_SHOW_HOME);
        searchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                searchView.clearSearchFocus();
                swipeRefreshLayout.setRefreshing(true);
                searchQuery = searchSuggestion.getBody();
                loadSearchData();
            }

            @Override
            public void onSearchAction(String currentQuery) {
                // load result when searchText submitted
                searchView.clearSearchFocus();
                swipeRefreshLayout.setRefreshing(true);
                searchQuery = currentQuery;
                loadSearchData();
            }
        });
        searchView.setOnHomeActionClickListener(this::finish);
        searchView.setOnQueryChangeListener(new SuggestionHandler(searchView));
        searchView.setOnMenuItemClickListener(item -> {
            OptionDialogFragment optionDialogFragment = OptionDialogFragment.newInstance();
            optionDialogFragment.show(getSupportFragmentManager(), optionDialogFragment.getTag());
            optionDialogFragment.setOnDialogDismissListener(() -> {
               swipeRefreshLayout.setRefreshing(true);
               loadSearchData();
            });
        });

        /* RecyclerView Recent */
        RecyclerView rvRecent = findViewById(R.id.rvRecent);
        rvRecent.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rvRecent.setItemAnimator(new DefaultItemAnimator());
        adapter = new RecipeListAdapter(this, R.layout.recycler_view_item);
        rvRecent.setAdapter(adapter);

        /* SwipeRefreshLayout */
        swipeRefreshLayout = findViewById(R.id.mainSwipeRefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(this);

        /* No match view */
        noMatchLayout = findViewById(R.id.noMatchLayout);

        /* ClearSearchButton */
        TextView clearSearchButton = findViewById(R.id.clearSearchButton);
        clearSearchButton.setOnClickListener(view -> {
            searchView.setSearchFocused(true);
            searchView.clearQuery();
        });

        /* appbar */
        appBarLayout = findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(this);
    }

    /**
     * download search result
     *
     */
    private void loadSearchData() {
        SharedPreferences sharedPreferences = getSharedPreferences(OptionDialogFragment.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        boolean vegetarian = sharedPreferences.getBoolean(OptionDialogFragment.SHARED_PREFERENCES_VEGETARIAN, false);
        if (vegetarian) {
            searchQuery = "_exists_:approved_at%20AND%20" + searchQuery + "%20AND%20tags.name:vegetarian";
        }
        Call<SearchRecipeResponse> call = searchAPI.searchRecipes(searchQuery);
        call.enqueue(new Callback<SearchRecipeResponse>() {
            @Override
            public void onResponse(Call<SearchRecipeResponse> call, Response<SearchRecipeResponse> response) {
                if (response.body() != null) {
                    loadDataView(response.body());
                }
                else {
                    Toast.makeText(SearchResultActivity.this, "Something went wrong...", Toast.LENGTH_LONG).show();
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<SearchRecipeResponse> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(SearchResultActivity.this, "Something went wrong...", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Bind data to adapter
     * @param response search result
     */
    private void loadDataView(SearchRecipeResponse response) {
        if (response.getResults() != null) {
            if (response.getResults().size() == 0) {
                noMatchLayout.setVisibility(View.VISIBLE);
                adapter.setRecipeList(null);
                adapter.notifyDataSetChanged();
            } else {
                adapter.setRecipeList(response.getResults());
                adapter.notifyDataSetChanged();
                if (noMatchLayout.getVisibility() == View.VISIBLE) {
                    noMatchLayout.setVisibility(View.GONE);
                }
            }
        }
        else {
            noMatchLayout.setVisibility(View.VISIBLE);
            adapter.setRecipeList(null);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        loadSearchData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        searchView.setTranslationY(i);
    }
}
