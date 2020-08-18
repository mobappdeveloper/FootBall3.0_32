package com.footballio.Activities;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.footballio.Adapter.ChallengeViewAdapter;
import com.footballio.Adapter.ChanllengeViewListAdapter;
import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.Utils.Loader;
import com.footballio.Utils.Prefs;
import com.footballio.model.chanllenge.chanllengeview.ChanllengeViewResponse;
import com.footballio.retrofit.ApiClient;
import com.footballio.retrofit.ApiInterface;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerNotificationManager;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.gson.Gson;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FBChallengeFullViewActivity extends AppCompatActivity implements Player.EventListener {
    private static final String TAG = FBChallengeFullViewActivity.class.getSimpleName();
    @BindView(R.id.fb_challenge_back)
    ImageView fbChallengeBack;
    @BindView(R.id.fb_workout_view_title)
    TextView fbWorkoutViewTitle;

    @BindView(R.id.btn_fb_challenge)
    Button btnFbChallenge;
    @BindView(R.id.fb_challenge_rank)
    ImageView fbChallengeRank;
    @BindView(R.id.fb_challenge_rank_place)
    TextView fbChallengeRankPlace;
    @BindView(R.id.vendor_ads_image)
    ImageView vendorAdsImage;
    @BindView(R.id.cv_one_login)
    LinearLayout cvOneLogin;
    @BindView(R.id.fb_challenge_list)
    ImageView fbChallengeList;
    @BindView(R.id.fb_challenge_list_name)
    TextView fbChallengeListName;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_title_desc)
    TextView txtTitleDesc;
    @BindView(R.id.txt_price)
    TextView txtPrice;
    @BindView(R.id.fb_challenge_member_name)
    TextView fbChallengeMemberName;
    @BindView(R.id.gridView1)
    RecyclerView gridView1;
    @BindView(R.id.fb_blog_image)
    SimpleExoPlayerView fbBlogImage;
    SimpleExoPlayer exoPlayer;
    @BindView(R.id.progressBargreen)
    ProgressWheel progressBargreen;
    @BindView(R.id.progressBarBlue)
    ProgressWheel progressBarBlue;
    @BindView(R.id.progress_bar_layout)
    LinearLayout progressBarLayout;
    @BindView(R.id.txt_share)
    ImageView txtShare;
    @BindView(R.id.layout_rank)
    LinearLayout layoutRank;
    @BindView(R.id.ranklayouttitle)
    LinearLayout ranklayouttitle;
    @BindView(R.id.layoutranklist)
    LinearLayout layoutranklist;

    private Context mycontext;
    private Activity activity;
    private ChallengeViewAdapter challengeViewAdapter;
    private TextView empty;
    ArrayList<ClipData.Item> gridArray = new ArrayList<ClipData.Item>();
    private List<ChanllengeViewResponse> chanllengeViewResponse;
    private String cid = "";
    private ChanllengeViewListAdapter chanllengeViewListAdapter;
    private String fullid = "";
    private PlayerNotificationManager playerNotificationManager;
    MediaSource mediaSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_fullview);
        ButterKnife.bind(this);

        initUi();
    }

    private void initUi() {
        mycontext = this;
        activity = this;


        //  challengeViewAdapter = new ChallengeViewAdapter(mycontext, R.layout.li_item_challenge_view, gridArray);
        //  gridView1.setAdapter(challengeViewAdapter);
        getBundle();
    }

    private void getBundle() {
        Intent intent = getIntent();
        if (intent.getStringExtra(APPConst.CHANLLENGEID) != null) {
            cid = intent.getStringExtra(APPConst.CHANLLENGEID);
            getChanllengeView(cid);
        }
        if (intent.getStringExtra(APPConst.FULLID) != null) {
            fullid = intent.getStringExtra(APPConst.FULLID);
           /*if (fullid.equals("ACTIVE")){
               btnFbChallenge.setEnabled(true);
               btnFbChallenge.setText("Challenge akzeptieren");
           }else{
               btnFbChallenge.setEnabled(false);
               btnFbChallenge.setText("Challenge akzeptiert");
               btnFbChallenge.setBackgroundColor(getResources().getColor(R.color.bg_comm_btn));
               btnFbChallenge.setTextColor(Color.WHITE);
           }*/
        }
    }

    @OnClick({R.id.fb_challenge_back, R.id.btn_fb_challenge, R.id.txt_share})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.fb_challenge_back:
                if (exoPlayer != null) {
                    exoPlayer.stop();
                    exoPlayer = null;
                    Log.d(TAG, "onBackPressed: player");
                }
                finish();
                break;
            case R.id.txt_share:
                if (chanllengeViewResponse != null) {
                    String shareBody = chanllengeViewResponse.get(0).getTitle() + chanllengeViewResponse.get(0).getDescription();
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, chanllengeViewResponse.get(0).getTitle());
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(sharingIntent, "Football 3.0 share vis"));
                }
                break;
            case R.id.btn_fb_challenge:
                if (exoPlayer != null) {
                    exoPlayer.stop();
                    exoPlayer = null;
                    Log.d(TAG, "onBackPressed: player");
                }
                Intent i = new Intent(mycontext, FBUploadChallengeActivity.class);
                i.putExtra(APPConst.CHANLLENGEID, cid);
                startActivity(i);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getChanllengeView(cid);
    }

    Call<List<ChanllengeViewResponse>> chanllengeViewResponseCall;

    private void getChanllengeView(String pid) {

        Loader.showLoad(mycontext, true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        chanllengeViewResponseCall = apiService.getChanllengeView(pid, Prefs.getString(APPConst.TOKEN, ""));


        chanllengeViewResponseCall.enqueue(new Callback<List<ChanllengeViewResponse>>() {
            @Override
            public void onResponse(Call<List<ChanllengeViewResponse>> call, Response<List<ChanllengeViewResponse>> response) {
                Log.d(TAG, "onResponse: allCategoryResponse" + new Gson().toJson(response.body()));
                chanllengeViewResponse = response.body();
                txtTitle.setText(chanllengeViewResponse.get(0).getTitle());
                txtTitleDesc.setText(chanllengeViewResponse.get(0).getDescription());
                txtPrice.setText(chanllengeViewResponse.get(0).getPrize());
                if (chanllengeViewResponse.get(0).getRank().getUserName().length() > 0) {
                    txtTitle.setVisibility(View.VISIBLE);
                    vendorAdsImage.setVisibility(View.VISIBLE);
                    layoutRank.setVisibility(View.VISIBLE);
                    ranklayouttitle.setVisibility(View.VISIBLE);
                    fbChallengeMemberName.setText(chanllengeViewResponse.get(0).getRank().getUserName());
                    Glide.with(mycontext)
                            .load(chanllengeViewResponse.get(0).getRank().getUserphoto())
                            .error(R.color.colorTransparent)
                            .placeholder(R.color.colorTransparent)
                            .into(vendorAdsImage);
                    txtTitle.setText(chanllengeViewResponse.get(0).getTitle());
                } else {
                    txtTitle.setVisibility(View.GONE);
                    vendorAdsImage.setVisibility(View.GONE);
                    layoutRank.setVisibility(View.GONE);
                    ranklayouttitle.setVisibility(View.GONE);
                }
                chanllengeViewListAdapter = new ChanllengeViewListAdapter(mycontext, chanllengeViewResponse.get(0).getLearnboard(), cid);

                gridView1.setLayoutManager(new GridLayoutManager(mycontext, 2));

                gridView1.setAdapter(chanllengeViewListAdapter);
                if (chanllengeViewResponse.get(0).getLearnboard().size()>0){
                    layoutranklist.setVisibility(View.VISIBLE);
                }else {
                    layoutranklist.setVisibility(View.GONE);
                }

                vendorAdsImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (exoPlayer != null) {
                            exoPlayer.release();
                            exoPlayer = null;
                            Log.d(TAG, "onBackPressed: player");
                        }
                        Intent i = new Intent(mycontext, FBChallengeVideoActivity.class);
                        i.putExtra(APPConst.CHANLLENGEID, cid);
                        i.putExtra(APPConst.PID, chanllengeViewResponse.get(0).getRank().getUserPostid());
                        startActivity(i);
                    }
                });
                if (chanllengeViewResponse.get(0).getChallangeStatus().equals("1")) {
                    btnFbChallenge.setEnabled(true);
                    btnFbChallenge.setText("Challenge akzeptieren");
                } else {
                    btnFbChallenge.setEnabled(false);
                    btnFbChallenge.setText("Challenge akzeptiert");
                    btnFbChallenge.setBackgroundColor(getResources().getColor(R.color.bg_comm_btn));
                    btnFbChallenge.setTextColor(Color.WHITE);
                }

                try {
                   /*  exoPlayer = ExoPlayerFactory.newSimpleInstance(mycontext, new DefaultTrackSelector(new DefaultBandwidthMeter.Builder().build()));

                    SimpleCache downloadCache = new SimpleCache(new File(getCacheDir(), "exoCache"), new NoOpCacheEvictor());

                    String uri = chanllengeViewResponse.get(0).getVideo();

                    DataSource.Factory dataSourceFactory = new CacheDataSourceFactory(downloadCache, new DefaultDataSourceFactory(mycontext, "seyed"));

                    MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(uri));

                    exoPlayer.addListener(new Player.EventListener() {
                        @Override
                        public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {
                            Log.d("exo", "timeLine Changed");
                        }

                        @Override
                        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

                        }

                        @Override
                        public void onLoadingChanged(boolean isLoading) {
                            Log.d("exo", "loding changed= " + isLoading);
                        }

                        @Override
                        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                            if (playbackState == Player.STATE_BUFFERING) {
                                progressBarLayout.setVisibility(View.VISIBLE);

                            } else {
                                progressBarLayout.setVisibility(View.GONE);

                            }
                        }

                        @Override
                        public void onRepeatModeChanged(int repeatMode) {

                        }

                        @Override
                        public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

                        }

                        @Override
                        public void onPlayerError(ExoPlaybackException error) {
                            Log.e("exo", "exoplayer error", error);
                        }

                        @Override
                        public void onPositionDiscontinuity(int reason) {

                        }

                        @Override
                        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

                        }

                        @Override
                        public void onSeekProcessed() {
                            Log.d("exo", "seek processed");
                        }
                    });
                    exoPlayer.prepare(mediaSource);

                    fbBlogImage.setPlayer(exoPlayer);

                    exoPlayer.setPlayWhenReady(true);*/

                    BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                    TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
                    exoPlayer = ExoPlayerFactory.newSimpleInstance(mycontext, trackSelector);
                    Uri videoURI = Uri.parse(chanllengeViewResponse.get(0).getVideo());
                    DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
                    ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                    AudioAttributes audioAttributes = new AudioAttributes.Builder()
                            .setUsage(C.USAGE_MEDIA)
                            .setContentType(C.CONTENT_TYPE_MOVIE)
                            .build();
                             exoPlayer.setAudioAttributes(audioAttributes, true);
                     mediaSource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);
                  //  exoPlayer.setAudioAttributes(audioAttributes, /* handleAudioFocus= */ true);
                    fbBlogImage.setPlayer(exoPlayer);
                    exoPlayer.prepare(mediaSource);

                    //   playerNotificationManager.setPlayer(exoPlayer);
                    exoPlayer.setPlayWhenReady(true);
                   /* exoPlayer.addListener(new Player.EventListener() {
                        @Override
                        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                            if (playbackState == Player.STATE_BUFFERING) {
                                progressBarLayout.setVisibility(View.VISIBLE);

                            } else {
                                progressBarLayout.setVisibility(View.GONE);

                            }
                        }
                    });*/


                } catch (Exception e) {
                    Log.e("MainAcvtivity", " exoplayer error " + e.toString());
                }

                Loader.showLoad(mycontext, false);
            }

            @Override
            public void onFailure(Call<List<ChanllengeViewResponse>> call, Throwable t) {
                Log.d(TAG, "onFailure: loginUser" + t.getMessage().toString());
                Loader.showLoad(mycontext, false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });


    }


 /*   @Override
    public void onPause() {
        if (exoPlayer != null) {
            exoPlayer.release();
            exoPlayer = null;
            mediaSource =null;
        }
        super.onPause();

    }*/

    @Override
    protected void onDestroy() {
        if (exoPlayer != null) {

            exoPlayer.setPlayWhenReady(false);
            exoPlayer.getPlaybackState();
            exoPlayer.stop(true);
          //  exoPlayer.seekTo(0);
            exoPlayer.release();
            exoPlayer = null;
            mediaSource =null;
            Log.d(TAG, "onDestroy: fullvideo");
        }
        super.onDestroy();

    }

    @Override
    protected void onStop() {
        if (exoPlayer != null) {
            exoPlayer.stop();

            exoPlayer = null;
            mediaSource =null;
            Log.d(TAG, "onDestroy: fullvideo");
        }
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        if (exoPlayer != null) {

            exoPlayer.setPlayWhenReady(false);
            exoPlayer.getPlaybackState();
            exoPlayer.stop(true);
           // exoPlayer.seekTo(0);
            //   playerNotificationManager.setPlayer(null);
            exoPlayer.release();
            exoPlayer = null;
            mediaSource =null;
            Log.d(TAG, "onDestroy: onBackPressed full");
        }
        fbBlogImage.setPlayer(null);
        super.onBackPressed();
    }
}
