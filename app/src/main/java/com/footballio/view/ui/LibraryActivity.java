package com.footballio.view.ui;

import android.os.Bundle;

import com.footballio.R;
import com.footballio.view.adapter.OlympicAdapter;
import com.footballio.view.adapter.TrainingAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OlympicActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private OlympicAdapter olympicAdapter;
    private GridLayoutManager manager;
    private List<String> strings = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olympic);
        init();
    }

    private void init() {
        getList();
        recyclerView = findViewById(R.id.recycler_olympic);
        manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        olympicAdapter = new OlympicAdapter();
        olympicAdapter.setTrainingList(strings);
        recyclerView.setAdapter(olympicAdapter);
    }

    private void getList() {
        strings.add("Technik");
        strings.add("Ausdauer");
        strings.add("Kraft");
        strings.add("Mental");
        strings.add("Health");
        strings.add("Einstellung");
    }
}
