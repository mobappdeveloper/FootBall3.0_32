package com.footballio.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.Utils.Loader;
import com.footballio.Utils.Prefs;
import com.footballio.model.BasicResponse;
import com.footballio.retrofit.ApiClient;
import com.footballio.retrofit.ApiInterface;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FBWorkoutAccomplishedActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = FBWorkoutAccomplishedActivity.class.getSimpleName();

    private Button  btn_accomplished;
    private Context mycontext;
    private String pid="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_accomplished);

        initUi();
    }

    private void initUi() {
        mycontext=this;

        btn_accomplished=findViewById(R.id.btn_fb_accompolished_finish);
getBundle();
        btn_accomplished.setOnClickListener(this);
    }
    private void getBundle() {
        Intent intent = getIntent();
        if (intent.getStringExtra(APPConst.PID) != null) {
            pid = intent.getStringExtra(APPConst.PID);

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_fb_accompolished_finish:
                workoutComplted(pid);

                break;
        }


    }

    Call<BasicResponse> addWorkoutResponseCall;

    private void workoutComplted(String pid) {

        Loader.showLoad(mycontext,true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        addWorkoutResponseCall = apiService.workcomplete(pid, Prefs.getString(APPConst.TOKEN, ""));


        addWorkoutResponseCall.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                Log.d(TAG, "onResponse: allCategoryResponse" + new Gson().toJson(response.body()));
                String workstart="0";
                Log.d(TAG, "setupBottomBarHolderActivity:getBundle workoutComplted "+workstart);
                Intent i = new Intent(mycontext, FBDashboardActivity.class);
                i.putExtra(APPConst.PWORKSTATUS,workstart);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
              /*  if (response.body().isStatus() == true) {
                    Toast.makeText(myContext, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(myContext, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
*/Loader.showLoad(mycontext,false);
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
