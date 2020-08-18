package com.footballio.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.Utils.Loader;
import com.footballio.Utils.Prefs;
import com.footballio.model.BasicResponse;
import com.footballio.model.ForgotPasswordResponse;
import com.footballio.retrofit.ApiClient;
import com.footballio.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FBChangePasswordActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = FBChangePasswordActivity.class.getSimpleName();

    private Context mycontext;
    private EditText change_password_otp,change_password_new,change_password_confirm;
    private Button btn_change_password_submit;
    private TextView change_password_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        initUi();
    }

    private void initUi() {
        mycontext=this;
        change_password_otp =findViewById(R.id.fb_change_pwrd_otp);
        change_password_new =findViewById(R.id.fb_change_pwrd_new_password);
        change_password_confirm =findViewById(R.id.fb_change_pwrd_confirm_password);
        btn_change_password_submit =findViewById(R.id.btn_fb_change_password_submit);
        change_password_contact =findViewById(R.id.fb_change_password_contact);

        btn_change_password_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_fb_change_password_submit:
                if (change_password_otp.getText().toString().isEmpty()){
                    Toast.makeText(mycontext, "Please enter OTP", Toast.LENGTH_SHORT).show();
                }else if (change_password_new.getText().toString().isEmpty()){
                    Toast.makeText(mycontext, "Please enter password", Toast.LENGTH_SHORT).show();
                }else if (change_password_confirm.getText().toString().isEmpty()){
                    Toast.makeText(mycontext, "Please enter confirm password", Toast.LENGTH_SHORT).show();
                }else if (change_password_new.getText().toString().equals(change_password_confirm.getText().toString())){
                    Toast.makeText(mycontext, "Password Mismatch", Toast.LENGTH_SHORT).show();
                }else {
                    updatePassword(change_password_new.getText().toString(),change_password_otp.getText().toString());
                }
                break;
        }

    }

    Call<BasicResponse> updatePasswordResponseCall;

    private void updatePassword( String password,String otp) {

        Loader.showLoad(mycontext,true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        updatePasswordResponseCall = apiService.update_password(password,otp);


        updatePasswordResponseCall.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {

                if (response.body().isStatus() == true) {
                    Intent i = new Intent(mycontext, FBResetPasswordActivity.class);
                    startActivity(i);
                    finish();

                    // activated_email(email);
                    Toast.makeText(mycontext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: register sucess\"");

                } else {

                    Toast.makeText(mycontext, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "onResponse: register failed");
                } Loader.showLoad(mycontext,false);
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: loginUser" + t.getMessage().toString());
                Loader.showLoad(mycontext,false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });



    }
}
