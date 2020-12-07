package com.footballio.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.footballio.R;
import com.footballio.databinding.ActivityThankyouBinding;
import com.footballio.viewmodel.LoginViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

public class WelcomeActivity extends AppCompatActivity {
    private ActivityThankyouBinding thankyouBinding;
    private LoginViewModel welcome;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thankyouBinding = DataBindingUtil.setContentView(this, R.layout.activity_thankyou);
        welcome = new ViewModelProvider(this).get(LoginViewModel.class);
        thankyouBinding.setWelcom(welcome);
        thankyouBinding.setLifecycleOwner(this);
        thankyouBinding.buttThankyouLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, ELoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}
