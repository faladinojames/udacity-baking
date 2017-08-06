package com.faladionojames.bakingapp;

import android.app.Activity;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.faladionojames.bakingapp.models.RecipeStep;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A fragment representing a single Recipe detail screen.
 * This fragment is either contained in a {@link RecipeStepsActivity}
 * in two-pane mode (on tablets) or a {@link RecipeStepDetailActivity}
 * on handsets.
 */
public class RecipeDetailFragment extends Fragment {

    @InjectView(R.id.video_view)
    SimpleExoPlayerView simpleExoPlayerView;
    TextView instructions;
    RecipeStep recipeStep;
    SimpleExoPlayer simpleExoPlayer;
    long playbackPosition=0;
    int currentWindow=0;
    boolean playWhenReady=true;
    boolean videoAvailable;
    public RecipeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(Constants.STEPS)) {
            recipeStep= (RecipeStep) getArguments().getSerializable(Constants.STEPS);
            videoAvailable=!TextUtils.isEmpty(recipeStep.getVideoUrl());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_detail, container, false);
        ButterKnife.inject(this,rootView);


        instructions=(TextView)rootView.findViewById(R.id.recipe_step_instruction);
        if(instructions!=null)
        instructions.setText(recipeStep.getDesc());
        else{
            //Check if video is available
            if(!videoAvailable)
            {
                Toast.makeText(getActivity(), "Video not available. Please change to portrait mode", Toast.LENGTH_SHORT).show();
            }
        }

        if(!videoAvailable){
            simpleExoPlayerView.setVisibility(View.GONE);
        }


        return rootView;
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri,
                new DefaultHttpDataSourceFactory("ua"),
                new DefaultExtractorsFactory(), null, null);
    }
    private void initializePlayer() {
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getActivity()),
                new DefaultTrackSelector(), new DefaultLoadControl());

        simpleExoPlayerView.setPlayer(simpleExoPlayer);
        simpleExoPlayer.setPlayWhenReady(true);

        if(currentWindow!=0 && playbackPosition!=0)
        simpleExoPlayer.seekTo(currentWindow, playbackPosition);

        Uri uri = Uri.parse(recipeStep.getVideoUrl());
        MediaSource mediaSource = buildMediaSource(uri);
        simpleExoPlayer.prepare(mediaSource, true, false);
    }
    private void releasePlayer() {
        if (simpleExoPlayer != null) {
            playbackPosition = simpleExoPlayer.getCurrentPosition();
            currentWindow = simpleExoPlayer.getCurrentWindowIndex();
            playWhenReady = simpleExoPlayer.getPlayWhenReady();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(videoAvailable)
        initializePlayer();

    }
}
