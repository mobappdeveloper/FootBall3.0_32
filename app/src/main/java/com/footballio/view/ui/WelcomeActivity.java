package com.footballio.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.footballio.R;
import com.footballio.Utils.AppConst;
import com.footballio.Utils.Utils;
import com.footballio.databinding.ActivityThankyouBinding;
import com.footballio.viewmodel.LoginViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WelcomeActivity extends AppCompatActivity {
    private ActivityThankyouBinding thankyouBinding;
    private LoginViewModel welcome;
    private Bundle bundle_fromLoginOption;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpBinding();
        thankyouBinding.buttThankyouLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bundle_fromLoginOption != null) {
                    Bundle bundle = bundle_fromLoginOption.getBundle(AppConst.LOGIN_BUNDLE);
                    int navigateScreen = bundle.getInt(AppConst.LOGIN_TYPE, -1);
                    if (navigateScreen == 0) {
                        Intent intent = new Intent(WelcomeActivity.this, ELoginActivity.class);
                        intent.putExtra(AppConst.UP, AppConst.UP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else if (navigateScreen == 1 || navigateScreen == 3) {
                        createSession(bundle);
                        Intent intent = new Intent(WelcomeActivity.this, GenderActivity.class);
                        intent.putExtra(AppConst.UP, AppConst.UP);
                        intent.putExtra(AppConst.LOGIN_BUNDLE, bundle);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Utils.showToast("Error", WelcomeActivity.this);
                    }

                }

            }
        });
    }

    private void createSession(Bundle login) {
        welcome.createUserSession(login);
    }

    private void setUpBinding() {
        thankyouBinding = DataBindingUtil.setContentView(this, R.layout.activity_thankyou);
        welcome = new ViewModelProvider(this).get(LoginViewModel.class);
        thankyouBinding.setWelcom(welcome);
        thankyouBinding.setLifecycleOwner(this);
        bundle_fromLoginOption = getIntent().getExtras();
    }
}
