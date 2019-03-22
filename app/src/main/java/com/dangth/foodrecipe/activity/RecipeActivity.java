package com.dangth.foodrecipe.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dangth.foodrecipe.GlideApp;
import com.dangth.foodrecipe.R;
import com.dangth.foodrecipe.adapter.InstructionAdapter;
import com.dangth.foodrecipe.adapter.RecipeListAdapter;
import com.dangth.foodrecipe.fragment.IngredientSheetFragment;
import com.dangth.foodrecipe.services.model.Recipe;
import com.dangth.foodrecipe.services.model.Section;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


public class RecipeActivity extends AppCompatActivity {
    private SimpleExoPlayer player;
    private PlayerView playerView;
    private ImageView thumbnail;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private InstructionAdapter instructionAdapter;
    private Recipe recipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        recipe = getIntent().getParcelableExtra(RecipeListAdapter.INTENT_RECIPE_ACTIVITY);
        bindView();
    }
    private void bindView() {
        /* thumb image */
        thumbnail = findViewById(R.id.coverImageView);
        GlideApp.with(this)
                .load(recipe.getThumbnail_url())
                .transition(withCrossFade())
                .centerCrop()
                .into(thumbnail);

        /* ProgressBar */
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        /* TextView title */
        TextView tvTitleCollapse = findViewById(R.id.tvTitle);
        tvTitleCollapse.setText(recipe.getName());

        /* ExoPlayer */
        playerView = findViewById(R.id.videoSurfaceContainer);
        playerView.setControllerAutoShow(false);
        player = ExoPlayerFactory.newSimpleInstance(this);
        DataSource.Factory dataFactory = new DefaultDataSourceFactory(this,  Util.getUserAgent(this, "FoodRecipe"));
        HlsMediaSource mediaSource = new HlsMediaSource.Factory(dataFactory).createMediaSource(Uri.parse(recipe.getVideo_url()));
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

        /* Button StepView */
        Button button = findViewById(R.id.stepByStepButton);
        button.setOnClickListener(view -> {
            player.setPlayWhenReady(false);
            playerView.showController();
            Intent intent = new Intent(RecipeActivity.this, InstructionPagerActivity.class);
            startActivity(intent);
        });

        /*RecyclerView - Instruction Adapter */
        instructionAdapter = new InstructionAdapter(this);
        instructionAdapter.setInstructionList(recipe.getInstructions());
        recyclerView = findViewById(R.id.preparationView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(instructionAdapter);

        /* Ingredient Button */
        Button btnIngredient = findViewById(R.id.btnIngredient);
        btnIngredient.setOnClickListener(view -> {
            player.setPlayWhenReady(false);
            playerView.showController();
            IngredientSheetFragment bottomSheetFragment = IngredientSheetFragment.newInstance((ArrayList<Section>) recipe.getSections());
            bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
        });

        /* Appbar Control */
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> finish());
    }

    @Override
    public void finish() {
        super.finish();
        player.release();
    }
}
