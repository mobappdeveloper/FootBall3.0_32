package com.footballio.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.footballio.R;
import com.footballio.Utils.Utils;
import com.footballio.model.login.User;
import com.footballio.view.adapter.TrainingAdapter;
import com.footballio.view.callback.IProgressBar;
import com.footballio.viewmodel.LoginViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ChooseTrainingActivity extends AppCompatActivity implements IProgressBar {
    private RecyclerView recyclerView;
    private TrainingAdapter trainingAdapter;
    private GridLayoutManager manager;
    private List<String> strings = new ArrayList<>();
    private Button button_next;
    private ProgressBar progressBar;
    private LoginViewModel loginViewModel;
    private ImageView imageView_back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_traininig);
        init();
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressBar();
                updateProfile();

            }
        });

        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        // observer data and update in recyclerview
        //trainingAdapter.setTrainingList();
    }

    private void updateProfile() {
        Bundle bundle = getIntent().getExtras();
        loginViewModel.updateProfile(bundle);
        loginViewModel.getErrorResponse().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                hideProgressBar();
                Utils.showToast(s, ChooseTrainingActivity.this);
            }
        });

        loginViewModel.getSuccessResponse().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                hideProgressBar();
                Utils.showToast(user.getResponse(), ChooseTrainingActivity.this);
                Intent intent = new Intent(ChooseTrainingActivity.this, DashboardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private void init() {
        getList();
        recyclerView = findViewById(R.id.recycleview_training);
        progressBar = findViewById(R.id.progressBar_training);
        button_next = findViewById(R.id.butt_training_next);
        manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        //recyclerView.setHasFixedSize(true);
        trainingAdapter = new TrainingAdapter();
        trainingAdapter.setTrainingList(strings);
        recyclerView.setAdapter(trainingAdapter);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.iProgressBar = this;
        imageView_back = findViewById(R.id.img_back_cta);
    }

    private void getList() {
        strings.add("m");
        strings.add("m");
        strings.add("m");
        strings.add("m");
        strings.add("m");
        strings.add("m");
        strings.add("m");
        strings.add("m");
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }
}
