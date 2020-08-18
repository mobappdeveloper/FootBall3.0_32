package com.footballio.Activities;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.Utils.Loader;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
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
import com.google.android.exoplayer2.video.VideoRendererEventListener;


public class FBWorkoutVideoViewActivity extends AppCompatActivity implements View.OnClickListener, VideoRendererEventListener {
    private static final String TAG = FBWorkoutVideoViewActivity.class.getSimpleName();

    private Context mycontext;

    VideoView simpleVideoView;
    SimpleExoPlayerView exoPlayerView;
    SimpleExoPlayer exoPlayer;    MediaController mediaControls;
    private LinearLayout view_back;
    private String videioUrl = "";
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_video);

        initUi();
    }
    private void getBundle() {
        Intent intent = getIntent();
        if (intent.getStringExtra(APPConst.PVIDEOID) != null) {
            videioUrl = intent.getStringExtra(APPConst.PVIDEOID);


            try {
                BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
                exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
                Uri videoURI = Uri.parse(videioUrl);
  ;
                DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(mycontext,
                        Util.getUserAgent(mycontext, "yourApplicationName"));
// This is the MediaSource representing the media to be played.
                MediaSource videoSource =
                        new ProgressiveMediaSource.Factory(dataSourceFactory)
                                .createMediaSource(videoURI);
// Prepare the player with the source.
                exoPlayer.prepare(videoSource);
               // exoPlayer.prepare(mediaSource);
                exoPlayerView.setPlayer(exoPlayer);

                exoPlayer.setPlayWhenReady(true);
            }catch (Exception e){
                Log.e("MainAcvtivity"," exoplayer error "+ e.toString());
            }



        }
    }
    private void initUi() {
        mycontext=this;


        view_back = findViewById(R.id.fb_workout_video_view_back);
        progressBar = findViewById(R.id.progressbar);
        //simpleVideoView = (VideoView) findViewById(R.id.simpleVideoView);
        exoPlayerView = (SimpleExoPlayerView) findViewById(R.id.simpleVideoView);
        getBundle();
        view_back.setOnClickListener(this);

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (exoPlayer!=null) {
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.fb_workout_video_view_back:
                finish();
            finish();
                break;
        }

    }

    @Override
    public void onVideoEnabled(DecoderCounters counters) {

    }

    @Override
    public void onVideoDecoderInitialized(String decoderName, long initializedTimestampMs, long initializationDurationMs) {

    }

    @Override
    public void onVideoInputFormatChanged(Format format) {

    }

    @Override
    public void onDroppedFrames(int count, long elapsedMs) {

    }

    @Override
    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
        Log.d(TAG, "onVideoSizeChanged [" + " width: " + width + " height: " + height + "]");
    }

    @Override
    public void onRenderedFirstFrame(@Nullable Surface surface) {

    }

    @Override
    public void onVideoDisabled(DecoderCounters counters) {

    }
}
