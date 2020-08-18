package com.footballio.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.Utils.Loader;
import com.footballio.Utils.Prefs;
import com.footballio.Utils.Utils;
import com.footballio.model.BasicResponse;
import com.footballio.model.dashboard.checkworkout.CheckWorkoutResponse;
import com.footballio.model.dashboard.viewprogram.ViewProgramResponse;
import com.footballio.retrofit.ApiClient;
import com.footballio.retrofit.ApiInterface;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FBDashboardViewActivity extends AppCompatActivity {

    private static final String TAG = "FBDashboardViewActivity";

    @BindView(R.id.fb_workout_view_title)
    TextView fbWorkoutViewTitle;
    @BindView(R.id.fb_workout_view_image)
    ImageView fbWorkoutViewImage;
    @BindView(R.id.fb_workout_view_category)
    TextView fbWorkoutViewCategory;

    @BindView(R.id.fb_workout_view_description)
    TextView fbWorkoutViewDescription;
    @BindView(R.id.btn_fb_workout_view)
    Button btnFbWorkoutView;
    @BindView(R.id.green)
    TextView green;
    @BindView(R.id.yellow)
    TextView yellow;
    @BindView(R.id.red)
    TextView red;
    @BindView(R.id.blue)
    TextView blue;
    @BindView(R.id.orange)
    TextView orange;
    @BindView(R.id.purpule)
    TextView purpule;
    @BindView(R.id.fb_workout_view_back)
    LinearLayout fbWorkoutViewBack;
    @BindView(R.id.txt_share)
    ImageView txtShare;
    private ViewProgramResponse viewprogramResponse;
    Context mycontext;
    private String pid = "";
    private CheckWorkoutResponse checkworkoutResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fbdashboard_view);
        mycontext = this;
        ButterKnife.bind(this);
        getBundle();
        init();
    }

    private void init() {
        fbWorkoutViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewprogramResponse!=null) {
                    String shareBody = viewprogramResponse.getPrograms().get(0).getProgramName()+viewprogramResponse.getPrograms().get(0).getBigdesc();
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, viewprogramResponse.getPrograms().get(0).getProgramName());
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(sharingIntent, "Football 3.0 share vis"));
                }
            }
        });
    }

    private void getBundle() {
        Intent intent = getIntent();
        if (intent.getStringExtra(APPConst.PID) != null) {
            pid = intent.getStringExtra(APPConst.PID);
            viewprogram(pid);
        }
    }

    Call<ViewProgramResponse> viewProgramResponseCall;

    private void viewprogram(String pid) {

        Loader.showLoad(mycontext, true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        viewProgramResponseCall = apiService.viewprogram(pid, Prefs.getString(APPConst.TOKEN, ""));


        viewProgramResponseCall.enqueue(new Callback<ViewProgramResponse>() {
            @Override
            public void onResponse(Call<ViewProgramResponse> call, Response<ViewProgramResponse> response) {
                Log.d(TAG, "onResponse: allCategoryResponse" + new Gson().toJson(response.body()));
                viewprogramResponse = response.body();
                //  Log.d(TAG, "onResponse:size "+allCategoryResponse.size());
                Glide.with(mycontext)
                        .load(viewprogramResponse.getPrograms().get(0).getBigphoto())
                        .placeholder(R.drawable.ic_grid)
                        .error(R.drawable.ic_grid)
                        .into(fbWorkoutViewImage);

              /*  Prefs.putInt(APPConst.PGREENPERCENTAGE, Integer.parseInt(viewprogramResponse.getPrograms().get(0).getGreen()));
                Prefs.putInt(APPConst.PBLUEPERCENTAGE, Integer.parseInt(viewprogramResponse.getPrograms().get(0).getBlue()));
*/
                fbWorkoutViewTitle.setText(viewprogramResponse.getPrograms().get(0).getProgramName());
                fbWorkoutViewCategory.setText(viewprogramResponse.getPrograms().get(0).getProgramName());
                fbWorkoutViewDescription.setText(viewprogramResponse.getPrograms().get(0).getBigdesc());
                green.setText(viewprogramResponse.getPrograms().get(0).getGreen());
                yellow.setText(viewprogramResponse.getPrograms().get(0).getYellow());
                red.setText(viewprogramResponse.getPrograms().get(0).getRed());
                blue.setText(viewprogramResponse.getPrograms().get(0).getBlue());
                orange.setText(viewprogramResponse.getPrograms().get(0).getOrange());
                purpule.setText(viewprogramResponse.getPrograms().get(0).getPurpule());
                if (viewprogramResponse.getPrograms().get(0).getStatus().equals("Y")) {
                    btnFbWorkoutView.setText("Workout hinzufügen");
                    Utils.chnageBAckgroundDrawable(mycontext, btnFbWorkoutView, R.drawable.ic_button_bg);
                } else {
                    btnFbWorkoutView.setText("Workout entfernen");
                    Utils.chnageBAckgroundDrawable(mycontext, btnFbWorkoutView, R.drawable.textview_btn);


                }
                btnFbWorkoutView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "onClick: " + viewprogramResponse.getPrograms().get(0).getStatus());
                        if (viewprogramResponse.getPrograms().get(0).getStatus().equals("Y")) {
                            addWorkout(pid);
                            Intent intent = new Intent(mycontext, FBDashboardActivity.class);
                            intent.putExtra(APPConst.PWORKSTATUS, "3");
                            startActivity(intent);
                        } else {
                            checkWorkout(pid);


                        }
                    }
                });

                Loader.showLoad(mycontext, false);
            }

            @Override
            public void onFailure(Call<ViewProgramResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: loginUser" + t.getMessage().toString());
                Loader.showLoad(mycontext, false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });


    }


    Call<BasicResponse> addWorkoutResponseCall;

    private void addWorkout(String pid) {

        Loader.showLoad(mycontext, true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        addWorkoutResponseCall = apiService.addworkout(pid, Prefs.getString(APPConst.TOKEN, ""));


        addWorkoutResponseCall.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                Log.d(TAG, "onResponse: allCategoryResponse" + new Gson().toJson(response.body()));
                if (response.body().isStatus() == true) {
                    Toast.makeText(mycontext, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mycontext, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
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


    Call<CheckWorkoutResponse> workoutResponseCall;

    private void checkWorkout(String pid) {

        Loader.showLoad(mycontext, true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        workoutResponseCall = apiService.checkworkout(Prefs.getString(APPConst.TOKEN, ""));


        workoutResponseCall.enqueue(new Callback<CheckWorkoutResponse>() {
            @Override
            public void onResponse(Call<CheckWorkoutResponse> call, Response<CheckWorkoutResponse> response) {
                Log.d(TAG, "onResponse: CheckWorkoutResponse" + new Gson().toJson(response.body()));
                checkworkoutResponse = response.body();
                for (int i = 0; i < checkworkoutResponse.getData().size(); i++) {
                    if (checkworkoutResponse.getData().get(i).getPid().equals(pid)) {
                        deleteWorkout(checkworkoutResponse.getData().get(i).getWorkoutId());
                    }

                }
                Loader.showLoad(mycontext, false);
            }

            @Override
            public void onFailure(Call<CheckWorkoutResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: loginUser" + t.getMessage().toString());
                Loader.showLoad(mycontext, false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });


    }

    Call<BasicResponse> deleteWorkoutResponseCall;

    private void deleteWorkout(String workid) {

        Loader.showLoad(mycontext, true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        deleteWorkoutResponseCall = apiService.deleteworkout(workid);


        deleteWorkoutResponseCall.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                Log.d(TAG, "onResponse: allCategoryResponse" + new Gson().toJson(response.body()));
                if (response.body().isStatus() == true) {
                    btnFbWorkoutView.setText("Workout hinzufügen");
                    Utils.chnageBAckgroundDrawable(mycontext, btnFbWorkoutView, R.drawable.ic_button_bg);
                    viewprogramResponse.getPrograms().get(0).setStatus("Y");
                    Toast.makeText(mycontext, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mycontext, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
