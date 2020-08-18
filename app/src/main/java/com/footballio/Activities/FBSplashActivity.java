package com.footballio.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;


import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.Utils.Prefs;

import java.util.Timer;
import java.util.TimerTask;

public class FBSplashActivity extends AppCompatActivity {
    private Timer timer;
    private ProgressBar progressBar;
    private int i=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar=(ProgressBar)findViewById(R.id.fb_splash_progress);
        progressBar.setProgress(0);


        final long period = 20;
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //this repeats every 100 ms
                if (i<100){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                    progressBar.setProgress(i);
                    i++;
                }else{
                    //closing the timer
                    timer.cancel();
                    if (Prefs.getBoolean(APPConst.ISLOGIN,false)){
                        startActivity(new Intent(FBSplashActivity.this, FBDashboardActivity.class));
                        finish();
                    }else{
                        startActivity(new Intent(FBSplashActivity.this, FBLoginActivity.class));
                        finish();
                    }

                }
            }
        }, 0, period);
    }

}


