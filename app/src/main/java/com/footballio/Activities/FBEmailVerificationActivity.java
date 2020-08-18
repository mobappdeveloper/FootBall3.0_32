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
import com.footballio.model.RegisterResponse;
import com.footballio.retrofit.ApiClient;
import com.footballio.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FBEmailVerificationActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = FBEmailVerificationActivity.class.getSimpleName();

    private Context mycontext;
    private TextView email_verification_text;
    private Button btn_email_verify,btn_fb_email_verificationotp;
    private EditText fb_register_otp;
    private String email="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);
        initUi();
    }

    private void initUi() {
        mycontext=this;
getBundle();
        email_verification_text=findViewById(R.id.fb_email_verification_tv);
        btn_email_verify=findViewById(R.id.btn_fb_email_verification);
        btn_fb_email_verificationotp=findViewById(R.id.btn_fb_email_verificationotp);
        fb_register_otp=findViewById(R.id.fb_register_otp);

        btn_email_verify.setOnClickListener(this);
        btn_fb_email_verificationotp.setOnClickListener(this);


    }

    private void getBundle() {
        Intent intent = getIntent();
        if (intent.getStringExtra(APPConst.PROFILEEMAIL)!=null){
            email=intent.getStringExtra(APPConst.PROFILEEMAIL);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.btn_fb_email_verification:
                /*Intent i = new Intent(mycontext, FBLoginActivity.class);
                startActivity(i);*/
                break;
                case R.id.btn_fb_email_verificationotp:
               if (fb_register_otp.getText().toString().isEmpty()){
                   Toast.makeText(mycontext, "Please enter OTP", Toast.LENGTH_SHORT).show();
               }else {
                   VerifyOTP(Prefs.getString(APPConst.PROFILEEMAIL,""),fb_register_otp.getText().toString());
               }
                break;
        }


    }


    Call<BasicResponse> verifyOTPResponseCall;

    private void VerifyOTP( String email, String code) {

        Loader.showLoad(mycontext,true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        verifyOTPResponseCall = apiService.verify_otp(email, code);


        verifyOTPResponseCall.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {

                if (response.body().isStatus() == true) {

                    activated_email(email);
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

    private void activated_email(String email) {

        Loader.showLoad(mycontext,true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        verifyOTPResponseCall = apiService.activated_email(email);


        verifyOTPResponseCall.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {

                if (response.body().isStatus() == true) {

                    Intent i1 = new Intent(mycontext, FBLoginActivity.class);
                    startActivity(i1);
                    finish();
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
        });}
}
