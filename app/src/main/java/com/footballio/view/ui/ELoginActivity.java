package com.footballio.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.footballio.R;
import com.footballio.Utils.AppConst;
import com.footballio.Utils.Utils;
import com.footballio.databinding.ActivityLoginEmailBinding;
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
public class ELoginActivity extends AppCompatActivity implements IProgressBar {
    private ActivityLoginEmailBinding loginEmailBinding;
    private LoginViewModel loginViewModel;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupBinding();
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

        loginEmailBinding.etxtPwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // Do you job here which you want to done through event
                    loginViewModel.onLoginButtonClick();
                }
                return false;
            }
        });


    }

    private void setupBinding() {
        loginEmailBinding = DataBindingUtil.setContentView(this, R.layout.activity_login_email);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginEmailBinding.setEmaillogin(loginViewModel);
        loginEmailBinding.setLifecycleOwner(this);
        loginViewModel.iProgressBar = this;
        loginViewModel.initEloginLivedata();
        doLogin();
    }

    private void doLogin() {
        loginViewModel.getErrorResponse().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Utils.showToast(s, ELoginActivity.this);

            }
        });
        loginViewModel.getSuccessResponse().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                Utils.showToast(user.getResponse(), ELoginActivity.this);
                Bundle bundle_welcome = getIntent().getExtras();
                createSession(user);
                if (bundle_welcome != null) {
                    String isProfileRequired = bundle_welcome.getString("UP");
                    if (isProfileRequired.isEmpty()) {
                        startActivity(new Intent(ELoginActivity.this, DashboardActivity.class).putExtra(AppConst.UID, user.getId()).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    } else {
                        startActivity(new Intent(ELoginActivity.this, GenderActivity.class).putExtra(AppConst.LOGIN_BUNDLE, getLoginBundle(user)));
                    }
                } else {
                    startActivity(new Intent(ELoginActivity.this, DashboardActivity.class).putExtra(AppConst.UID, user.getId()).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                }

            }
        });
    }

    private void createSession(User user) {
        loginViewModel.createUserSession(user);
    }

    private Bundle getLoginBundle(User user) {
        Bundle bundle = new Bundle();
        bundle.putString(AppConst.FN, user.getFirstName());
        bundle.putString(AppConst.LN, user.getLastName());
        bundle.putString(AppConst.UN, user.getUserName());
        bundle.putInt(AppConst.UID, user.getId());
        return bundle;
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
