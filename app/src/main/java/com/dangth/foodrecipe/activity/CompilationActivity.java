package com.dangth.foodrecipe.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dangth.foodrecipe.GlideApp;
import com.dangth.foodrecipe.R;
import com.dangth.foodrecipe.adapter.RecipeListAdapter;
import com.dangth.foodrecipe.services.model.Compilation;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class CompilationActivity extends AppCompatActivity {
    private SimpleExoPlayer player;
    private PlayerView playerView;
    private ImageView thumbnail;
    private ProgressBar progressBar;
    private Compilation compilation;
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
        progressBar.setVisibility(View.VISIBLE);

        /* TextView title */
        TextView tvTitleCollapse = findViewById(R.id.tvTitle);
        tvTitleCollapse.setText(compilation.getName());

        /* ExoPlayer */
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

        /* RecyclerView */

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecipeListAdapter adapter = new RecipeListAdapter(compilation.getRecipes(), this);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void finish() {
        super.finish();
        player.release();
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.setPlayWhenReady(false);
        playerView.showController();
    }
}
