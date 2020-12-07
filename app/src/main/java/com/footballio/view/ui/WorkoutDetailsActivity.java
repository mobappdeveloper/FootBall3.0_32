package com.footballio.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.hilt.android.AndroidEntryPoint;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.footballio.R;
import com.footballio.Utils.AppConst;
import com.footballio.Utils.Utils;
import com.footballio.model.dashboard.ProgramDetails;
import com.footballio.model.dashboard.Session;
import com.footballio.view.adapter.WorkoutDetailsAdapter;
import com.footballio.view.callback.IProgressBar;
import com.footballio.viewmodel.DashboardViewModel;

import java.util.HashMap;
import java.util.List;

@AndroidEntryPoint
public class WorkoutDetailsActivity extends AppCompatActivity implements IProgressBar {
    private WorkoutDetailsAdapter workoutDetailsAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private HashMap<String, List<Session>> list = new HashMap<>();
    private Button beginWorkout;
    private DashboardViewModel dashboardViewModel;
    private TextView txt_prgName, txt_prgDesc, txt_prgIntrvl, txt_prgBigDesc;
    private ImageView img_prgBanner;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_details);
        init();
        beginWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressBar();
                startWorkout();
            }
        });
    }

    private void startWorkout() {
        Bundle bundle = getIntent().getExtras();
        dashboardViewModel.beginWorkout(bundle.getInt(AppConst.PID)).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Utils.showToast(s, WorkoutDetailsActivity.this);
                startActivity(new Intent(WorkoutDetailsActivity.this, WorkoutBeginActivity.class).putExtra(AppConst.PID, bundle.getInt(AppConst.PID)).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                hideProgressBar();
            }
        });
    }

    private void init() {
        createViews();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        getExerciseList();
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        workoutDetailsAdapter = new WorkoutDetailsAdapter();

    }

    private void createViews() {
        recyclerView = findViewById(R.id.recycler_workoutdetails);
        beginWorkout = findViewById(R.id.butt_begin_workout);
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        img_prgBanner = findViewById(R.id.img_workoutdetails_programBanner);
        txt_prgName = findViewById(R.id.txt_workoutdetails_programName);
        txt_prgDesc = findViewById(R.id.txt_workoutdetails_programDesc);
        txt_prgIntrvl = findViewById(R.id.txt_workoutdetails_programIntrvl);
        txt_prgBigDesc = findViewById(R.id.txt_workoutdetails_programBigDesc);
        progressBar = findViewById(R.id.prb_workoutDetails);
        dashboardViewModel.initWorkoutDetailLiveData();
    }

    private void getExerciseList() {
        showProgressBar();
        dashboardViewModel.getWorkoutDetails(getProgramId(), AppConst.LANG_GERMAN);

        dashboardViewModel.getErrorResponse().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Utils.showToast(s, WorkoutDetailsActivity.this);
                hideProgressBar();
            }
        });

        dashboardViewModel.getProgramDetailsData().observe(this, new Observer<ProgramDetails>() {
            @Override
            public void onChanged(ProgramDetails programDetails) {
                if (programDetails.getSessions() != null) {
                    setViewValues(programDetails);
                    list.putAll(programDetails.getSessions());
                    workoutDetailsAdapter.setList(list);
                    recyclerView.setAdapter(workoutDetailsAdapter);
                    hideProgressBar();
                }

            }
        });
    }

    private void setViewValues(ProgramDetails viewValues) {
        txt_prgName.setText(viewValues.getProgramName());
        txt_prgDesc.setText(viewValues.getCategoryName());
        txt_prgIntrvl.setText(viewValues.getTime());
        txt_prgBigDesc.setText(viewValues.getDescription());
        Glide.with(img_prgBanner)
                .load(viewValues.getPhoto())
                .placeholder(R.drawable.group_72683)
                .error(R.drawable.group_72683)
                .fallback(R.drawable.group_72683)
                .into(img_prgBanner);
    }

    private int getProgramId() {
        Bundle bundle = getIntent().getExtras();
        return bundle.getInt("pid", 0);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }


    @Override
    protected void onPause() {
        super.onPause();
        //dashboardViewModel.beginWorkout(0).removeObservers(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //dashboardViewModel.beginWorkout(0).removeObservers(this);
    }


}