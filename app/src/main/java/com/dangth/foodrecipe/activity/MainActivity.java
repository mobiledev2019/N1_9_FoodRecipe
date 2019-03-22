package com.dangth.foodrecipe.activity;

import android.content.Intent;
import android.os.Parcelable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.dangth.foodrecipe.GlideApp;
import com.dangth.foodrecipe.R;
import com.dangth.foodrecipe.adapter.RecipeListAdapter;
import com.dangth.foodrecipe.searchview.SuggestionHandler;
import com.dangth.foodrecipe.services.FeedAPI;
import com.dangth.foodrecipe.services.RetrofitClientInstance;
import com.dangth.foodrecipe.services.model.Compilation;
import com.dangth.foodrecipe.services.model.FeedResponse;
import com.dangth.foodrecipe.services.model.Recipe;
import com.dangth.foodrecipe.searchview.SearchViewQuery;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener, AppBarLayout.OnOffsetChangedListener {
    private FloatingSearchView searchView;
    private RecipeListAdapter adapter;
    private ImageView ibFeatureItem;
    private TextView tvFeatureTitle;
    private NestedScrollView nestedScrollView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ConstraintLayout errorView;
    private LinearLayout retryButton;
    private FeedAPI feedAPI;
    private AppBarLayout appBarLayout;
    private Recipe featureItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setHomeButtonEnabled(false);
        swipeRefreshLayout.setRefreshing(true);
        loadFeedData();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);

        return true;
    }
    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    /**
     * bind view for widget
     */

    private void bindView() {
        /* FeedAPI Instance */
        feedAPI = RetrofitClientInstance.getRetrofitInstance().create(FeedAPI.class);

        /* ImageButton Feature */
        ibFeatureItem = findViewById(R.id.ivFeatureItem);
        ibFeatureItem.setOnClickListener(this);

        /* Feature title */
        tvFeatureTitle = findViewById(R.id.featureTitle);

        /* SearchView */
        searchView = findViewById(R.id.floating_search_view);
        searchView.setOnSearchListener(new SearchViewQuery(MainActivity.this, searchView));
        searchView.setOnQueryChangeListener(new SuggestionHandler(searchView));

        /* RecyclerView Recent */
        RecyclerView rvRecent = findViewById(R.id.rvRecent);
        rvRecent.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rvRecent.setItemAnimator(new DefaultItemAnimator());
        adapter = new RecipeListAdapter(this);
        rvRecent.setAdapter(adapter);

        /* NSV */
        nestedScrollView = findViewById(R.id.scrollMainView);
        nestedScrollView.setVisibility(View.INVISIBLE);

        /* SwipeRefreshLayout */
        swipeRefreshLayout = findViewById(R.id.mainSwipeRefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(this);

        /* Error View */
        errorView = findViewById(R.id.errorView);

        /* RetryButton */
        retryButton = findViewById(R.id.retryButton);
        retryButton.setOnClickListener(view ->{
            swipeRefreshLayout.setRefreshing(true);
            loadFeedData();
        });

        /* appbar */
        appBarLayout = findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(this);
    }

    /**
     * Get feed data from API through Retrofit
     * send data to loadDataFeedView()
     *
     */
    public void loadFeedData() {
        Call<FeedResponse> call = feedAPI.getFeedData();
        call.enqueue(new Callback<FeedResponse>() {
            @Override
            public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {
                if (response.body() != null) {
                    if (nestedScrollView.getVisibility() == View.GONE) {
                        nestedScrollView.setVisibility(View.VISIBLE);
                    }
                    errorView.setVisibility(View.GONE);
                    loadDataFeedView(response.body());
                }
                else {
                    Toast.makeText(MainActivity.this, "Something went wrong...", Toast.LENGTH_LONG).show();
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<FeedResponse> call, Throwable t) {
                Log.e("Fail", t.getMessage());
                swipeRefreshLayout.setRefreshing(false);
                nestedScrollView.setVisibility(View.GONE);
                errorView.setVisibility(View.VISIBLE);

            }
        });
    }

    /**
     * set data for FeatureItem and Recipe RecyclerView
     * @param feedResponse feed data
     */

    private void loadDataFeedView(FeedResponse feedResponse) {
        List<FeedResponse.FeedItem> feedItemList = feedResponse.getResults();
        List<Recipe> recipeList = new ArrayList<>();
        for (FeedResponse.FeedItem feedItem : feedItemList) {
            if (feedItem.getContent()!=null) {
                if (feedItem.getType().equals("featured")) {
                    featureItem = feedItem.getContent();
                    tvFeatureTitle.setText(feedItem.getContent().getName());
                    GlideApp.with(MainActivity.this)
                            .load(feedItem.getContent().getThumbnail_url())
                            .transition(withCrossFade())
                            .centerCrop()
                            .into(ibFeatureItem);
                }
                else if (feedItem.getType().equals("item")) {
                    recipeList.add(feedItem.getContent());
                }
            }
        }
        if (nestedScrollView.getVisibility() == View.INVISIBLE) {
            nestedScrollView.setVisibility(View.VISIBLE);
        }
        adapter.setRecipeList(recipeList);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivFeatureItem :
                if (!featureItem.isComplition()) {
                    Intent intent = new Intent(MainActivity.this, RecipeActivity.class);
                    intent.putExtra(RecipeListAdapter.INTENT_RECIPE_ACTIVITY, (Parcelable) featureItem);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, CompilationActivity.class);
                    Compilation compilation = new Compilation();
                    compilation.setRecipes(featureItem.getRecipes());
                    compilation.setThumbnail_url(featureItem.getThumbnail_url());
                    compilation.setName(featureItem.getName());
                    compilation.setVideo_url(featureItem.getVideo_url());
                    intent.putExtra(RecipeListAdapter.INTENT_RECIPE_ACTIVITY, (Parcelable) compilation);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        loadFeedData();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        searchView.setTranslationY(i);
    }
}
