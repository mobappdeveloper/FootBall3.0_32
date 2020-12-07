package com.footballio.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bruce.pickerview.LoopScrollListener;
import com.bruce.pickerview.LoopView;
import com.footballio.R;
import com.footballio.Utils.AppConst;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WeightActivity extends AppCompatActivity {
    private Button button_next;
    private LoopView loopView_height, loopView_measurement;
    private int weight = 2, measurementType = 0;
    private List<String> list_values = new ArrayList<>();
    private List<String> list_measurement = new ArrayList<>();
    private Bundle bundle;
    private ImageView imageView_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
        init();
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String weightMeasurement = list_measurement.get(measurementType);
                String weightValue = list_values.get(weight);
                startActivity(new Intent(WeightActivity.this, ClubDetailsActivity.class)
                        .putExtra(AppConst.W, weightValue + weightMeasurement)
                        .putExtra(AppConst.G, bundle.getString(AppConst.G))
                        .putExtra(AppConst.DOB, bundle.getString(AppConst.DOB))
                        .putExtra(AppConst.H, bundle.getString(AppConst.H))
                        .putExtra(AppConst.LOGIN_BUNDLE, bundle.getBundle(AppConst.LOGIN_BUNDLE)));
            }
        });

        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        loopView_height.setLoopListener(new LoopScrollListener() {
            @Override
            public void onItemSelect(int item) {
                weight = item;
                //loopView_height.setDataList(getValues());
            }
        });
        loopView_measurement.setLoopListener(new LoopScrollListener() {
            @Override
            public void onItemSelect(int item) {
                measurementType = item;
            }
        });

    }

    private void init() {
        button_next = findViewById(R.id.butt_weight_next);
        bundle = getIntent().getExtras();
        imageView_back = findViewById(R.id.img_back_weight);
        loopView_height = (LoopView) findViewById(R.id.loop_view_weight);
        loopView_height.setInitPosition(2);
        loopView_height.setCanLoop(false);
        loopView_height.setTextSize(28);//must be called before setDateList
        loopView_height.setDataList(getValues());
        loopView_measurement = (LoopView) findViewById(R.id.loop_view_weight_measurement);
        loopView_measurement.setInitPosition(0);
        loopView_measurement.setCanLoop(false);
        loopView_measurement.setTextSize(20);//must be called before setDateList
        loopView_measurement.setDataList(getMeasurementList());
    }

    private List<String> getValues() {
        if (list_values.size() == 0) {
            for (int i = 40; i <= 200; i++) {
                list_values.add(i + "");
            }
        }
        return list_values;
    }

    private List<String> getMeasurementList() {

        if (list_measurement.size() == 0) {
            list_measurement.add("kg");
            list_measurement.add("lbs");
        }
        return list_measurement;
    }
}