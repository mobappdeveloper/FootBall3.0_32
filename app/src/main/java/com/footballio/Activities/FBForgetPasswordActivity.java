package com.footballio.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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


public class FBForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = FBForgetPasswordActivity.class.getSimpleName();

    private Context mycontext;
    private Button btn_forget_password_email_sent;
    private TextView forget_password_contactus;
    private EditText forget_password_email;
    private ImageView forget_password_back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        initUi();
    }

    private void initUi() {

        mycontext=this;

        forget_password_email =findViewById(R.id.fb_forget_password_email);
        forget_password_back =findViewById(R.id.fb_forget_password_back);
        forget_password_contactus =findViewById(R.id.fb_forget_password_contact);
        btn_forget_password_email_sent =findViewById(R.id.btn_fb_forget_password_sent);

        btn_forget_password_email_sent.setOnClickListener(this);
        forget_password_back.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.fb_forget_password_back:
                finish();
                break;
            case R.id.btn_fb_forget_password_sent:
               if (forget_password_email.getText().toString().isEmpty()){
                   Toast.makeText(mycontext, "Please enter email ID", Toast.LENGTH_SHORT).show();
               }else {
                   forgotpassword(forget_password_email.getText().toString());
               }
                break;
        }

    }

    Call<ForgotPasswordResponse> forgotPasswordResponseCall;

    private void forgotpassword( String email) {

        Loader.showLoad(mycontext,true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        forgotPasswordResponseCall = apiService.forgot_password(email);


        forgotPasswordResponseCall.enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {

                if (response.body().isStatus() == true) {
                    Intent i = new Intent(mycontext, FBChangePasswordActivity.class);
                    startActivity(i);
                    finish();
                    Prefs.putString(APPConst.PROFILEEMAIL,response.body().getEmail());

                    // activated_email(email);
                    Toast.makeText(mycontext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: register sucess\"");

                } else {

                    Toast.makeText(mycontext, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "onResponse: register failed");
                } Loader.showLoad(mycontext,false);
            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: loginUser" + t.getMessage().toString());
                Loader.showLoad(mycontext,false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });



    }

}
