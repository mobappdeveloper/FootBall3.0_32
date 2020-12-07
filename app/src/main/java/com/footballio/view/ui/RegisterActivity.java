package com.footballio.view.ui;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.footballio.R;
import com.footballio.Utils.Utils;
import com.footballio.databinding.ActivityRegistrationBinding;
import com.footballio.model.login.User;
import com.footballio.view.callback.IProgressBar;
import com.footballio.viewmodel.LoginViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegisterActivity extends AppCompatActivity implements IProgressBar {
    private ActivityRegistrationBinding registrationBinding;
    private LoginViewModel register;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

        registrationBinding.etxtRegisterPwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // Do you job here which you want to done through event
                    register.onRegisterButtonClick();
                }
                return false;
            }
        });
    }

    private void init() {
        createViews();
        createRegistration();
    }

    private void createViews() {registrationBinding = DataBindingUtil.setContentView(this, R.layout.activity_registration);
        register = new ViewModelProvider(this).get(LoginViewModel.class);
        registrationBinding.setRegister(register);
        registrationBinding.setLifecycleOwner(this);
        register.iProgressBar = this;
        register.initRegisterLiveData();

    }

    private void createRegistration() {
        register.getErrorResponse().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Utils.showToast(s, RegisterActivity.this);
                hideProgressBar();
            }
        });
        register.getSuccessResponse().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                Utils.showToast(user.getResponse(), RegisterActivity.this);
                hideProgressBar();
                startActivity(new Intent(RegisterActivity.this, VerifyEmailCodeActivity.class)
                        .putExtra("FirstTimeRegistration", "0")
                        .putExtra("Logintype", "0")
                        .putExtra("email", user.getEmailAddress()));
            }
        });


    }


    @Override
    public void showProgressBar() {
        registrationBinding.progressBarRegistration.setVisibility(View.VISIBLE);
        registrationBinding.progressBarRegistration.getIndeterminateDrawable()
                .setColorFilter(ContextCompat.getColor(this, R.color.colorWhite), PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void hideProgressBar() {
        registrationBinding.progressBarRegistration.setVisibility(View.GONE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        register.getErrorResponse().removeObservers(this);
        register.getSuccessResponse().removeObservers(this);
    }


}
