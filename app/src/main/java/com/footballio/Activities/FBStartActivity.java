package com.footballio.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.footballio.R;
import com.footballio.Utils.APPConst;


public class FBStartActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = FBStartActivity.class.getSimpleName();

    private Context mycontext;
    private Button btn_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        initUi();
    }

    private void initUi() {
        mycontext=this;

        btn_start=findViewById(R.id.btn_fb_start);

        btn_start.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fb_start:
                Intent intent = new Intent(mycontext, FBDashboardActivity.class);
                intent.putExtra(APPConst.PWORKSTATUS,"0");
                startActivity(intent);
                break;
        }
        
    }
}
