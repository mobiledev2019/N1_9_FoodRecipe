package com.dangth.foodrecipe.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dangth.foodrecipe.GlideApp;
import com.dangth.foodrecipe.R;
import com.dangth.foodrecipe.adapter.RecipeListAdapter;
import com.dangth.foodrecipe.services.model.Compilation;
import com.dangth.foodrecipe.services.model.Recipe;
import com.dangth.foodrecipe.utils.AsyncTaskLikeRecipe;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class CompilationActivity extends AppCompatActivity {
    private SimpleExoPlayer player;
    private PlayerView playerView;
    private ImageView thumbnail;
    private ProgressBar progressBar;
    private Recipe compilation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compilation);
        compilation = getIntent().getParcelableExtra(RecipeListAdapter.INTENT_COMPILATION_ACTIVITY);
        bindView();
    }

    private void bindView() {
        /* thumb image */
        thumbnail = findViewById(R.id.coverImageView);
        GlideApp.with(this)
                .load(compilation.getThumbnail_url())
                .transition(withCrossFade())
                .centerCrop()
                .into(thumbnail);

        /* ProgressBar */
        progressBar = findViewById(R.id.progressBar);
        if (isNetworkAvailable()) progressBar.setVisibility(View.VISIBLE);

        /* TextView title */
        TextView tvTitleCollapse = findViewById(R.id.tvTitle);
        tvTitleCollapse.setText(compilation.getName());

        /* ExoPlayer */
        if (isNetworkAvailable()) {
            playerView = findViewById(R.id.videoSurfaceContainer);
            playerView.setControllerAutoShow(false);
            player = ExoPlayerFactory.newSimpleInstance(this);
            DataSource.Factory dataFactory = new DefaultDataSourceFactory(this,  Util.getUserAgent(this, "FoodRecipe"));
            HlsMediaSource mediaSource = new HlsMediaSource.Factory(dataFactory).createMediaSource(Uri.parse(compilation.getVideo_url()));
            player.prepare(mediaSource,true, false);
            player.setPlayWhenReady(true);
            player.addListener(new Player.EventListener() {
                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                    if (playbackState == Player.STATE_READY) {
                        if (thumbnail.getVisibility() == View.VISIBLE) {
                            thumbnail.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                }
            });
            playerView.setPlayer(player);
        }

        /* RecyclerView */

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecipeListAdapter adapter = new RecipeListAdapter(compilation.getRecipes(), this, R.layout.recycler_view_item);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        /* Appbar Control */
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> finish());

        /* LikeButton */

        SparkButton sparkButton = findViewById(R.id.spark_button);
        sparkButton.setChecked(compilation.like);
        sparkButton.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView imageView, boolean buttonState) {
                compilation.like = buttonState;
                AsyncTaskLikeRecipe likeRecipeTask =
                        new AsyncTaskLikeRecipe(compilation, getFilesDir());
                likeRecipeTask.execute();
            }

            @Override
            public void onEventAnimationEnd(ImageView imageView, boolean b) {

            }

            @Override
            public void onEventAnimationStart(ImageView imageView, boolean b) {

            }
        });

        /* shareButton */

        ImageView shareBtn = findViewById(R.id.shareButton);
        shareBtn.setOnClickListener(view -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "https://tasty.co/compilation/" + compilation.slug);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        });
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void finish() {
        super.finish();
        if (player != null) player.release();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null && playerView != null) {
            player.setPlayWhenReady(false);
            playerView.showController();
        }
    }
}
