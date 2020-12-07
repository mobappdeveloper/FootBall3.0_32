package com.footballio.view;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;

import com.footballio.R;
import com.footballio.Utils.Utils;
import com.footballio.databinding.ActivityRegistrationBinding;
import com.footballio.model.login.User;
import com.footballio.viewmodel.LoginViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class RegisterActivity extends AppCompatActivity implements IProgressBar {
    private ActivityRegistrationBinding registrationBinding;
    private LoginViewModel register;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registrationBinding = DataBindingUtil.setContentView(this, R.layout.activity_registration);
        register = new ViewModelProvider(this).get(LoginViewModel.class);
        registrationBinding.setRegister(register);
        registrationBinding.setLifecycleOwner(this);
        register.iProgressBar = this;
        createRegistration();
    }

    private void createRegistration() {

        register.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Utils.showToast(s, RegisterActivity.this);
                hideProgressBar();
            }
        });
        register.getSuccessMessage().observe(this, new Observer<User>() {
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
        register.getErrorMessage().removeObservers(this);
        register.getSuccessMessage().removeObservers(this);
    }


}
