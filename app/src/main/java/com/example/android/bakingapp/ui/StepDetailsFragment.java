package com.example.android.bakingapp.ui;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class StepDetailsFragment extends Fragment {

    private ArrayList<Step> mStepsSent;
    private int mStepSelected;
    private ViewPager mPager;
    private int mNum;

    //Exoplayer
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private boolean playWhenReady = false;
    private int currentWindow = 0;
    private Long playbackPosition = 0L;
    private String videoUrl;
    private String thumbnailUrl;


    public StepDetailsFragment(){

    }

    public static StepDetailsFragment newInstance(Bundle bundle, int position){

            StepDetailsFragment f = new StepDetailsFragment();
            Bundle bundlePosition = new Bundle();
            bundlePosition.putInt("position", position);
            bundlePosition.putBundle("fragmentBundle", bundle);
            f.setArguments(bundlePosition);
            return f;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mNum = getArguments() != null ? getArguments().getInt("position") : 1;
        mNum = getArguments().getInt("position", 0);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle fragmentBundle = getArguments().getBundle("fragmentBundle");
        mStepsSent = fragmentBundle.getParcelableArrayList("steps");
        mStepSelected = fragmentBundle.getInt("stepSelected");
        videoUrl = mStepsSent.get(mNum).getVideoURL();
        thumbnailUrl = mStepsSent.get(mNum).getThumbNailURL();



        View view = inflater.inflate(R.layout.fragment_step_details, container, false);
        setDescription(view);
        videoOrThumbnail(view);


        //description, id, shortDescription, thumbNailURL, videoURL



        return view;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        //check the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            //RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams playerView.getLayoutParams();

        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

//            outState.putLong(POSITION_KEY, mCurrentPosition);
//            outState.putBoolean(PLAY_WHEN_READY_KEY, mPlayWhenReady);
    }

    private void setDescription(View view){

        TextView mDescription = view.findViewById(R.id.tv_description);
        mDescription.setText(mStepsSent.get(mNum).getDescription());
    }

    private void videoOrThumbnail(View view){
        if (!videoUrl.isEmpty()){
            playerView = view.findViewById(R.id.video_view);
            playerView.setVisibility(View.VISIBLE);
            initializePlayer();
        } else if (!thumbnailUrl.isEmpty()){
            ImageView thumbImage = (ImageView) view.findViewById(R.id.thumbnail_image);
            thumbImage.setVisibility(View.VISIBLE);

            Picasso.get()
                    .load(thumbnailUrl)
                    .fit()
                    .placeholder(R.drawable.ic_cake_black_24dp)
                    .error(R.drawable.ic_cake_black_24dp)
                    .into(thumbImage);
        }
    }


    //region ExoPlayer
    //Reference: https://codelabs.developers.google.com/codelabs/exoplayer-intro/#2
    private void initializePlayer(){
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(), new DefaultLoadControl());

        playerView.setPlayer(player);

        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);

        //Media Source
        Uri uri = Uri.parse(videoUrl);
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);

    }

    private MediaSource buildMediaSource(Uri uri){
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).createMediaSource(uri);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23){

        }
    }


    @Override
    public void onResume() {
        super.onResume();
        //hideSystemUi();
        if ((Util.SDK_INT <= 23 || player == null)){

        }
    }

//    //Implementation detail to have a pure full screen experience
//    @SuppressLint("InlinedApi")
//    private void hideSystemUi() {
//        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
//                | View.SYSTEM_UI_FLAG_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    //endregion
}
