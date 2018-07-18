package com.example.android.bakingapp.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


@SuppressWarnings({"SpellCheckingInspection", "WeakerAccess"}) //Because of butterknife they cant be private
public class StepDetailsFragment extends Fragment {

    private ArrayList<Step> mStepsSent;
    private int mNum;

    //Exoplayer
    //private PlayerView playerView;
    private SimpleExoPlayer player;
    private boolean playWhenReady = false;
    private Long playbackPosition;
    private String videoUrl;
    private String thumbnailUrl;
    private final String CURRENT_POSITION = "current_position";

    @BindView(R.id.tv_description)
    TextView description;

    @BindView(R.id.video_view)
    PlayerView playerView;

    @BindView(R.id.thumbnail_image)
    ImageView thumbImage;


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
        if (getArguments() != null) {
            mNum = getArguments().getInt("position", 0);
        }

        if(savedInstanceState != null){
            playbackPosition = savedInstanceState.getLong(CURRENT_POSITION);
            playWhenReady = true;
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (getArguments() != null) {
            Bundle fragmentBundle = getArguments().getBundle("fragmentBundle");
            if (fragmentBundle!=null)
                mStepsSent = fragmentBundle.getParcelableArrayList("steps");
            if (mStepsSent !=null) {
                videoUrl = mStepsSent.get(mNum).getVideoURL();
                thumbnailUrl = mStepsSent.get(mNum).getThumbNailURL();
            }
        }



        if(savedInstanceState != null){
            playbackPosition = savedInstanceState.getLong(CURRENT_POSITION);
            playWhenReady = true;
        }

        View view = inflater.inflate(R.layout.fragment_step_details, container, false);
        ButterKnife.bind(this, view);
        setDescription();
        videoOrThumbnail();

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

            if (playbackPosition != null)
                outState.putLong(CURRENT_POSITION, playbackPosition);
    }

    private void setDescription(){

        description.setText(mStepsSent.get(mNum).getDescription());
    }

    private void videoOrThumbnail(){
        if (!videoUrl.isEmpty()){
            playerView.setVisibility(View.VISIBLE);
            initializePlayer();
        } else if (!thumbnailUrl.isEmpty()){
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

        //Media Source
        Uri uri = Uri.parse(videoUrl);
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);

        player.setPlayWhenReady(playWhenReady);
        if (playbackPosition!=null)
            player.seekTo(playbackPosition);

    }

    private MediaSource buildMediaSource(Uri uri){
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).createMediaSource(uri);
    }


    @Override
    public void onPause() {
        super.onPause();

        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
            releasePlayer();
    }

    private void releasePlayer() {
        if (player != null) {
            if (player.getCurrentPosition() > 0) {playbackPosition = player.getCurrentPosition();}
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    //endregion
}
