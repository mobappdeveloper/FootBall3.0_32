package com.footballio.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.Utils.Loader;
import com.footballio.Utils.Prefs;
import com.footballio.Utils.Utils;
import com.footballio.model.LoginResponse;
import com.footballio.model.RegisterRequest;
import com.footballio.model.RegisterResponse;
import com.footballio.retrofit.ApiClient;
import com.footballio.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FBRegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = FBRegisterActivity.class.getSimpleName();

    private Context mycontext;
    private LinearLayout register_back;
    private EditText register_user_name, register_first_name, register_last_name,
            register_password, register_confirm_password, register_email;
    private CheckBox register_terms_conditions;
    private Button btn_register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initUi();
    }

    private void initUi() {
        mycontext = this;

        register_back = findViewById(R.id.fb_register_back);
        register_user_name = findViewById(R.id.fb_register_username);
        register_first_name = findViewById(R.id.fb_register_first_name);
        register_last_name = findViewById(R.id.fb_register_last_name);
        register_password = findViewById(R.id.fb_register_password);
        register_confirm_password = findViewById(R.id.fb_register_confirm_password);
        register_email = findViewById(R.id.fb_register_email);
        register_terms_conditions = findViewById(R.id.fb_register_terms_conditions);
        btn_register = findViewById(R.id.btn_fb_register);


        register_back.setOnClickListener(this);
        btn_register.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.fb_register_back:
                finish();
                break;
            case R.id.btn_fb_register:
                vaildation();
                break;
        }

    }

    private void vaildation() {
        String email = register_email.getText().toString();
        String firstname = register_first_name.getText().toString();
        String lastname = register_last_name.getText().toString();
        String password = register_password.getText().toString();
        String confirmpassword = register_confirm_password.getText().toString();
        String username = register_user_name.getText().toString();
        String createon = Utils.CurrentDate();
        if (username.isEmpty()) {
            Toast.makeText(mycontext, "Please enter username", Toast.LENGTH_SHORT).show();
        } else if (firstname.isEmpty()) {
            Toast.makeText(mycontext, "Please enter first name", Toast.LENGTH_SHORT).show();
        } else if (lastname.isEmpty()) {
            Toast.makeText(mycontext, "Please enter last name", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(mycontext, "Please enter password", Toast.LENGTH_SHORT).show();
        } else if (confirmpassword.isEmpty()) {
            Toast.makeText(mycontext, "Please enter password confirm", Toast.LENGTH_SHORT).show();
        } else if (!password.matches(confirmpassword)) {
            Toast.makeText(mycontext, "Password mismatch", Toast.LENGTH_SHORT).show();
        } else if (email.matches(confirmpassword)) {
            Toast.makeText(mycontext, "Please enter email ID", Toast.LENGTH_SHORT).show();
        } else if (!Utils.isValidEmail(email)) {
            Toast.makeText(mycontext, "Please enter valid email ID", Toast.LENGTH_SHORT).show();
        } else if (!register_terms_conditions.isChecked()) {
            Toast.makeText(mycontext, "Please accept terms and conditions", Toast.LENGTH_SHORT).show();
        } else {
            register(email, firstname, lastname, password, createon, username);
        }


    }

    Call<RegisterResponse> registerResponseCall;

    private void register(String email, String firstname, String lastname, String password, String createon, String username) {

        Loader.showLoad(mycontext, true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setCreatedOn(createon);
        registerRequest.setEmailAddress(email);
        registerRequest.setFirstName(firstname);
        registerRequest.setLastName(lastname);
        registerRequest.setPassword(password);
        registerRequest.setUserName(username);
        registerRequest.setLoginType("0");
        registerRequest.setPhoto("");
        registerRequest.setStatus("No");
        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        registerResponseCall = apiService.register(registerRequest);


        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                if (response.body().isStatus() == true) {
                    Intent intent = new Intent(mycontext, FBEmailVerificationActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    Prefs.putString(APPConst.TOKEN, response.body().getId());
                    Prefs.putString(APPConst.PROFILEEMAIL, response.body().getEmailAddress());

                    //   Toast.makeText(mycontext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: register sucess\"");

                } else {

                    Toast.makeText(mycontext, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "onResponse: register failed");
                }
                Loader.showLoad(mycontext, false);
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: loginUser" + t.getMessage().toString());
                Loader.showLoad(mycontext, false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
