package com.footballio.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.Utils.Loader;
import com.footballio.Utils.Prefs;
import com.footballio.model.BasicResponse;
import com.footballio.model.chanllenge.ChanllengeLikeResponse;
import com.footballio.model.chanllenge.ChanllengeUnlikeResponse;
import com.footballio.model.chanllenge.ChanllengeVideoListResponse;
import com.footballio.retrofit.ApiClient;
import com.footballio.retrofit.ApiInterface;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
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
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.gson.Gson;
import com.pnikosis.materialishprogress.ProgressWheel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FBChallengeVideoActivity extends AppCompatActivity {
    private static final String TAG = FBChallengeVideoActivity.class.getSimpleName();
    @BindView(R.id.fb_challenge_video_back)
    ImageView fbChallengeVideoBack;
    @BindView(R.id.fb_workout_view_title)
    TextView fbWorkoutViewTitle;

    @BindView(R.id.vendor_ads_image)
    ImageView vendorAdsImage;
    @BindView(R.id.fb_challenege_video_username)
    TextView fbChallenegeVideoUsername;
    @BindView(R.id.category_view_checkbox)
    CheckBox categoryViewCheckbox;
    @BindView(R.id.fb_blog_image)
    SimpleExoPlayerView fbBlogImage;
    SimpleExoPlayer exoPlayer;
    @BindView(R.id.txt_like_count)
    TextView txtLikeCount;
    @BindView(R.id.img_delete)
    ImageView imgDelete;
    @BindView(R.id.progressBargreen)
    ProgressWheel progressBargreen;
    @BindView(R.id.progressBarBlue)
    ProgressWheel progressBarBlue;
    @BindView(R.id.progress_bar_layout)
    LinearLayout progressBarLayout;
    private Context mycontext;
    private String cid = "";
    private String pid = "";
    private ChanllengeVideoListResponse chanllengeVedioResponse;
    private ChanllengeVideoListResponse chanllengeLikeResponse;
    private ChanllengeLikeResponse chanllengeLikeeResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_video_view);
        ButterKnife.bind(this);

        initUi();
    }

    private void getBundle() {
        Intent intent = getIntent();
        if (intent.getStringExtra(APPConst.CHANLLENGEID) != null) {
            cid = intent.getStringExtra(APPConst.CHANLLENGEID);

        }
        if (intent.getStringExtra(APPConst.PID) != null) {
            pid = intent.getStringExtra(APPConst.PID);
            getChanllengeVideo(pid, cid);
        }
    }

    private void initUi() {
        mycontext = this;

        getBundle();

        categoryViewCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    getChanllengeLikeo(pid, cid);
                } else {
                    getChanllengeUnLikeo(pid, cid);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        if (exoPlayer != null) {
            //   exoPlayer.setPlayWhenReady(true);
        }
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        if (exoPlayer != null) {
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    @OnClick({R.id.fb_challenge_video_back, R.id.fb_challenege_video_username, R.id.category_view_checkbox, R.id.img_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fb_challenge_video_back:
                if (exoPlayer != null) {
                    exoPlayer.stop();
                    exoPlayer = null;
                    Log.d(TAG, "onBackPressed: player");
                }
                Intent intent = new Intent(mycontext, FBChallengeFullViewActivity.class);
                intent.putExtra(APPConst.CHANLLENGEID, cid);
                startActivity(intent);
                finish();
                break;
            case R.id.fb_challenege_video_username:
                if (exoPlayer != null) {
                    exoPlayer.stop();
                    exoPlayer = null;
                    Log.d(TAG, "onBackPressed: player");
                }
                Intent i = new Intent(mycontext, FBUploadChallengeActivity.class);
                i.putExtra(APPConst.CHANLLENGEID, cid);
                startActivity(i);
                break;
            case R.id.category_view_checkbox:
                break;
            case R.id.img_delete:
                getChanllengedelete(pid, cid);
                break;
        }
    }


    Call<ChanllengeVideoListResponse> chanllengeViewResponseCall;

    private void getChanllengeVideo(String pid, String cid) {

        Loader.showLoad(mycontext, true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        chanllengeViewResponseCall = apiService.getChanllengeVideoView(pid, Prefs.getString(APPConst.TOKEN, ""), cid);


        chanllengeViewResponseCall.enqueue(new Callback<ChanllengeVideoListResponse>() {
            @Override
            public void onResponse(Call<ChanllengeVideoListResponse> call, Response<ChanllengeVideoListResponse> response) {
                Log.d(TAG, "onResponse: allCategoryResponse" + new Gson().toJson(response.body()));
                chanllengeVedioResponse = response.body();
                fbChallenegeVideoUsername.setText(chanllengeVedioResponse.getUserName());
                txtLikeCount.setText(chanllengeVedioResponse.getLikeCount());
                Log.d(TAG, "onResponse:imgDelete prefs" + Prefs.getString(APPConst.TOKEN, ""));
                Log.d(TAG, "onResponse:imgDelete data" + chanllengeVedioResponse.getUserId());
                if (Prefs.getString(APPConst.TOKEN, "").equals(String.valueOf(chanllengeVedioResponse.getUserId()))) {
                    imgDelete.setVisibility(View.VISIBLE);
                    Log.d(TAG, "onResponse: imgDelete visible");
                } else {
                    imgDelete.setVisibility(View.GONE);
                    Log.d(TAG, "onResponse: imgDelete gone");
                }

                Glide.with(mycontext)
                        .load(chanllengeVedioResponse.getUserphoto())
                        .error(R.color.colorTransparent)
                        .placeholder(R.color.colorTransparent)
                        .into(vendorAdsImage);
                if (chanllengeVedioResponse.getUserLikeStatus().equals("N")) {
                    categoryViewCheckbox.setChecked(false);
                } else {
                    categoryViewCheckbox.setChecked(false);
                }


                try {
                    BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                    TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
                    exoPlayer = ExoPlayerFactory.newSimpleInstance(mycontext, trackSelector);
                    Uri videoURI = Uri.parse(chanllengeVedioResponse.getVideoUrl());
                    DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
                    ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                    MediaSource mediaSource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);
                    fbBlogImage.setPlayer(exoPlayer);
                    //  exoPlayer.res(AspectRatioFrameLayout.RESIZE_MODE_FIT);
                    exoPlayer.prepare(mediaSource);
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
                    //   exoPlayer.pla
               /*     exoPlayer.seekTo(1);
                    exoPlayer.release();*/
                } catch (Exception e) {
                    Log.e("MainAcvtivity", " exoplayer error " + e.toString());
                }

                Loader.showLoad(mycontext, false);
            }

            @Override
            public void onFailure(Call<ChanllengeVideoListResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: loginUser" + t.getMessage().toString());
                Loader.showLoad(mycontext, false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onDestroy() {
        if (exoPlayer != null) {
            exoPlayer.release();
            exoPlayer = null;
            Log.d(TAG, "onDestroy: player");
        }
        super.onDestroy();

    }

    Call<ChanllengeLikeResponse> chanllengeLikeResponseCall;

    private void getChanllengeLikeo(String pid, String cid) {

        Loader.showLoad(mycontext, true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        chanllengeLikeResponseCall = apiService.getChanllengelike(pid, Prefs.getString(APPConst.TOKEN, ""), cid);


        chanllengeLikeResponseCall.enqueue(new Callback<ChanllengeLikeResponse>() {
            @Override
            public void onResponse(Call<ChanllengeLikeResponse> call, Response<ChanllengeLikeResponse> response) {
                Log.d(TAG, "onResponse: allCategoryResponse" + new Gson().toJson(response.body()));
                chanllengeLikeeResponse = response.body();
                if (chanllengeLikeeResponse.isStatus()) {
                    txtLikeCount.setText("" + (Integer.parseInt(chanllengeVedioResponse.getLikeCount()) + 1));
                    Toast.makeText(mycontext, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mycontext, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

                Loader.showLoad(mycontext, false);
            }

            @Override
            public void onFailure(Call<ChanllengeLikeResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: loginUser" + t.getMessage().toString());
                Loader.showLoad(mycontext, false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });


    }

    Call<ChanllengeUnlikeResponse> chanllengeUnlikeResponseCall;

    private void getChanllengeUnLikeo(String pid, String cid) {

        Loader.showLoad(mycontext, true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        chanllengeUnlikeResponseCall = apiService.getChanllengeunlike(pid, Prefs.getString(APPConst.TOKEN, ""), cid);


        chanllengeUnlikeResponseCall.enqueue(new Callback<ChanllengeUnlikeResponse>() {
            @Override
            public void onResponse(Call<ChanllengeUnlikeResponse> call, Response<ChanllengeUnlikeResponse> response) {
                Log.d(TAG, "onResponse: allCategoryResponse" + new Gson().toJson(response.body()));

                if (response.body().isStatus()) {
                    txtLikeCount.setText("" + (Integer.parseInt(chanllengeVedioResponse.getLikeCount()) - 1));
                    Toast.makeText(mycontext, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mycontext, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

                Loader.showLoad(mycontext, false);
            }

            @Override
            public void onFailure(Call<ChanllengeUnlikeResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: loginUser" + t.getMessage().toString());
                Loader.showLoad(mycontext, false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });


    }


    Call<BasicResponse> basicResponseCall;

    private void getChanllengedelete(String pid, String cid) {

        Loader.showLoad(mycontext, true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        basicResponseCall = apiService.getChanllengedelete(pid, Prefs.getString(APPConst.TOKEN, ""), cid);


        basicResponseCall.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                Log.d(TAG, "onResponse: allCategoryResponse" + new Gson().toJson(response.body()));

                if (response.body().isStatus()) {
                    if (exoPlayer != null) {
                        exoPlayer.stop();
                        exoPlayer = null;
                        Log.d(TAG, "onBackPressed: player");
                    }
                    Intent intent = new Intent(mycontext, FBChallengeFullViewActivity.class);
                    intent.putExtra(APPConst.CHANLLENGEID, cid);
                    startActivity(intent);
                    finish();
                    //txtLikeCount.setText(""+(Integer.parseInt(chanllengeVedioResponse.getLikeCount())-1));
                    Toast.makeText(mycontext, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mycontext, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

                Loader.showLoad(mycontext, false);
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: loginUser" + t.getMessage().toString());
                Loader.showLoad(mycontext, false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (exoPlayer != null) {
            exoPlayer.release();
            exoPlayer = null;
            Log.d(TAG, "onBackPressed: player");
        }
        super.onBackPressed();
    }
    @Override
    protected void onStop() {
        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer = null;
            Log.d(TAG, "onStop: player");
        }
        super.onStop();
    }
}
