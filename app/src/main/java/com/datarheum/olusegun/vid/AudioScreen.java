package com.datarheum.olusegun.vid;

import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class AudioScreen extends AppCompatActivity {

    private ImageView playBtn;
    private boolean playPause;
    private MediaPlayer mediaPlayer;
    private ProgressDialog progressDialog;
    private boolean initialStage = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_screen);

        playBtn = (ImageView)(findViewById(R.id.audioStreamBtn));
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        progressDialog = new ProgressDialog(this);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!playPause) {
                    playBtn.setImageResource(R.drawable.exo_controls_pause);

                    if (initialStage) {
                        new Player().execute("http://us4.internet-radio.com:8266/;stream");
                    } else {
                        if (!mediaPlayer.isPlaying())
                            mediaPlayer.start();
                    }
                    playPause = true;
                } else {
                    playBtn.setImageResource(R.drawable.exo_controls_play);

                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    }
                    playPause = false;

                } }
            });


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null){
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    class Player extends AsyncTask<String, Void, Boolean>{
        @Override
        protected Boolean doInBackground(String... strings) {
            boolean prepared = false;
            try {
                mediaPlayer.setDataSource(strings[0]);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        initialStage = true;
                        playPause = false;
                        playBtn.setImageResource(R.drawable.exo_controls_play);
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });

                mediaPlayer.prepare();
                prepared=true;
            } catch (Exception e){
                Log.e("Mystreamingaudio", e.getMessage());
                prepared = false;

            }
            return prepared;


        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (progressDialog.isShowing()){
                progressDialog.cancel();
            }
            mediaPlayer.start();
            initialStage = false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Audio Playing");
            progressDialog.show();
        }
    }
}


 /* BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        final ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        TrackSelection.Factory trackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        DataSource.Factory dateSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, getPackageName()), bandwidthMeter);
        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse("www.musicper.com/tere-bin.mp3"), dateSourceFactory,
                extractorsFactory, new Handler(), Throwable::printStackTrace);    // replace Uri with your song url
        exoPlayer = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector(trackSelectionFactory));
        exoPlayer.prepare(mediaSource);  */