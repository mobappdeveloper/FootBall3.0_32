package com.footballio.Utils;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;

import com.footballio.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomProgress extends Dialog {

    Context activity;


    CustomProgress(Context activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.loader);
        ButterKnife.bind(this);
        /*ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 0, 500); // see this max value coming back here, we animate towards that value
        animation.setDuration(1000); // in milliseconds
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setRepeatCount(ObjectAnimator.INFINITE);
        animation.setRepeatMode(ObjectAnimator.RESTART);
        animation.start();*/
    }
}