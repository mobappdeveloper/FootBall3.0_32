package com.footballio.Activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.footballio.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FBDiscussionUploadedActivity extends AppCompatActivity {
    private static final String TAG = FBDiscussionUploadedActivity.class.getSimpleName();
    @BindView(R.id.fb_forget_password_logo)
    ImageView fbForgetPasswordLogo;
    @BindView(R.id.fb_forget_password_tv)
    TextView fbForgetPasswordTv;
    @BindView(R.id.btn_fb_discussion_finish)
    Button btnFbDiscussionFinish;

    private Context mycontext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_uploaded);
        ButterKnife.bind(this);

        initUi();
    }

    private void initUi() {
        mycontext = this;


    }

    @OnClick(R.id.btn_fb_discussion_finish)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_fb_discussion_finish:
                finish();
                break;
        }
    }
}
