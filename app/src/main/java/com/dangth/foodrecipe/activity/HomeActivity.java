package com.dangth.foodrecipe.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.dangth.foodrecipe.R;
import com.dangth.foodrecipe.adapter.HomePagerAdapter;
import com.dangth.foodrecipe.fragment.OptionDialogFragment;
import com.dangth.foodrecipe.searchview.SearchViewQuery;
import com.dangth.foodrecipe.searchview.SuggestionHandler;
import com.dangth.foodrecipe.services.FeedAPI;
import com.dangth.foodrecipe.services.RetrofitClientInstance;
import com.dangth.foodrecipe.services.model.FeedResponse;
import com.dangth.foodrecipe.services.model.HomeTabModel;
import com.dangth.foodrecipe.services.model.Recipe;
import com.dangth.foodrecipe.utils.FileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener, AppBarLayout.OnOffsetChangedListener  {

    private FloatingSearchView searchView;
    private ProgressBar progressBar;
    private FeedAPI feedAPI;
    private ConstraintLayout errorView;
    private HomePagerAdapter adapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private CoordinatorLayout coordinatorLayout;

    public List<Recipe> recentRecipe;
    public List<FeedResponse.FeedItem> carouselList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bindViews();
        loadFeedData();
    }

    private void bindViews() {
        /* FeedAPI Instance */
        feedAPI = RetrofitClientInstance.getRetrofitInstance().create(FeedAPI.class);

        /* progressbar */
        progressBar = findViewById(R.id.progressBar);

        /* Coordinator */
        coordinatorLayout = findViewById(R.id.coordinator);

        /* SearchView */
        searchView = findViewById(R.id.floating_search_view);
        searchView.setOnSearchListener(new SearchViewQuery(HomeActivity.this, searchView));
        searchView.setOnQueryChangeListener(new SuggestionHandler(searchView));
        searchView.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_filter) {
                OptionDialogFragment optionDialogFragment = OptionDialogFragment.newInstance();
                optionDialogFragment.show(getSupportFragmentManager(), optionDialogFragment.getTag());
                optionDialogFragment.setOnDialogDismissListener(this::loadFeedData);
            }
            else if (item.getItemId() == R.id.action_profile) {
                Intent intent = new Intent(HomeActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });

        /* appbar */
        AppBarLayout appBarLayout;
        appBarLayout = findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(this);

        /* Error View */
        errorView = findViewById(R.id.errorView);

        /* RetryButton */
        LinearLayout retryButton = findViewById(R.id.retryButton);
        retryButton.setOnClickListener(view -> {
            errorView.setVisibility(View.GONE);
            loadFeedData();
        });

        /* offline mode text */
        TextView offlineText  = findViewById(R.id.offlineMode);
        offlineText.setOnClickListener(v -> {
            FeedResponse feedResponse = FileUtils.readOfflineData(this);
            if (feedResponse != null) loadDataFeedView(feedResponse);
            else Toast.makeText(this, "Error reading file", Toast.LENGTH_SHORT).show();
        });
        if (!FileUtils.isOfflineDataExist(getFilesDir())) offlineText.setVisibility(View.GONE);

        /* HomePagerAdapter */
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

    }
    /**
     * Get feed data from API through Retrofit
     * send data to loadDataFeedView()
     *
     */
    public void loadFeedData() {
        progressBar.setVisibility(View.VISIBLE);
        SharedPreferences sharedPreferences = getSharedPreferences(OptionDialogFragment.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        boolean vegetarian = sharedPreferences.getBoolean(OptionDialogFragment.SHARED_PREFERENCES_VEGETARIAN, false);
        Call<FeedResponse> call = feedAPI.getFeedData(vegetarian, 15, 0);
        call.enqueue(new Callback<FeedResponse>() {
            @Override
            public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {
                if (response.body() != null) {
                    boolean isDone = FileUtils.writeOfflineData(response.body(), HomeActivity.this);
                    Log.i("OFFLINE_DATA", "onResponse: Write file status = " + isDone);
                    loadDataFeedView(response.body());
                }
                else {
                    Toast.makeText(HomeActivity.this, "Something went wrong...", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<FeedResponse> call, Throwable t) {
                Log.e("Fail", t.getMessage());
                progressBar.setVisibility(View.GONE);
                errorView.setVisibility(View.VISIBLE);

            }
        });
    }

    private void loadDataFeedView(FeedResponse feedResponse) {
        HashMap<Integer, Recipe> likeData = FileUtils.readLikeData(getFilesDir());
        Log.i("DEMO", "loadDataFeedView: load start");
        List<FeedResponse.FeedItem> feedItemList = feedResponse.getResults();
        List<Recipe> recipeList = new ArrayList<>();
        List<FeedResponse.FeedItem> carouselList = new ArrayList<>();
        Log.i("DEMO", "loadDataFeedView: " + feedItemList);
        HomeTabModel homeTabModel = new HomeTabModel();
        for (FeedResponse.FeedItem feedItem : feedItemList) {
            if (feedItem.getContent()!=null) {
                if (feedItem.getType().equals("featured")) {
                    homeTabModel.featureItem = feedItem;
                }
                else if (feedItem.getType().equals("item")) {
                    recipeList.add(feedItem.getContent());
                }
            } else if (feedItem.getCarouselItem() != null) {
                carouselList.add(feedItem);
            }
        }

        // set like for each recipe
        if (likeData != null) {
            for (Recipe recipe : recipeList) {
                if (likeData.get(recipe.getId()) != null) recipe.like = true;
            }
            for (FeedResponse.FeedItem feedItem : carouselList) {
                for (Recipe recipe : feedItem.getCarouselItem()) {
                    if (likeData.get(recipe.getId()) != null) recipe.like = true;
                }
            }
        }

        // public bind to fragment
        this.recentRecipe = recipeList;
        this.carouselList = carouselList;

        adapter = new HomePagerAdapter(getSupportFragmentManager(), homeTabModel, carouselList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        if (coordinatorLayout.getVisibility() == View.INVISIBLE) {
            coordinatorLayout.setVisibility(View.VISIBLE);
            searchView.setVisibility(View.VISIBLE);
        }
        errorView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

        @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        searchView.setTranslationY(i);
    }

    @Override
    public void onClick(View v) {

    }
}
