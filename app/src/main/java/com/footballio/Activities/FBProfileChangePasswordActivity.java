package com.footballio.Activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.footballio.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FBProfileChangePasswordActivity extends AppCompatActivity {
    private static final String TAG = FBProfileChangePasswordActivity.class.getSimpleName();
    @BindView(R.id.fb_profile_change_password_back)
    ImageView fbProfileChangePasswordBack;
    @BindView(R.id.fb_profile_change_password_current)
    EditText fbProfileChangePasswordCurrent;
    @BindView(R.id.fb_profile_change_password_new)
    EditText fbProfileChangePasswordNew;
    @BindView(R.id.fb_profile_change_password_confirm)
    EditText fbProfileChangePasswordConfirm;
    @BindView(R.id.btn_fb_profile_password_change)
    Button btnFbProfilePasswordChange;

    private Context mycontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_change_password);
        ButterKnife.bind(this);

        initUi();
    }

    private void initUi() {

        mycontext = this;
    }

    @OnClick({R.id.fb_profile_change_password_back, R.id.btn_fb_profile_password_change})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fb_profile_change_password_back:
                finish();
                break;
            case R.id.btn_fb_profile_password_change:
                finish();
                break;
        }
    }
}
