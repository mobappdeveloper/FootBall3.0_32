package com.footballio.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.footballio.R;
import com.footballio.Utils.Utils;
import com.footballio.databinding.ActivityLoginEmailBinding;
import com.footballio.model.login.User;
import com.footballio.viewmodel.LoginViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class ELoginActivity extends AppCompatActivity implements IProgressBar {
    private ActivityLoginEmailBinding loginEmailBinding;
    private LoginViewModel loginViewModel;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginEmailBinding = DataBindingUtil.setContentView(this, R.layout.activity_login_email);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginEmailBinding.setEmaillogin(loginViewModel);
        loginEmailBinding.setLifecycleOwner(this);
        loginViewModel.iProgressBar = this;
        doLogin();

        loginEmailBinding.txtEloginFp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ELoginActivity.this, VerifyEmailActivity.class));
            }
        });

        loginEmailBinding.txtEloginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ELoginActivity.this, RegisterActivity.class));
            }
        });

    }

    private void doLogin() {

        loginViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Utils.showToast(s, ELoginActivity.this);
                startActivity(new Intent(ELoginActivity.this, GenderActivity.class));// REMOVE LATTER
            }
        });

        loginViewModel.getSuccessMessage().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                Utils.showToast(user.getResponse(), ELoginActivity.this);
                startActivity(new Intent(ELoginActivity.this, GenderActivity.class));
            }
        });
    }

    @Override
    public void showProgressBar() {
        loginEmailBinding.progressBarElogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        loginEmailBinding.progressBarElogin.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


}
