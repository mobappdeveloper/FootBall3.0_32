package com.footballio.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.footballio.R;
import com.footballio.Utils.AppConst;
import com.footballio.Utils.Utils;
import com.footballio.model.login.dashboard.WorkoutTrack;
import com.footballio.view.callback.IProgressBar;
import com.footballio.viewmodel.DashboardViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
@AndroidEntryPoint
public class WorkoutBeginActivity extends AppCompatActivity implements IProgressBar {
    private VideoView videoView;
    private List<String> arrayList = new ArrayList<>();
    private List<WorkoutTrack> workoutTrackList = new ArrayList<>();
    int index = 0, progreesMax = 0;
    private TextView txt_duration, video_description;
    private ImageView img_settings;
    private TextView txt_play, txt_pause, txt_skip;
    private boolean isPaused = false;
    private MediaPlayer mediaPlayer;
    private MediaOption mediaOptionDialog;
    private DashboardViewModel dashboardViewModel;
    private long timer = 0;
    private ProgressBar progressBar, progressbar_workout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_begin);
        init();
        img_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialogFragment();
            }
        });

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        showTimer();
                        mediaPlayer = mp;
                        progressBar.setVisibility(View.VISIBLE);
                        txt_duration.setVisibility(View.VISIBLE);
                        //txt_duration.setText(getDuration(videoView.getDuration())+"/"+workoutTrackList.get(index).getRepitations());
                        txt_duration.setText(getDuration(videoView.getDuration()) + "/" + workoutTrackList.get(index).getRepitations());
                        progressBar.setMax(videoView.getDuration());
                        showProgress(videoView, videoView.getDuration());

                    }

                });
            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                index++;
                progreesMax = index;
                if (workoutTrackList.size() <= index) {
                    index = 0;
                    mp.release();
                    txt_duration.setText(getDuration(timer));
                    finishWorkout();
                } else {
                    videoView.setVideoURI(Uri.parse(workoutTrackList.get(index).getGifImage()));
                    video_description.setText(workoutTrackList.get(index).getTrackerName());
                    videoView.start();
                }
            }
        });

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.d("API123", "What " + what + " extra " + extra);
                Utils.showToast("Invalid Video Format", WorkoutBeginActivity.this);
                return false;
            }
        });

    }

    private void finishWorkout() {
        showProgressBar();
        Bundle bundle = getIntent().getExtras();
        dashboardViewModel.finishWorkout(bundle.getInt(AppConst.PID)).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Utils.showToast(s, WorkoutBeginActivity.this);
                hideProgressBar();
            }
        });
    }

    private void showTimer() {
        new CountDownTimer(videoView.getDuration(), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer += millisUntilFinished / 1000;
                txt_duration.setText(getDuration(timer));
            }

            @Override
            public void onFinish() {
            }
        }.start();
    }


    private void init() {
        videoView = findViewById(R.id.videoView);
        txt_duration = findViewById(R.id.video_title);
        img_settings = findViewById(R.id.img_workoutBegin_settings);
        txt_duration.setVisibility(View.INVISIBLE);
        txt_play = findViewById(R.id.play);
        txt_pause = findViewById(R.id.pause);
        //txt_stop = findViewById(R.id.stop);
        txt_skip = findViewById(R.id.skip);
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        video_description = findViewById(R.id.video_description);
        progressBar = findViewById(R.id.simpleProgressBar);
        progressBar.setVisibility(View.GONE);
        progressbar_workout = findViewById(R.id.progressbar_workout);
        progressbar_workout.setVisibility(View.GONE);
        dashboardViewModel.initWorkoutBeginLiveData();
        getVideoList();

    }

    private void getVideoList() {
        showProgressBar();
        Bundle bundle=getIntent().getExtras();
        int pid=bundle.getInt(AppConst.PID);//36
        dashboardViewModel.getWorkoutVideoList(pid, AppConst.LANG_GERMAN).observe(this, new Observer<WorkoutTrack>() {
            @Override
            public void onChanged(WorkoutTrack workoutTrack) {
                workoutTrackList.addAll(workoutTrack.getGetWorkoutTrackList());
                hideProgressBar();
            }
        });

        dashboardViewModel.getErrorResponse().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Utils.showToast(s, WorkoutBeginActivity.this);
                hideProgressBar();
            }
        });
    }

    public void play(View view) {
        mediaOptionDialog.dismiss();
        if (videoView.isPlaying()) {
            videoView.resume();
        } else if (isPaused) {
            videoView.start();
            isPaused = false;
        } else {
            if (index < workoutTrackList.size()) {
                videoView.setVideoURI(Uri.parse(workoutTrackList.get(index).getGifImage()));
                videoView.start();
            }
        }
    }

    public void pause(View view) {
        mediaOptionDialog.dismiss();
        isPaused = true;
        videoView.pause();
    }

//    public void stop(View view) {
////        trackVideoControl="stop";
////        videoView.stopPlayback();
////    }

    public void skip(View view) {
        videoView.stopPlayback();
        index++;
        play(view);
    }

    private void showProgress(VideoView videoView, int max) {
        new CountDownTimer(max, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                try {
                    progressBar.setProgress(videoView.getCurrentPosition());
                } catch (Exception e) {

                }

            }

            @Override
            public void onFinish() {
                //txt_duration.setText("DONE");
                progressBar.setMax(workoutTrackList.size() - 1);
                progressBar.setProgress(progreesMax);
            }
        }.start();
    }


    public void showBottomSheetDialogFragment() {
        mediaOptionDialog = new MediaOption();
        mediaOptionDialog.show(getSupportFragmentManager(), mediaOptionDialog.getTag());
    }

    private String getDuration(long millis) {
        long hours = TimeUnit.SECONDS.toHours(millis);
        long minutes = TimeUnit.SECONDS.toMinutes(millis);
        long seconds = TimeUnit.SECONDS.toSeconds(millis);
        if (hours > 0) {
            return hours + ":" + minutes + ":" + seconds;
        } else if (minutes > 0) {
            seconds = seconds - (minutes * 60);
            return minutes + ":" + seconds;
        } else {
            return minutes + ":" + seconds;
        }
    }

//    private String getTimeDuration() {
//        timer=2460;
//        long hours = TimeUnit.MILLISECONDS.toHours(timer);
//        long minutes = TimeUnit.MILLISECONDS.toMinutes(timer);
//        long seconds = TimeUnit.MILLISECONDS.toSeconds(timer);
//        long mseconds = timer/1000;
//
//        if (hours > 0) {
//            return hours + ":" + minutes + ":" + seconds;
//        } else if (minutes > 0) {
//            return minutes + "M:" + seconds;
//        } else {
//            return seconds + "S:" + mseconds;
//        }
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mediaPlayer != null) {
            //videoView.stopPlayback();
            mediaPlayer.release();
        }
    }

    @Override
    public void showProgressBar() {
        progressbar_workout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressbar_workout.setVisibility(View.GONE);
    }
}