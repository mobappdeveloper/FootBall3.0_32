package com.footballio.Activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.Utils.Loader;
import com.footballio.Utils.Prefs;
import com.footballio.model.BasicResponse;
import com.footballio.retrofit.ApiClient;
import com.footballio.retrofit.ApiInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FBFeedbackActivity extends AppCompatActivity {

    private static final String TAG = FBFeedbackActivity.class.getSimpleName();

    @BindView(R.id.fb_comment)
    EditText fbComment;
    @BindView(R.id.btn_fb_feedback_submit)
    Button btnFbFeedbackSubmit;
    Context mycontext;
    @BindView(R.id.fb_profile_change_password_back)
    LinearLayout fbProfileChangePasswordBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        initUI();
    }

    private void initUI() {
        mycontext = this;
        btnFbFeedbackSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fbComment.getText().toString() != null) {
                    feedback(fbComment.getText().toString());
                } else {
                    Toast.makeText(mycontext, "Please Enter feedback", Toast.LENGTH_SHORT).show();
                }
            }
        });
        fbProfileChangePasswordBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    Call<BasicResponse> feedbackResponseCall;

    private void feedback(String comment) {

        Loader.showLoad(mycontext, true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        feedbackResponseCall = apiService.feedback(Prefs.getString(APPConst.PROFILEEMAIL, ""), comment);


        feedbackResponseCall.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {

                if (response.body().isStatus() == true) {
                    Toast.makeText(mycontext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    finish();


                } else {

                    Toast.makeText(mycontext, response.body().getMessage(), Toast.LENGTH_SHORT).show();


                }
                Loader.showLoad(mycontext, false);
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: loginUser" + t.getMessage().toString());
                Loader.showLoad(mycontext, false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
