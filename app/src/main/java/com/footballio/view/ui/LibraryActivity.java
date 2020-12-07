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
import com.footballio.Utils.Utils;
import com.footballio.model.login.dashboard.Philosophy;
import com.footballio.view.adapter.LibraryAdapter;
import com.footballio.view.callback.IProgressBar;
import com.footballio.viewmodel.DashboardViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LibraryActivity extends AppCompatActivity implements IProgressBar {
    private RecyclerView recyclerView;
    @Inject
    public LibraryAdapter libraryAdapter;
    private GridLayoutManager manager;
    private List<Philosophy> philosophyList = new ArrayList<>();
    private DashboardViewModel libraryViewModel;
    private ImageView img_back, img_filter;
    public static int SpareTime = 0;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
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
        setUpRecyclerView();
        getPhilosophyList();
    }

    private void createViews() {
        libraryViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        recyclerView = findViewById(R.id.recycler_olympic);
        img_back = findViewById(R.id.img_back);
        img_filter = findViewById(R.id.img_filter);
        progressBar = findViewById(R.id.progress_bar);
        libraryViewModel.initLibraryLiveData();
    }

    private void setUpRecyclerView() {
        manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        libraryAdapter.setTrainingList(philosophyList);
    }

    private void getPhilosophyList() {
        showProgressBar();
        libraryViewModel.getPhilosophyList().observe(this, new Observer<Philosophy>() {
            @Override
            public void onChanged(Philosophy philosophy) {
                if (philosophy != null) {
                    philosophyList.addAll(philosophy.getPhilosophyList());
                    recyclerView.setAdapter(libraryAdapter);
                }
                hideProgressBar();
            }
        });

        libraryViewModel.getErrorResponse().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Utils.showToast(s, LibraryActivity.this);
                hideProgressBar();
            }
        });
    }


    private void showFilterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LibraryActivity.this);
        builder.setTitle("Time");
        final View customLayout = LayoutInflater.from(LibraryActivity.this).inflate(R.layout.dialog_timefilter, null);
        EditText editText = customLayout.findViewById(R.id.etxt_dialog_filter);
        editText.setText(SpareTime + "");
        builder.setView(customLayout);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String spareTime = editText.getText().toString();
                if (!TextUtils.isEmpty(spareTime)) {
                    SpareTime = Integer.parseInt(spareTime);
                    libraryAdapter.setSpareTime(SpareTime);
                }
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
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
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
