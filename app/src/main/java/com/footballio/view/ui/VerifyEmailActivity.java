package com.footballio.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.footballio.R;
import com.footballio.Utils.Utils;
import com.footballio.databinding.ActivityVerifyEmailBinding;
import com.footballio.model.login.User;
import com.footballio.view.callback.IProgressBar;
import com.footballio.viewmodel.LoginViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class VerifyEmailActivity extends AppCompatActivity implements IProgressBar {
    private ActivityVerifyEmailBinding verifyEmailBinding;
    private LoginViewModel verifyEmail;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        verifyEmailBinding.etxtVerifyEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // Do you job here which you want to done through event
                    verifyEmail.onEmailVerifyButtonClick();
                }
                return false;
            }
        });
    }

    private void init() {
        verifyEmailBinding = DataBindingUtil.setContentView(this, R.layout.activity_verify_email);
        verifyEmail = new ViewModelProvider(this).get(LoginViewModel.class);
        verifyEmailBinding.setVerifyEmail(verifyEmail);
        verifyEmailBinding.setLifecycleOwner(this);
        verifyEmail.iProgressBar = this;
        verifyEmail.initVerifyEmailLiveData();
        verifyEmail();
    }

    private void verifyEmail() {
        verifyEmail.getErrorResponse().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Utils.showToast(s, VerifyEmailActivity.this);
            }
        });

        verifyEmail.getSuccessResponse().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                Utils.showToast(user.getResponse(), VerifyEmailActivity.this);
                hideProgressBar();
                startActivity(new Intent(VerifyEmailActivity.this, VerifyEmailCodeActivity.class));
            }
        });

    }

    @Override
    public void showProgressBar() {
        verifyEmailBinding.progressBarVerifyEmail.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        verifyEmailBinding.progressBarVerifyEmail.setVisibility(View.INVISIBLE);
    }

   
}
