package com.footballio.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.model.login.dashboard.ProgramDetails;
import com.footballio.model.login.dashboard.UserWeekReport;
import com.footballio.model.login.dashboard.Workouts;
import com.footballio.view.adapter.WorkoutDetailsAdapter;
import com.footballio.viewmodel.DashboardViewModel;

import java.util.ArrayList;
import java.util.List;

public class WorkoutDetailsActivity extends AppCompatActivity {
    private WorkoutDetailsAdapter workoutDetailsAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Workouts> list = new ArrayList<>();
    private Button beginWorkout;
    private DashboardViewModel dashboardViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_details);
        init();
        beginWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WorkoutDetailsActivity.this, WorkoutBeginActivity.class));
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
        workoutDetailsAdapter.setList(list);
        recyclerView.setAdapter(workoutDetailsAdapter);
    }

    private void createViews() {
        recyclerView = findViewById(R.id.recycler_workoutdetails);
        beginWorkout = findViewById(R.id.butt_begin_workout);
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
    }


    private void getExerciseList() {
        dashboardViewModel.getProgramData();
        dashboardViewModel.getWorkoutDetails(getProgramId(), APPConst.LANG_ENGLISH);
        dashboardViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });

        dashboardViewModel.getProgramDetailsData().observe(this, new Observer<ProgramDetails>() {
            @Override
            public void onChanged(ProgramDetails programDetails) {

                list.addAll(programDetails.getWorkoutList());
            }
        });
    }

    private int getProgramId() {
        Bundle bundle=getIntent().getExtras();
       return bundle.getInt("pid",0);
    }
}