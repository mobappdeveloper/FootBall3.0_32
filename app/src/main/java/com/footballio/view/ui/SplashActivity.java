package com.footballio.view.ui;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.footballio.R;
import com.footballio.Utils.AppConst;
import com.footballio.viewmodel.LoginViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SplashActivity extends AppCompatActivity {
    private Button butt_getStarted;
    private boolean isLastLoginAvailable = false;
    private LoginViewModel splashViewModel;
    private CircleProgressBar mLineProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
        butt_getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this, LoginOptionActivity.class));
                finish();
            }
        });

//        mLineProgressBar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mLineProgressBar.setStyle(CircleProgressBar.SOLID_LINE);
//                mLineProgressBar.setProgressTextColor(Color.WHITE);
//                mLineProgressBar.setProgressStartColor(Color.RED);
//                mLineProgressBar.setProgressEndColor(Color.BLUE);
//                mLineProgressBar.setProgressBackgroundColor(Color.CYAN);
//                mLineProgressBar.setDrawBackgroundOutsideProgress(true);
//            }
//        });

    }

    private void init() {
        butt_getStarted = findViewById(R.id.butt_getStarted);
        splashViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        isLastLoginAvailable = splashViewModel.checkLastLoginAvailability();
        //mLineProgressBar = findViewById(R.id.line_progress);
        if (isLastLoginAvailable) {
            butt_getStarted.setVisibility(View.GONE);
            startActivity(new Intent(SplashActivity.this, DashboardActivity.class).putExtra(AppConst.UID, splashViewModel.getLastLoginUserId()));
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //simulateProgress();
    }

    private void simulateProgress() {
        ValueAnimator animator = ValueAnimator.ofInt(0, 100);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progress = (int) animation.getAnimatedValue();
                mLineProgressBar.setProgress(50);

            }
        });
        animator.setRepeatCount(ValueAnimator.REVERSE);
        animator.setDuration(4000);
        animator.start();
    }

    // todo VERSION api checking classes

}


