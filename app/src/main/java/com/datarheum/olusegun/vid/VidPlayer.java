package com.datarheum.olusegun.vid;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class VidPlayer extends AppCompatActivity {
    SimpleExoPlayerView exoPlayerView;
    SimpleExoPlayer exoPlayer;
    String videoURL = "https://static.videezy.com/system/resources/previews/000/005/357/original/23.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vid_player);
        exoPlayerView = findViewById(R.id.exo_player_view);

        try {

            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));

            exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
            Uri videoURI = Uri.parse(videoURL);

            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("Exoplayer");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource videosource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);

            exoPlayerView.setPlayer(exoPlayer);
            exoPlayer.prepare(videosource);
            exoPlayer.setPlayWhenReady(true);

        }catch (Exception e){
            Log.e("MainActivity", "Exoplayer error" + toString());
        }




    }

    }








    /*
    @Override
    protected void onStart() {
        super.onStart();

        player = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector());
        playerView.setPlayer(player);

        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "Exoplayer"));

        ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse("URL to be placed"));

        player.prepare(mediaSource);
        player.setPlayWhenReady(true);

    }

    @Override
    protected void onStop() {
        super.onStop();
        playerView.setPlayer(null);
        player.release();
        player = null;
        */