package com.footballio.view.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.footballio.R;
import com.footballio.Utils.AppConst;
import com.footballio.Utils.Utils;
import com.footballio.model.login.dashboard.LibraryProgram;
import com.footballio.view.adapter.WorkoutAdapter;
import com.footballio.view.callback.IProgressBar;
import com.footballio.viewmodel.DashboardViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LibraryDetailActivity extends AppCompatActivity implements IProgressBar {
    @Inject
    public WorkoutAdapter mAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<LibraryProgram> list = new ArrayList<>();
    private List<LibraryProgram> filterList = new ArrayList<>();
    private DashboardViewModel dashboardViewModel;
    private ImageView img_back, img_filter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_detail);
        init();
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        img_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterDialog();
            }
        });
    }

    private void init() {
        createViews();
        setUpRecyclerview();
        getList();
    }

    private void setUpRecyclerview() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter.setViewType(1);
        mAdapter.setLibraryProgramList(list);
        recyclerView.setAdapter(mAdapter);
    }

    private void createViews() {
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        recyclerView = findViewById(R.id.recycleview_library_detail);
        img_back = findViewById(R.id.img_back);
        img_filter = findViewById(R.id.img_filter);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        dashboardViewModel.initLibraryDetailLiveData();
    }

    private void getList() {
        showProgressBar();
        Bundle bundle = getIntent().getExtras();
        String type = bundle.getString(AppConst.CN, "");
        dashboardViewModel.getWorkoutList(dashboardViewModel.getLastLoginUserId(), AppConst.LANG_ENGLISH, type).observe(this, new Observer<LibraryProgram>() {
            @Override
            public void onChanged(LibraryProgram program) {
                if (program != null) {
                    list.addAll(program.getData());
                    if (LibraryActivity.SpareTime > 0) {
                        performFilter(LibraryActivity.SpareTime);
                    } else {
                        mAdapter.setLibraryProgramList(list);
                        hideProgressBar();
                    }
                } else {
                    hideProgressBar();
                }

            }
        });

        dashboardViewModel.getErrorResponse().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Utils.showToast(s, LibraryDetailActivity.this);
                hideProgressBar();
            }
        });
    }

    private void showFilterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LibraryDetailActivity.this);
        builder.setTitle("Time");
        final View customLayout = LayoutInflater.from(LibraryDetailActivity.this).inflate(R.layout.dialog_timefilter, null);
        EditText editText = customLayout.findViewById(R.id.etxt_dialog_filter);
        editText.setText(LibraryActivity.SpareTime + "");
        builder.setView(customLayout);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String spareTime = editText.getText().toString();
                if (!TextUtils.isEmpty(spareTime)) {
                    showProgressBar();
                    LibraryActivity.SpareTime = Integer.parseInt(spareTime);
                    performFilter(LibraryActivity.SpareTime);
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void performFilter(int spareTime) {
        mAdapter.setLibraryProgramList(dashboardViewModel.filterProgram(list, spareTime));
        hideProgressBar();
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