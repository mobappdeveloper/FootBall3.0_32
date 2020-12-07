package com.footballio.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.footballio.R;
import com.footballio.Utils.AppConst;
import com.footballio.Utils.Utils;
import com.footballio.model.dashboard.Program;
import com.footballio.model.dashboard.UserWeekReport;
import com.footballio.view.adapter.WorkoutAdapter;
import com.footballio.view.callback.IProgressBar;
import com.footballio.view.callback.OnBottomReachedListener;
import com.footballio.view.ui.profile.ProfileActivity;
import com.footballio.viewmodel.DashboardViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DashboardActivity extends AppCompatActivity implements IProgressBar {
    @Inject
    public WorkoutAdapter mAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Program> list = new ArrayList<>();
    private ImageView img_library, img_olympic;
    private DashboardViewModel dashboardViewModel;
    private TextView txt_week, txt_technique, txt_fitness, txt_body, txt_mental, txt_health, txt_behaviour;
    //private ImageView img_week, img_technique, img_fitness, img_body, img_mental, img_health, img_behaviour;
    private ImageView img_techniqueShadow, img_fitnessShadow, img_bodyShadow, img_mentalShadow, img_healthShadow, img_behaviourShadow;
    private ImageView img_profileModule, img_challengeModule;
    private ProgressBar progressBar;
    private View bottomView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard2);
        init();

        img_library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, LibraryActivity.class));
            }
        });

        img_olympic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, LibraryActivity.class));
            }
        });

        img_profileModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, ProfileActivity.class));
            }
        });

        img_challengeModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showToast("CHALLENGE", DashboardActivity.this);
            }
        });
    }


    private void init() {
        createViews();
        getReportList();
        setUpRecyclerView();
        refreshReportList();
    }

    private void refreshReportList() {
        dashboardViewModel.refreshDashboardData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                getReportList();
            }
        });
    }

    private void setUpRecyclerView() {
        //recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter.setProgramList(list);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnBottomReachedListener(new OnBottomReachedListener() {
            @Override
            public void onBottomReached(int position) {
                bottomView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFromBottomToTop(int position) {
                bottomView.setVisibility(View.GONE);
            }
        });
    }


    private void createViews() {
        recyclerView = findViewById(R.id.recyclerview_dashboard);
        img_library = findViewById(R.id.img_dashboard_library);
        img_olympic = findViewById(R.id.img_dashboard_olympic);
        txt_week = findViewById(R.id.txt_dashboard_week);
        txt_technique = findViewById(R.id.txt_dashboard_ringTechnique);
        txt_fitness = findViewById(R.id.txt_dashboard_ringFitness);
        txt_body = findViewById(R.id.txt_dashboard_ringbody);
        txt_mental = findViewById(R.id.txt_dashboard_ringmental);
        txt_health = findViewById(R.id.txt_dashboard_ringhealth);
        txt_behaviour = findViewById(R.id.txt_dashboard_ringbehaviour);

//        img_technique = findViewById(R.id.img_dashboard_ringTechnique);
//        img_fitness = findViewById(R.id.img_dashboard_ringFitness);
//        img_body = findViewById(R.id.img_dashboard_ringbody);
//        img_mental = findViewById(R.id.img_dashboard_ringmental);
//        img_health = findViewById(R.id.img_dashboard_ringhealth);
//        img_behaviour = findViewById(R.id.img_dashboard_ringbehaviour);

        img_techniqueShadow = findViewById(R.id.img_dashboard_ringTechniqueShadow);
        img_fitnessShadow = findViewById(R.id.img_dashboard_ringFitnessShadow);
        img_bodyShadow = findViewById(R.id.img_dashboard_ringbodyShadow);
        img_mentalShadow = findViewById(R.id.img_dashboard_ringmentalShadow);
        img_healthShadow = findViewById(R.id.img_dashboard_ringhealthShadow);
        img_behaviourShadow = findViewById(R.id.img_dashboard_ringbehaviourShadow);
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        img_profileModule = findViewById(R.id.img_dashboard_profile);
        img_challengeModule = findViewById(R.id.img_dashboard_challenge);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        bottomView = findViewById(R.id.bottomView);
        dashboardViewModel.initDashboardLiveData();


    }


    private void getReportList() {
        showProgressBar();
        dashboardViewModel.getWeekReport(getUserId() + "", AppConst.LANG_ENGLISH);
        dashboardViewModel.getErrorResponse().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Utils.showToast(s, DashboardActivity.this);
                hideProgressBar();
            }
        });

        dashboardViewModel.getUserWeekReport().observe(this, new Observer<UserWeekReport>() {
            @Override
            public void onChanged(UserWeekReport s) {
                txt_week.setText(s.getWeek());
                txt_technique.setText(setRingScore(txt_technique.getId(), s.getTechnique()));
                txt_fitness.setText(setRingScore(txt_fitness.getId(), s.getFitness()));
                txt_body.setText(setRingScore(txt_body.getId(), s.getBody()));
                txt_mental.setText(setRingScore(txt_mental.getId(), s.getMental()));
                txt_health.setText(setRingScore(txt_health.getId(), s.getHealth()));
                txt_behaviour.setText(setRingScore(txt_behaviour.getId(), s.getBehaviour()));
                list.clear();
                list.addAll(s.getProgramList());
                mAdapter.setProgramList(list);
                hideProgressBar();
            }
        });

    }

    private int getUserId() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            return bundle.getInt(AppConst.UID, 0);
        } else {
            return dashboardViewModel.getLastLoginUserId();
        }

    }

    private String setRingScore(int viewId, String value) {
        int scoreValue = Utils.convertStringToInt(value);
        switch (viewId) {
            case R.id.txt_dashboard_ringTechnique:
                if (scoreValue == 15) {
                    img_techniqueShadow.setImageResource(R.drawable.tarqueue_shadow);
                } else if (scoreValue > 0) {
                    img_techniqueShadow.getLayoutParams().width = getResources().getDimensionPixelSize(R.dimen.img_progress_width);
                    img_techniqueShadow.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.img_progress_width);
                    //img_techniqueShadow.setImageResource(R.drawable.tarquee_shadow13);
                    img_techniqueShadow.setImageResource(R.drawable.ic_tarquee_shadow13);
                    // todo: need to delete tarquee_shadow13 file
                }
                return value + AppConst.SCORE;
            case R.id.txt_dashboard_ringFitness:
                if (scoreValue == 15) {
                    img_fitnessShadow.setImageResource(R.drawable.yellow_shadow);
                } else if (scoreValue > 0) {
                    img_fitnessShadow.getLayoutParams().width = getResources().getDimensionPixelSize(R.dimen.img_progress_width);
                    img_fitnessShadow.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.img_progress_width);
                    img_fitnessShadow.setImageResource(R.drawable.ic_yellow_shadow13);
                    //img_fitnessShadow.setColorFilter(R.color.yellow);
                }
                return value + AppConst.SCORE;
            case R.id.txt_dashboard_ringbody:
                if (scoreValue == 15) {
                    img_bodyShadow.setImageResource(R.drawable.red_shadow);
                } else if (scoreValue > 0) {
                    img_bodyShadow.getLayoutParams().width = getResources().getDimensionPixelSize(R.dimen.img_progress_width);
                    img_bodyShadow.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.img_progress_width);
                    img_bodyShadow.setImageResource(R.drawable.ic_red_shadow13);
                }
                return value + AppConst.SCORE;
            case R.id.txt_dashboard_ringmental:
                if (scoreValue == 15) {
                    img_mentalShadow.setImageResource(R.drawable.blue_shadow);
                } else if (scoreValue > 0) {
                    img_mentalShadow.getLayoutParams().width = getResources().getDimensionPixelSize(R.dimen.img_progress_width);
                    img_mentalShadow.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.img_progress_width);
                    img_mentalShadow.setImageResource(R.drawable.ic_blue_shadow13);
                }
                return value + AppConst.SCORE;
            case R.id.txt_dashboard_ringhealth:
                if (scoreValue == 15) {
                    img_healthShadow.setImageResource(R.drawable.brown_shadow);
                } else if (scoreValue > 0) {
                    img_healthShadow.getLayoutParams().width = getResources().getDimensionPixelSize(R.dimen.img_progress_width);
                    img_healthShadow.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.img_progress_width);
                    img_healthShadow.setImageResource(R.drawable.ic_brown_shadow13);
                }
                return value + AppConst.SCORE;
            case R.id.txt_dashboard_ringbehaviour:
                if (scoreValue == 15) {
                    img_behaviourShadow.setImageResource(R.drawable.violet_shadow);
                } else if (scoreValue > 0) {
                    img_behaviourShadow.getLayoutParams().width = getResources().getDimensionPixelSize(R.dimen.img_progress_width);
                    img_behaviourShadow.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.img_progress_width);
                    img_behaviourShadow.setImageResource(R.drawable.ic_violet_shadow13);
                }
                return value + AppConst.SCORE;
            default:
        }
        return value + AppConst.SCORE;
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }


}
