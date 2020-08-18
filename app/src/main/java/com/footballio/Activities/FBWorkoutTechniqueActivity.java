package com.footballio.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.footballio.Adapter.WorkoutViewAdapter;
import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.Utils.Loader;
import com.footballio.model.workout.WorkoutViewResponse;
import com.footballio.model.workout.videoview.WorkoutVideoViewResponse;
import com.footballio.retrofit.ApiClient;
import com.footballio.retrofit.ApiInterface;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FBWorkoutTechniqueActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = FBWorkoutTechniqueActivity.class.getSimpleName();
    private static boolean isTimerRunning;

    private Context mycontext;
    private TextView technique_name, technique_speed, technique_time, fb_workout_view_title;
    private ImageView technique_pause, technique_backward, img_play;
    private Button technique_next;
    private VideoView simpleVideoView;
    private ProgressBar progressbar;
    private WorkoutVideoViewResponse workoutViewResponse;
    private String pid = "";
    MediaController mediaControls;
    private String videioUrl = "", videourl_next = "";
    private int count = 0;
    private int countstart = 0;
    private static int elapsedTime = 0;
    private Timer T;
    private boolean isslow = true;
    Chronometer chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_technique);

        initUi();
    }

    private void initUi() {
        mycontext = this;

        technique_name = findViewById(R.id.fb_technique_name);
        fb_workout_view_title = findViewById(R.id.fb_workout_view_title);
        chronometer = findViewById(R.id.chronometer);
        technique_speed = findViewById(R.id.fb_technique_speed);
        technique_time = findViewById(R.id.fb_technique_time);
        technique_pause = findViewById(R.id.fb_technique_pause);
        technique_backward = findViewById(R.id.fb_technique_backward);
        technique_next = findViewById(R.id.btn_fb_techinque_next);
        simpleVideoView = findViewById(R.id.simpleVideoView);
        progressbar = findViewById(R.id.progressbar);
        img_play = findViewById(R.id.img_play);

        technique_next.setOnClickListener(this);
        technique_pause.setOnClickListener(this);
        fb_workout_view_title.setOnClickListener(this);
        technique_backward.setOnClickListener(this);
        img_play.setOnClickListener(this);
        getBundle();


        chronometer.start();

       /* new CountDownTimer(20*60000, 1000) {

            public void onTick(long millisUntilFinished) {
                technique_time.setText(new SimpleDateFormat("mm:ss:SS").format(new Date( millisUntilFinished)));
            }

            public void onFinish() {
                technique_time.setText("done!");
            }
        }.start();*/

    }

 /*   protected static void startTimer() {
        isTimerRunning = true;
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                elapsedTime += 1; //increase every sec
                mHandler.obtainMessage(1).sendToTarget();
            }
        }, 0, 1000);
    }

    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            technique_time.setText(formatIntoHHMMSS(elapsedTime)); //this is the textview
        }
    };

    private String formatIntoHHMMSS(int elapsedTime) {
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_fb_techinque_next:
                startOtherVid();
                break;
            case R.id.fb_workout_view_title:
               /* Intent intent = new Intent(mycontext, FBWorkoutVideoViewActivity.class);
                intent.putExtra(APPConst.PVIDEOID, videourl_next);
                startActivity(intent);*/
                Log.d(TAG, "onClick:fb_workout_view_title " + isslow);
                Log.d(TAG, "onClick:fb_workout_view_title count " + count);
                Log.d(TAG, "onClick:fb_workout_view_title size " + workoutViewResponse.getTracks().size());
                if (isslow) {
                    simpleVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            //works only from api 23
                            mp.start();
                            mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                                @Override
                                public void onVideoSizeChanged(MediaPlayer mp, int arg1,
                                                               int arg2) {
                                    // TODO Auto-generated method stub
                                    progressbar.setVisibility(View.GONE);
                                    mp.start();
                                    mp.setLooping(true);
                                }
                            });
                            PlaybackParams myPlayBackParams = new PlaybackParams();
                            myPlayBackParams.setSpeed(0.3f); //here set speed eg. 0.5 for slow 2 for fast mode
                            mp.setPlaybackParams(myPlayBackParams);

                            //start your video.
                        }
                    });
                    isslow = false;
                    Toast.makeText(mycontext, "Video play in slow motion", Toast.LENGTH_SHORT).show();
                    startcurrentVid();
                } else {
                    simpleVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            //works only from api 23
                            mp.start();
                            mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                                @Override
                                public void onVideoSizeChanged(MediaPlayer mp, int arg1,
                                                               int arg2) {
                                    // TODO Auto-generated method stub
                                    progressbar.setVisibility(View.GONE);
                                    mp.start();
                                    mp.setLooping(true);
                                }
                            });
                            PlaybackParams myPlayBackParams = new PlaybackParams();
                            myPlayBackParams.setSpeed(1f); //here set speed eg. 0.5 for slow 2 for fast mode
                            mp.setPlaybackParams(myPlayBackParams);

                        }
                    });
                    isslow = true;
                    Toast.makeText(mycontext, "Video play in normal mode", Toast.LENGTH_SHORT).show();
                    startcurrentVid();
                }
                break;
            case R.id.fb_technique_pause:
                openBottomSheet();
                break;
            case R.id.img_play:
                openBottomSheet();
                break;
            case R.id.fb_technique_backward:
                startpreviousVid();
                break;
        }

    }

    private void openBottomSheet() {
        View view = getLayoutInflater().inflate(R.layout.activity_pause, null);


        TextView status_btn_cancel = (TextView) view.findViewById(R.id.cancel_workout);
        TextView btnDone = (TextView) view.findViewById(R.id.continue_workout);
        Switch switch_music = view.findViewById(R.id.switch_music);
        Switch switch_sound = view.findViewById(R.id.switch_sound);

        final Dialog mBottomSheetDialog = new Dialog(mycontext,
                R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();


        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });
        status_btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                finish();

            }
        });

        switch_sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setVolume(70);
                } else {
                    setVolume(0);
                }
            }
        });
        switch_music.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setVolume(70);
                } else {
                    setVolume(0);
                }
            }
        });


    }

    private void setVolume(int amount) {
        simpleVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVolume(amount, amount);
                mp.setLooping(true);
            }
        });
    }

    private void getBundle() {
        Intent intent = getIntent();
        if (intent.getStringExtra(APPConst.PID) != null) {
            pid = intent.getStringExtra(APPConst.PID);
            workoutVideoView(pid);
        }
    }

    Call<WorkoutVideoViewResponse> workoutVideoViewResponseCall;

    private void workoutVideoView(String pid) {

        Loader.showLoad(mycontext, true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        workoutVideoViewResponseCall = apiService.workoutVideoView(pid);


        workoutVideoViewResponseCall.enqueue(new Callback<WorkoutVideoViewResponse>() {
            @Override
            public void onResponse(Call<WorkoutVideoViewResponse> call, Response<WorkoutVideoViewResponse> response) {
                Log.d(TAG, "onResponse: allCategoryResponse" + new Gson().toJson(response.body()));
                workoutViewResponse = response.body();
                count = workoutViewResponse.getTracks().size();
                countstart = 0;
                Log.d(TAG, "onResponse: count" + count);
                videioUrl = workoutViewResponse.getTracks().get(0).getGifImage();
                videourl_next = workoutViewResponse.getTracks().get(0).getVideo();
                technique_name.setText(workoutViewResponse.getTracks().get(0).getTrackerName());
            /*    if (mediaControls == null) {
                    // create an object of media controller class
                    mediaControls = new MediaController(mycontext);
                    mediaControls.setAnchorView(simpleVideoView);
                }*/
                simpleVideoView.setMediaController(null);
                // set the media controller for video view
                //simpleVideoView.setMediaController(mediaControls);
                // set the uri for the video view
                Uri uri = Uri.parse(videioUrl);
                Log.d(TAG, "initUi: videourl" + videioUrl);
                // simpleVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.videoplayback));
                simpleVideoView.setVideoURI(uri);
                // start a video
                simpleVideoView.start();

                // implement on completion listener on video view
                simpleVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        Toast.makeText(getApplicationContext(), "Thank You...!!!", Toast.LENGTH_LONG).show(); // display a toast when an video is completed
                    }
                });
                simpleVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        Toast.makeText(getApplicationContext(), "Oops An Error Occur While Playing Video...!!!", Toast.LENGTH_LONG).show(); // display a toast when an error is occured while playing an video
                        return false;
                    }
                });

                progressbar.setVisibility(View.VISIBLE);
                simpleVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        // TODO Auto-generated method stub
                        mp.start();
                        mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                            @Override
                            public void onVideoSizeChanged(MediaPlayer mp, int arg1,
                                                           int arg2) {
                                // TODO Auto-generated method stub
                                progressbar.setVisibility(View.GONE);
                                mp.start();
                                mp.setLooping(true);
                            }
                        });
                    }
                });

                simpleVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp) {

                        //    startcurrentVid();
                    }
                });
                Loader.showLoad(mycontext, false);
            }

            @Override
            public void onFailure(Call<WorkoutVideoViewResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: loginUser" + t.getMessage().toString());
                Loader.showLoad(mycontext, false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void startOtherVid() {
        long systemCurrTime = SystemClock.elapsedRealtime();
        //chronometer.setBase(systemCurrTime);
        // count--;
        countstart++;
        Log.d(TAG, "onResponse: count next" + countstart);

        if (countstart < count) {
            technique_name.setText(workoutViewResponse.getTracks().get(countstart).getTrackerName());
            technique_speed.setText(workoutViewResponse.getTracks().get(countstart).getRepitations());
            Uri uri = Uri.parse(workoutViewResponse.getTracks().get(countstart).getGifImage());
            simpleVideoView.setVideoURI(uri);
            videourl_next = workoutViewResponse.getTracks().get(countstart).getVideo();
            simpleVideoView.start();
        } else {
            Intent i = new Intent(mycontext, FBWorkoutAccomplishedActivity.class);
            i.putExtra(APPConst.PID, pid);
            startActivity(i);
        }

    }

    private void startpreviousVid() {
        long systemCurrTime = SystemClock.elapsedRealtime();
        //chronometer.setBase(systemCurrTime);
        // count++;
        countstart--;
        Log.d(TAG, "onResponse: count next" + countstart);
        if (countstart > -1) {

            if (countstart < count) {
                technique_name.setText(workoutViewResponse.getTracks().get(countstart).getTrackerName());
                technique_speed.setText(workoutViewResponse.getTracks().get(countstart).getRepitations());
                Uri uri = Uri.parse(workoutViewResponse.getTracks().get(countstart).getGifImage());
                simpleVideoView.setVideoURI(uri);
                videourl_next = workoutViewResponse.getTracks().get(countstart).getVideo();
                simpleVideoView.start();
            } else {
                Intent i = new Intent(mycontext, FBWorkoutAccomplishedActivity.class);
                i.putExtra(APPConst.PID, pid);
                startActivity(i);
            }

        } else {
            countstart++;
            finish();
            //   Toast.makeText(mycontext, "First video Playing", Toast.LENGTH_SHORT).show();
        }

    }

    private void startcurrentVid() {
        //count--;
        Log.d(TAG, "onResponse: count next" + countstart);
        //  countstart++;
        if (countstart < count) {
            technique_name.setText(workoutViewResponse.getTracks().get(countstart).getTrackerName());
            Uri uri = Uri.parse(workoutViewResponse.getTracks().get(countstart).getGifImage());
            simpleVideoView.setVideoURI(uri);
            videourl_next = workoutViewResponse.getTracks().get(countstart).getVideo();
            simpleVideoView.start();
            //  countstart++;
        } else {
            Intent i = new Intent(mycontext, FBWorkoutAccomplishedActivity.class);
            i.putExtra(APPConst.PID, pid);
            startActivity(i);
        }
    }
}
