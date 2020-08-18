package com.footballio.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.Utils.Prefs;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FBSettingsActivity extends AppCompatActivity {
    private static final String TAG = FBSettingsActivity.class.getSimpleName();

    @BindView(R.id.fb_settings_profile_edit)
    TextView fbSettingsProfileEdit;
    @BindView(R.id.fb_settings_change_password)
    TextView fbSettingsChangePassword;
    @BindView(R.id.switch_notification)
    Switch switchNotification;
    @BindView(R.id.fb_settings_language)
    TextView fbSettingsLanguage;
    @BindView(R.id.fb_settings_feedback)
    TextView fbSettingsFeedback;
    @BindView(R.id.fb_settings_premium)
    TextView fbSettingsPremium;
    @BindView(R.id.fb_settings_back)
    LinearLayout fbSettingsBack;
    @BindView(R.id.fb_status)
    TextView fbStatus;
    @BindView(R.id.fb_logout)
    TextView fbLogout;
    @BindView(R.id.fb_tandc)
    TextView fbTandc;
    private Context mycontext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        initUi();
    }

    private void initUi() {
        mycontext = this;
    }


    @OnClick({R.id.fb_settings_back, R.id.fb_settings_profile_edit, R.id.fb_logout, R.id.fb_settings_change_password,
            R.id.switch_notification,R.id.fb_tandc, R.id.fb_settings_language, R.id.fb_settings_feedback, R.id.fb_settings_premium})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fb_settings_back:
                finish();
                break;
            case R.id.fb_settings_profile_edit:
                Intent i = new Intent(mycontext, FBProfileEditActivity.class);
                startActivity(i);
                break;
            case R.id.fb_settings_change_password:
                Intent intent = new Intent(mycontext, FBProfileChangePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.switch_notification:
                break;
                case R.id.fb_tandc:
                    Intent intent22 = new Intent(mycontext, FBPageActivity.class);
                    startActivity(intent22);
                break;
            case R.id.fb_settings_language:
                break;
            case R.id.fb_settings_feedback:
                Intent intent11 = new Intent(mycontext, FBFeedbackActivity.class);
                startActivity(intent11);
                break;
            case R.id.fb_settings_premium:
                break;
                case R.id.fb_logout:
                    Prefs.clear();
                    Prefs.putBoolean(APPConst.ISLOGIN,false);

                    Profile profile = Profile.getCurrentProfile().getCurrentProfile();
                    if (profile != null) {
                        LoginManager.getInstance().logOut();
                        // user has logged in
                        Log.d(TAG, "onClick:fb_logout facebook ");
                    } else {
                        // user has not logged in
                        Log.d(TAG, "onClick:fb_logout facebook not");
                    }
                    Intent intent1 = new Intent(mycontext,FBLoginActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent1);
                    finish();


                break;
        }
    }
}
