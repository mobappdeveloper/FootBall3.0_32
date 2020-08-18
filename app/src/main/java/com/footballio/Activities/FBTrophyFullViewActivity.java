package com.footballio.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.footballio.R;
import com.footballio.Utils.APPConst;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FBTrophyFullViewActivity extends AppCompatActivity {
    private static final String TAG = FBTrophyFullViewActivity.class.getSimpleName();
    @BindView(R.id.fb_trophy_back)
    ImageView fbTrophyBack;
    @BindView(R.id.trophy_icon)
    ImageView trophyIcon;
    @BindView(R.id.trophy_name)
    TextView trophyName;
    @BindView(R.id.trophy_desc)
    TextView trophyDesc;

    private Context mycontext;
    private String tname = "";
    private String ticon = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trophy_fullview);
        ButterKnife.bind(this);

        initUi();
    }

    private void initUi() {
        mycontext = this;
        getBundle();
    }

    private void getBundle() {
        Intent intent = getIntent();
        if (intent.getStringExtra(APPConst.TROPHYNAME) != null) {

            tname = intent.getStringExtra(APPConst.TROPHYNAME);

        }
        if (intent.getStringExtra(APPConst.TROPHYICON) != null) {

            ticon = intent.getStringExtra(APPConst.TROPHYICON);

        }
        Glide.with(mycontext)
                .load(ticon)
                .placeholder(R.color.colorTransparent)
                .error(R.color.colorTransparent)
                .into(trophyIcon);
        trophyName.setText(tname);

    }

    @OnClick(R.id.fb_trophy_back)
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.fb_trophy_back:
                finish();
                break;
        }
    }
}
