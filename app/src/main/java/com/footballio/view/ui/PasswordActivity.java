package com.footballio.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.footballio.R;
import com.footballio.Utils.Utils;
import com.footballio.viewmodel.ProfileViewModel;

@AndroidEntryPoint
public class PasswordActivity extends AppCompatActivity {
    private AppCompatEditText compatEditText_Pwd, compatEditText_confirmPwd;
    private Button Speichern;
    private ProfileViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        init();
        Speichern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(compatEditText_Pwd.getText().toString())) {
                    Utils.showToast("Enter password", PasswordActivity.this);
                } else if (TextUtils.isEmpty(compatEditText_confirmPwd.getText().toString())) {
                    Utils.showToast("Enter password", PasswordActivity.this);
                } else if (compatEditText_Pwd.getText().toString().equals(compatEditText_confirmPwd.getText().toString())) {
                    updatePassword(compatEditText_Pwd.getText().toString());
                }

            }
        });
    }

    private void updatePassword(String pwd) {
        viewModel.updateUserPassword(pwd).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Utils.showToast(s, PasswordActivity.this);
                finish();
            }
        });
    }

    private void init() {
        compatEditText_Pwd = findViewById(R.id.etxt_pwd);
        //compatEditText_Pwd.setBackground(null);
        compatEditText_confirmPwd = findViewById(R.id.etxt_confirm_pwd);
        compatEditText_confirmPwd.setBackground(null);
        Speichern = findViewById(R.id.butt_Speichern);
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
    }
}