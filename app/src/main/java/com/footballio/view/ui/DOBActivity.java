package com.footballio.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.bruce.pickerview.LoopScrollListener;
import com.bruce.pickerview.LoopView;
import com.footballio.R;
import com.footballio.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class DOBActivity extends AppCompatActivity {
    private Button button_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_o_b);
        button_next = findViewById(R.id.butt_dob_next);

        LoopView loopView = (LoopView) findViewById(R.id.loop_view);
        loopView.setInitPosition(2);
        loopView.setCanLoop(false);
        loopView.setLoopListener(new LoopScrollListener() {
            @Override
            public void onItemSelect(int item) {
                Utils.showToast(item + "", DOBActivity.this);

            }
        });
        loopView.setTextSize(25);//must be called before setDateList
        loopView.setDataList(getList());


        LoopView loopView1 = (LoopView) findViewById(R.id.loop_view1);
        loopView1.setInitPosition(2);
        loopView1.setCanLoop(false);
        loopView1.setLoopListener(new LoopScrollListener() {
            @Override
            public void onItemSelect(int item) {
                Utils.showToast(item + "", DOBActivity.this);
            }
        });
        loopView1.setTextSize(25);//must be called before setDateList
        loopView1.setDataList(getList1());


        LoopView loopView2 = (LoopView) findViewById(R.id.loop_view2);
        loopView2.setInitPosition(2);
        loopView2.setCanLoop(false);
        loopView2.setLoopListener(new LoopScrollListener() {
            @Override
            public void onItemSelect(int item) {
                Utils.showToast(item + "", DOBActivity.this);
            }
        });
        loopView2.setTextSize(25);//must be called before setDateList
        loopView2.setDataList(getList2());
    }

    private List<String> getList() {
        List<String> ll = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            ll.add(i + "");
        }
        return ll;

    }

    private List<String> getList1() {
        List<String> ll = new ArrayList<>();
        ll.add("JAN");
        ll.add("FEB");
        ll.add("MAR");
        ll.add("APR");
        ll.add("MAY");
        ll.add("JUN");
        ll.add("JUL");
        ll.add("AUG");
        ll.add("SEP");
        ll.add("OCT");
        ll.add("NOV");
        ll.add("DEC");

        return ll;

    }

    private List<String> getList2() {
        List<String> ll = new ArrayList<>();
        for (int i = 1960; i <= 2020; i++) {
            ll.add(i + "");
        }

        return ll;

    }
}