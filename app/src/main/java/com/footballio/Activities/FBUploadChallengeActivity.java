package com.footballio.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import com.footballio.R;
import com.footballio.Utils.APPConst;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class FBUploadChallengeActivity extends AppCompatActivity {
    private static final String TAG = FBUploadChallengeActivity.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 111;
    @BindView(R.id.fb_challenge_upload_back)
    ImageView fbChallengeUploadBack;
    @BindView(R.id.fb_workout_view_title)
    TextView fbWorkoutViewTitle;
    @BindView(R.id.fb_challenege_upload_flash)
    ImageView fbChallenegeUploadFlash;
    @BindView(R.id.fb_challenge_upload_camera_view)
    ImageView fbChallengeUploadCameraView;
    @BindView(R.id.btn_fb_challenge_upload_camera)
    ImageView btnFbChallengeUploadCamera;
    @BindView(R.id.btn_fb_challenge_upload_gallery)
    ImageView btnFbChallengeUploadGallery;
    @BindView(R.id.fb_challenege_upload_restore)
    ImageView fbChallenegeUploadRestore;

    private static final int CAMERA_REQUEST = 1888;
    private static final int VIDEO_REQUEST = 1998;
    private Activity mycontext;
    private Uri uri;
    private String pathToStoredVideo = "";
    private String cid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_challenge);
        ButterKnife.bind(this);
        getBundle();
        initUi();


    }

    private void getBundle() {
        Intent intent = getIntent();
        if (intent.getStringExtra(APPConst.CHANLLENGEID) != null) {
            cid = intent.getStringExtra(APPConst.CHANLLENGEID);

        }
    }

    private void initUi() {
        mycontext=this;
    }

    @OnClick({R.id.fb_challenge_upload_back,R.id.fb_challenege_upload_flash, R.id.btn_fb_challenge_upload_camera, R.id.btn_fb_challenge_upload_gallery,R.id.fb_challenege_upload_restore})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fb_challenge_upload_back:
                finish();
                break;
            case R.id.fb_challenege_upload_flash:
                break;
            case R.id.btn_fb_challenge_upload_camera:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{READ_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant
                        Log.d(TAG, "Recorded Video Path 1deined" );
                        return;
                    }
                }
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_VIDEO_CAPTURE);
                if(cameraIntent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(cameraIntent, VIDEO_REQUEST);
                }
                break;
            case R.id.btn_fb_challenge_upload_gallery:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{READ_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant
                        Log.d(TAG, "Recorded Video Path 1deined" );
                        return;
                    }
                }
                Intent selectfile = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(selectfile, VIDEO_REQUEST);
               /* Intent i = new Intent(mycontext, FBUploadSelectionActivity.class);
                startActivity(i);*/
                break;
            case R.id.fb_challenege_upload_restore:
                break;
        }
    }

    private String getFileDestinationPath(){
        String generatedFilename = String.valueOf(System.currentTimeMillis());
        String filePathEnvironment = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        File directoryFolder = new File(filePathEnvironment + "/video/");
        if(!directoryFolder.exists()){
            directoryFolder.mkdir();
        }
        Log.d(TAG, "Full path " + filePathEnvironment + "/video/" + generatedFilename + ".mp4");
        return filePathEnvironment + "/video/" + generatedFilename + ".mp4";
    }
    /*https://inducesmile.com/android/android-record-and-upload-video-to-server-using-retrofit-2/*/
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            fbChallengeUploadCameraView.setImageBitmap(photo);
        }

        if(resultCode == Activity.RESULT_OK && requestCode == VIDEO_REQUEST){
            uri = data.getData();
            Log.d(TAG, "Recorded Video Path " + uri);

            pathToStoredVideo = getRealPathFromURIPath(uri, mycontext);
            Intent intent = new Intent(mycontext, FBUploadSelectionActivity.class);
            intent.putExtra(APPConst.VIDEOPATH,pathToStoredVideo);
            intent.putExtra(APPConst.VIDEOURI, uri.toString());
            intent.putExtra(APPConst.CHANLLENGEID, cid);
            startActivity(intent);
            finish();
                Log.d(TAG, "Recorded Video Path " + pathToStoredVideo);
                //Store the video to your server
               // uploadVideoToServer(pathToStoredVideo);


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! do the
                    // calendar task you need to do.
                    pathToStoredVideo = getRealPathFromURIPath(uri, mycontext);
                    Log.d(TAG, "Recorded Video Path 1" + pathToStoredVideo);

                } else {
                    ActivityCompat.requestPermissions(mycontext, new String[]{READ_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                    Log.d(TAG, "Recorded Video Path deined" );
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'switch' lines to check for other
            // permissions this app might request
        }
    }
    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {

        try {
            Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
            if (cursor == null) {
                return contentURI.getPath();
            } else {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                return cursor.getString(idx);
            }
        }catch (Exception e){
            Log.d(TAG, "getRealPathFromURIPath: "+e.getMessage().toString());
        }
        return "";
    }

    }
