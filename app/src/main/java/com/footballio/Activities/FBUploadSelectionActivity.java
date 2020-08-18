package com.footballio.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.footballio.R;
import com.footballio.Utils.APPConst;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FBUploadSelectionActivity extends AppCompatActivity {

    private static final String TAG = FBUploadSelectionActivity.class.getSimpleName();
    @BindView(R.id.fb_upload_video_back)
    ImageView fbUploadVideoBack;
    @BindView(R.id.fb_workout_view_title)
    TextView fbWorkoutViewTitle;

    @BindView(R.id.btn_fb_video_workout)
    Button btnFbVideoWorkout;
    @BindView(R.id.vendor_ads_image)
    VideoView vendorAdsImage;
    @BindView(R.id.delete_btn)
    ImageView deleteBtn;

    private Context mycontext;
    private String cid = "";
    private String videopath = "";
    private Uri videouri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_selection);
        ButterKnife.bind(this);

        initUi();
        getBundle();
    }

    private void getBundle() {
        Intent intent = getIntent();
        if (intent.getStringExtra(APPConst.CHANLLENGEID) != null) {
            cid = intent.getStringExtra(APPConst.CHANLLENGEID);

        }
        if (intent.getStringExtra(APPConst.VIDEOPATH) != null) {
            videopath = intent.getStringExtra(APPConst.VIDEOPATH);

        }
        if (intent.getStringExtra(APPConst.VIDEOURI) != null) {
            videouri = Uri.parse(intent.getStringExtra(APPConst.VIDEOURI));
            vendorAdsImage.setVideoURI(videouri);
            vendorAdsImage.start();

        }
    }

    private void initUi() {
        mycontext = this;
    }

    @OnClick({R.id.fb_upload_video_back, R.id.btn_fb_video_workout,R.id.delete_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fb_upload_video_back:
                Intent intent = new Intent(mycontext,FBUploadChallengeActivity.class);
                intent.putExtra(APPConst.CHANLLENGEID,cid);
                startActivity(intent);
                finish();
                break;
                case R.id.delete_btn:
                    Intent intent1 = new Intent(mycontext,FBUploadChallengeActivity.class);
                    intent1.putExtra(APPConst.CHANLLENGEID,cid);
                    startActivity(intent1);

                    finish();

                break;
            case R.id.btn_fb_video_workout:
                Intent intent2 = new Intent(mycontext, FBUploadVideoAccomplishActivity.class);
                intent2.putExtra(APPConst.VIDEOPATH, videopath);
                intent2.putExtra(APPConst.CHANLLENGEID, cid);
                startActivity(intent2);
                finish();
                break;
        }
    }


}
