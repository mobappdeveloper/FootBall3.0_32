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
public class DOBActivity extends AppCompatActivity {
    private Button button_next;
    private LoopView loopView_date, loopView_month, loopView_year;
    private List<String> day_list = new ArrayList<>();
    private List<String> month_list = new ArrayList<>();
    private List<String> year_list = new ArrayList<>();
    private int day, month, year;
    private Bundle bundle;
    private ImageView imageView_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_o_b);
        init();
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dob = day_list.get(day).toString() + "-" + month_list.get(month).toString() + "-" + year_list.get(year).toString();
                startActivity(new Intent(DOBActivity.this, HeightActivity.class).putExtra(AppConst.DOB, dob).putExtra(AppConst.G, bundle.getString(AppConst.G)).putExtra(AppConst.LOGIN_BUNDLE, bundle.getBundle(AppConst.LOGIN_BUNDLE)));
            }
        });

        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });


        loopView_date.setLoopListener(new LoopScrollListener() {
            @Override
            public void onItemSelect(int item) {
                day = item;
            }
        });


        loopView_month.setLoopListener(new LoopScrollListener() {
            @Override
            public void onItemSelect(int item) {
                month = item;
            }
        });


        loopView_year.setLoopListener(new LoopScrollListener() {
            @Override
            public void onItemSelect(int item) {
                year = item;
            }
        });

    }

    private void init() {
        bundle = getIntent().getExtras();
        button_next = findViewById(R.id.butt_dob_next);
        imageView_back = findViewById(R.id.img_back_dob);
        loopView_date = (LoopView) findViewById(R.id.loop_view_date);
        loopView_date.setInitPosition(2);
        loopView_date.setCanLoop(false);
        loopView_date.setTextSize(25);//must be called before setDateList
        loopView_date.setDataList(getDayList());
        loopView_month = (LoopView) findViewById(R.id.loop_view_month);
        loopView_month.setInitPosition(2);
        loopView_month.setCanLoop(false);
        loopView_month.setTextSize(25);//must be called before setDateList
        loopView_month.setDataList(getMonthList());
        loopView_year = (LoopView) findViewById(R.id.loop_view_year);
        loopView_year.setInitPosition(2);
        loopView_year.setCanLoop(false);
        loopView_year.setTextSize(25);//must be called before setDateList
        loopView_year.setDataList(getYearList());
    }

    private List<String> getDayList() {

        for (int i = 1; i <= 31; i++) {
            day_list.add(i + "");
        }
        return day_list;

    }

    private List<String> getMonthList() {
        month_list.add("JAN");
        month_list.add("FEB");
        month_list.add("MAR");
        month_list.add("APR");
        month_list.add("MAY");
        month_list.add("JUN");
        month_list.add("JUL");
        month_list.add("AUG");
        month_list.add("SEP");
        month_list.add("OCT");
        month_list.add("NOV");
        month_list.add("DEC");
        return month_list;

    }

    private List<String> getYearList() {
        int currentYear = Utils.getCurrentYear();
        for (int i = 1; i <= 40; i++) {
            year_list.add(currentYear + "");
            --currentYear;
        }
        return year_list;

    }
    // todo this flow of activity has to be changed to fragments
}