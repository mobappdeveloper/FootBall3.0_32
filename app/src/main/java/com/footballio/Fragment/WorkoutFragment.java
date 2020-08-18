package com.footballio.Fragment;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.footballio.Activities.FBDashboardActivity;
import com.footballio.Activities.FBLoaderActivity;
import com.footballio.Adapter.WorkoutAdapter;
import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.Utils.Loader;
import com.footballio.Utils.Prefs;
import com.footballio.model.dashboard.checkworkout.CheckWorkoutResponse;
import com.footballio.retrofit.ApiClient;
import com.footballio.retrofit.ApiInterface;
import com.google.gson.Gson;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class WorkoutFragment extends Fragment {

    private WorkoutFragment.OnFragmentInteractionListener listener;
    private Context mycontext;
    private RecyclerView workout_gridView;
    private TextView empty,fb_home_tv_name;
    ArrayList<ClipData.Item> gridArray = new ArrayList<ClipData.Item>();
    private WorkoutAdapter workoutAdapter;
    private CheckWorkoutResponse checkworkoutResponse;
    private CircularProgressBar circularProgressBarGreen,circularProgressBarBlue;
    private Handler handler;
    private Button btn_dash;
    private LinearLayout payment_info;
    public static WorkoutFragment newInstance() {
        return new WorkoutFragment();
    }
private LinearLayout linear_no_data;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View aView = inflater.inflate(R.layout.fragment_workout, container, false);
        initUi(aView);
        // Inflate the layout for this fragment
        //  setHasOptionsMenu(true);
        return aView;
    }

    private void initUi(View aView) {
        mycontext = getActivity();
        workout_gridView = aView.findViewById(R.id.gridView1);
        fb_home_tv_name = aView.findViewById(R.id.fb_home_tv_name);
        linear_no_data = aView.findViewById(R.id.linear_no_data);
        payment_info = aView.findViewById(R.id.payment_info);
        btn_dash = aView.findViewById(R.id.btn_dash);
        checkWorkout();
        fb_home_tv_name.setText(Prefs.getString(APPConst.PROFILEUSERNAME,""));

        circularProgressBarGreen = aView.findViewById(R.id.progressgreen);
        circularProgressBarBlue = aView.findViewById(R.id.progressblue);
        handler = new Handler();
        circularProgressBarGreen.setProgressWithAnimation(100, (long) 700);
        circularProgressBarBlue.setProgressWithAnimation(100, (long) 700);
        final Runnable r = new Runnable() {
            public void run() {
                circularProgressBarGreen.setProgressWithAnimation(Prefs.getInt(APPConst.PGREENPERCENTAGE,0), (long) 500);
                circularProgressBarBlue.setProgressWithAnimation(Prefs.getInt(APPConst.PBLUEPERCENTAGE,0), (long) 500);
                handler.postDelayed(this, 1000);
            }
        };

        handler.postDelayed(r, 1000);

        btn_dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mycontext, FBDashboardActivity.class);
                intent.putExtra(APPConst.DASHREDIRECT,"1");
                mycontext.startActivity(intent);
                ((Activity)mycontext).finish();
            }
        });

        payment_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mycontext, FBLoaderActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof WorkoutFragment.OnFragmentInteractionListener) {
            listener = (WorkoutFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnFragmentInteractionListener {
    }


    Call<CheckWorkoutResponse> workoutResponseCall;

    private void checkWorkout() {
        Loader.showLoad(mycontext,true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        workoutResponseCall = apiService.checkworkout(Prefs.getString(APPConst.TOKEN, ""));

        workoutResponseCall.enqueue(new Callback<CheckWorkoutResponse>() {
            @Override
            public void onResponse(Call<CheckWorkoutResponse> call, Response<CheckWorkoutResponse> response) {
                Log.d(TAG, "onResponse: CheckWorkoutResponse" + new Gson().toJson(response.body()));

                checkworkoutResponse = response.body();
                if (checkworkoutResponse.getData()!=null) {
                    workoutAdapter = new WorkoutAdapter(mycontext, checkworkoutResponse);
                    workout_gridView.setLayoutManager(new GridLayoutManager(mycontext, 2));
                    workout_gridView.setAdapter(workoutAdapter);
                    workout_gridView.setVisibility(View.VISIBLE);
                    linear_no_data.setVisibility(View.GONE);
                }else{
                    workout_gridView.setVisibility(View.GONE);
                    linear_no_data.setVisibility(View.VISIBLE);
                    Log.d(TAG, "onResponse: CheckWorkoutResponse empty data");
                } Loader.showLoad(mycontext,false);
            }

            @Override
            public void onFailure(Call<CheckWorkoutResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: loginUser" + t.getMessage().toString());
                Loader.showLoad(mycontext,false);
                workout_gridView.setVisibility(View.GONE);
                linear_no_data.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });


    }

}
