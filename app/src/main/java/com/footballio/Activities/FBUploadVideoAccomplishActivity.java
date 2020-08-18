package com.footballio.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.bumptech.glide.request.RequestOptions;
import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.Utils.Loader;
import com.footballio.Utils.Prefs;
import com.footballio.model.ResultObject;
import com.footballio.model.chanllenge.VideoUploadResponse;
import com.footballio.retrofit.ApiClient;
import com.footballio.retrofit.ApiInterface;
import com.google.gson.Gson;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.footballio.retrofit.ApiClient.BASE_URL_VERSION;

public class FBUploadVideoAccomplishActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = FBUploadVideoAccomplishActivity.class.getSimpleName();
    @BindView(R.id.btn_fb_upload_accompolished_finish)
    Button btnFbUploadAccompolishedFinish;

    private Context mycontext;
    private Button btn_accomplished;
    private String cid = "";
    private String videopath = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_accomplished);
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
            Log.d(TAG, "getBundle: "+videopath);
        }

    }

    private void initUi() {
        mycontext = this;

      //  btn_accomplished = findViewById(R.id.btn_fb_upload_accompolished_finish);

      //  btn_accomplished.setOnClickListener(this);
    }



    @OnClick(R.id.btn_fb_upload_accompolished_finish)
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_fb_upload_accompolished_finish:
                uploadVideoToServer(videopath,cid);
                break;
        }


    }

    private void uploadVideoToServer(String pathToVideoFile,String Chanllengeid1){
        File videoFile = new File(pathToVideoFile);
        RequestBody videoBody = RequestBody.create(MediaType.parse("video/*"), videoFile);
        MultipartBody.Part vFile = MultipartBody.Part.createFormData("video", videoFile.getName(), videoBody);
        RequestBody userid = RequestBody.create(
                MediaType.parse("text/plain"),
                Prefs.getString(APPConst.TOKEN,""));
        RequestBody Chanllengeid = RequestBody.create(
                MediaType.parse("text/plain"),
                Chanllengeid1);

        Loader.showLoad(mycontext,true);
        final Call<VideoUploadResponse> profileImageResponseCall;
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        String BASE_Image_URL = BASE_URL_VERSION+"challenges/uploadchallange.php";
        profileImageResponseCall = apiService.uploadVideo(BASE_Image_URL,vFile,userid ,Chanllengeid);
        profileImageResponseCall.enqueue(new Callback<VideoUploadResponse>() {
            @Override
            public void onResponse(@NonNull Call<VideoUploadResponse> call, @NonNull Response<VideoUploadResponse> response) {

                Log.d(TAG, "Got Response"+new Gson().toJson(response.body()));
                if (response.isSuccessful()) {

                    RequestOptions options;
                    options = new RequestOptions();
                    options.fitCenter();
            //        Log.d(TAG, "onResponse: profie_image"+response.body().getImage_path());

                /*    Glide.with(AIRProfileActivity.this)
                            .load(response.body().getImage_path() + response.body().getNew_file_name())
                            .apply(options)
                            .into(myPrifileIMG);*/
                if (response.body().getStatus().equals("success")) {

                    Intent intent = new Intent(mycontext, FBChallengeFullViewActivity.class);
                    intent.putExtra(APPConst.CHANLLENGEID,cid);
                   // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    Toast.makeText(mycontext, "Video Upload successfully!!!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(mycontext, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
                    //  recreate();
                }else {
                    Log.d(TAG, "onResponse: failed to upload");
                  //  Toast.makeText(ATProfileActivity.this, response.body().getAlert(), Toast.LENGTH_SHORT).show();

                }
                Loader.showLoad(mycontext,false);

            }

            @Override
            public void onFailure(@NonNull Call<VideoUploadResponse> call, @NonNull Throwable t) {
                Log.e(TAG, "Got Failure Response:" + t.getMessage());
                Loader.showLoad(mycontext,false);

            }
        });
    }

}
