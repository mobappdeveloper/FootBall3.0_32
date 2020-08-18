package com.footballio.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.footballio.R;


public class FBResetPasswordActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = FBResetPasswordActivity.class.getSimpleName();

    private Context mycontext;
    private Button btn_reset_password_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        initUi();
    }

    private void initUi() {
        mycontext=this;

        btn_reset_password_login =findViewById(R.id.btn_fb_reset_password_login);

        btn_reset_password_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_fb_reset_password_login:
                Intent i = new Intent(mycontext, FBLoginActivity.class);
                startActivity(i);
                break;
        }

    }
}
