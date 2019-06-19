package com.dangth.foodrecipe.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dangth.foodrecipe.GlideApp;
import com.dangth.foodrecipe.R;
import com.dangth.foodrecipe.activity.StepViewActivity;
import com.dangth.foodrecipe.adapter.StepViewAdapter;
import com.dangth.foodrecipe.services.model.Instruction;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ClippingMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class StepViewFragment extends Fragment {
    private static final String STF_KEY_POS = "stf_key_pos";
    private SimpleExoPlayer player;
    private PlayerView playerView;

    public static StepViewFragment getInstance(int pos) {
        StepViewFragment stepViewFragment = new StepViewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(STF_KEY_POS, pos);
        stepViewFragment.setArguments(bundle);
        return stepViewFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_step_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        playerView = view.findViewById(R.id.videoSurfaceContainer);
        ConstraintLayout videoContainer = view.findViewById(R.id.videoContainer);
        TextView instructionText = view.findViewById(R.id.stepText);
        ImageView thumbnail = view.findViewById(R.id.coverImageView);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        Bundle bundle = getArguments();
        Instruction instruction;
        if (bundle!=null && getContext() != null && getActivity() != null) {
            if (((StepViewActivity) getActivity()).isNetworkAvailable()){
                progressBar.setVisibility(View.VISIBLE);
            }
            else {
                progressBar.setVisibility(View.GONE);
            }
            int pos = bundle.getInt(STF_KEY_POS);
            instruction = ((StepViewActivity) getActivity()).instructionList.get(pos);
            instructionText.setText(instruction.getDisplayText());
            GlideApp
                    .with(getContext())
                    .load(((StepViewActivity) getActivity()).thumbnail)
                    .centerCrop()
                    .into(thumbnail);
            if (instruction.getStartTime() == instruction.getEndTime() && instruction.getEndTime() == 0L) {
                videoContainer.setVisibility(View.INVISIBLE);
                instructionText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
            }
            else {
                Log.i("STEP_VIEW", "onViewCreated: " + ((StepViewActivity) getActivity()).url);
                player = ExoPlayerFactory.newSimpleInstance(getContext());
                playerView.setControllerAutoShow(false);
                DataSource.Factory dataFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "FoodRecipe"));
                HlsMediaSource mediaSource = new HlsMediaSource.Factory(dataFactory).createMediaSource(Uri.parse(((StepViewActivity) getActivity()).url));
                ClippingMediaSource clippingMediaSource = new ClippingMediaSource(mediaSource, instruction.getStartTime() * 1000, instruction.getEndTime() * 1000);
                player.prepare(clippingMediaSource, true, false);
                player.setVolume(0);
                if (pos == 0) player.setPlayWhenReady(true);
                playerView.hideController();
                player.addListener(new Player.EventListener() {
                    @Override
                    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                        if (playbackState == Player.STATE_READY) {
                            if (progressBar.getVisibility() == View.VISIBLE) {
                                progressBar.setVisibility(View.GONE);
                                thumbnail.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                });
                playerView.setPlayer(player);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (player != null && playerView != null) {
            player.setPlayWhenReady(false);
            playerView.showController();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser) {
            if (player != null && playerView != null) {
                player.setPlayWhenReady(false);
                playerView.showController();
            }
        }
        else {
            if (player != null && playerView != null) {
                player.setPlayWhenReady(true);
                playerView.showController();
            }
        }
    }
}
