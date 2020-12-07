package com.footballio.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import dagger.hilt.android.AndroidEntryPoint;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bruce.pickerview.LoopScrollListener;
import com.bruce.pickerview.LoopView;
import com.footballio.R;
import com.footballio.Utils.AppConst;
import com.footballio.Utils.Utils;

import java.util.ArrayList;
import java.util.List;
@AndroidEntryPoint
public class HeightActivity extends AppCompatActivity {
    private Button button_next;
    private LoopView loopView_height, loopView_measurement;
    private int heightValue, mesurementType;
    private List<String> list_Heightvalues = new ArrayList<>();
    private List<String> list_measurementType = new ArrayList<>();
    private Bundle bundle;
    private ImageView imageView_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_height);
        init();
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String height = list_Heightvalues.get(heightValue);
                String heightType = list_measurementType.get(mesurementType);
                startActivity(new Intent(HeightActivity.this, WeightActivity.class)
                        .putExtra(AppConst.H, height + heightType)
                        .putExtra(AppConst.G, bundle.getString(AppConst.G))
                        .putExtra(AppConst.DOB, bundle.getString(AppConst.DOB))
                        .putExtra(AppConst.LOGIN_BUNDLE, bundle.getBundle(AppConst.LOGIN_BUNDLE))
                );
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
                heightValue = item;
                Utils.showToast(item + "", HeightActivity.this);
                //loopView_height.setDataList(getHeightListinCM());
            }
        });

        loopView_measurement.setLoopListener(new LoopScrollListener() {
            @Override
            public void onItemSelect(int item) {
                Utils.showToast(item + "", HeightActivity.this);
                mesurementType = item;
                if (item == 0) {
                    loopView_height.setDataList(getHeightListInCM());
                    //loopView_measurement.setInitPosition(item);
                } else {
                    loopView_height.setDataList(getHeightListInIN());
                    //loopView_measurement.setInitPosition(item);
                }
            }
        });

    }

    private void init() {
        bundle = getIntent().getExtras();
        button_next = findViewById(R.id.butt_height_next);
        imageView_back = findViewById(R.id.img_back_height);
        loopView_height = (LoopView) findViewById(R.id.loop_view_height);
        loopView_height.setInitPosition(2);
        loopView_height.setCanLoop(false);
        loopView_height.setTextSize(27);//must be called before setDateList
        loopView_height.setDataList(getHeightListInCM());
        loopView_measurement = (LoopView) findViewById(R.id.loop_view_measurement);
        loopView_measurement.setInitPosition(0);
        loopView_measurement.setCanLoop(false);
        loopView_measurement.setTextSize(25);//must be called before setDateList
        loopView_measurement.setDataList(getMeasurementList());
    }

    private List<String> getHeightListInCM() {
        for (int i = 100; i <= 200; i++) {
            list_Heightvalues.add(i + "");
        }
        return list_Heightvalues;
    }

    private List<String> getHeightListInIN() {
        List<String> list_values = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            list_values.add(i + "");
        }
        return list_values;
    }

    private List<String> getMeasurementList() {
        list_measurementType.add("cm");
        list_measurementType.add("in");
        return list_measurementType;   //todo move this to view model
    }
}