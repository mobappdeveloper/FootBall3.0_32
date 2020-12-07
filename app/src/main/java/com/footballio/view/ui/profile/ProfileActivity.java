package com.footballio.view.ui.profile;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.footballio.BuildConfig;
import com.footballio.R;
import com.footballio.UserAccountActivity;
import com.footballio.Utils.Utils;
import com.footballio.model.dashboard.MyResponse;
import com.footballio.model.login.User;
import com.footballio.view.callback.IProgressBar;
import com.footballio.viewmodel.ProfileViewModel;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.internal.Util;

import static android.os.Environment.DIRECTORY_PICTURES;
import static android.os.Environment.getExternalStoragePublicDirectory;

@AndroidEntryPoint
public class ProfileActivity extends AppCompatActivity implements IProgressBar {
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    private static final int PICK_PHOTO_CODE = 200;
    private EditText profile_name;
    private ImageView img_profilePicture, img_editProfile, img_back, img_settings;
    private TextView txt_profile;
    private static final int REQUEST_IMAGE_CAPTURE = 300;
    private String currentPhotoPath;
    final CharSequence[] profileChangeOption = {"Neues Profilbild aufnehmen", "Aus Mediathek hochladen", "Cancel"};
    boolean count[] = new boolean[profileChangeOption.length];
    private ProfileViewModel profileModel;
    private AppCompatEditText profile_Name, profile_Dob, profile_Height, profile_Weight, profile_Position, profile_FavClub, profile_Favourite_Player;
    private ProgressBar progressBar;
    private Uri mediaFileUri = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();

        img_profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        txt_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ProfileActivity.this, UserAccountActivity.class));

            }
        });
        img_editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //:todo deny permission case has to tested and mutliple screen test
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ProfileActivity.this,
                                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_CAMERA);

                    } else {
                        profileOptionAlertDialog();
                    }
                } else {
                    profileOptionAlertDialog();
                }
//                profile_Name.setFocusable(true);
//                profile_Dob.setFocusable(true);
//                profile_Height.setFocusable(true);
//                profile_Weight.setFocusable(true);
//                profile_Position.setFocusable(true);
//                profile_FavClub.setFocusable(true);
//                profile_Favourite_Player.setFocusable(true);
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        img_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    private void init() {
        profile_name = findViewById(R.id.editext_profile_name);
        profile_name.setBackground(null);
        img_profilePicture = findViewById(R.id.img_profilePicture);
        img_editProfile = findViewById(R.id.img_edit_profile);
        img_settings = findViewById(R.id.img_profile_settings);
        img_back = findViewById(R.id.img_profile_back);
        txt_profile = findViewById(R.id.txt_profile);
        // user info
        profile_Name = findViewById(R.id.editext_profile_name);
        profile_Dob = findViewById(R.id.editext_profile_dob);
        profile_Height = findViewById(R.id.etxt_profile_height);
        profile_Weight = findViewById(R.id.etxt_profile_weight);
        profile_Position = findViewById(R.id.etxt_profile_positionValue);
        profile_FavClub = findViewById(R.id.etxt_profile_favClubValue);
        profile_Favourite_Player = findViewById(R.id.etxt_profile_favourite_playerValue);
        profileModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        getUserPersonalInfo();
    }

    private void getUserPersonalInfo() {
        showProgressBar();
        profileModel.getUserInfo().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                updateUserInfo(user);
            }
        });

        profileModel.getErrorResponse().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Utils.showToast(s, ProfileActivity.this);
                hideProgressBar();
            }
        });
    }

    private void updateUserInfo(User user) {
        Glide.with(img_profilePicture)
                .load(user.getPhoto())
                .placeholder(R.drawable.profile_header)
                .error(R.drawable.profile_header)
                .fallback(R.drawable.profile_header)
                .into(img_profilePicture);
        profile_Name.setText(user.getFirstName());
        profile_Dob.setText(user.getDateofBirth());
        profile_Height.setText(user.getHeight());
        profile_Weight.setText(user.getWeight());
        profile_Position.setText(user.getPosition());
        profile_FavClub.setText(user.getClub());
        profile_Favourite_Player.setText(user.getFavourite_player());
        profileModel.createUserInfoSession(user);
        hideProgressBar();
    }


    private void profileOptionAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Choose to Update Profile Pic");
        builder.setItems(profileChangeOption, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        try {
                            dispatchTakePictureIntent();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 1:
                        onPickPhotoFromGallery();
                        break;
                    case 2:
                        dialog.dismiss();
                        break;
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void onPickPhotoFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_PHOTO_CODE);

    }

    private void dispatchTakePictureIntent() throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
                Log.d("ProfileActivity", photoFile + "");
            } catch (ActivityNotFoundException | IOException e) {
                Utils.showToast(e.getMessage().toString(), this);
            }
            if (photoFile != null) {
//                Uri photoURI = FileProvider.getUriForFile(this,
//                        "com.footballio.android.fileprovider",
//                        photoFile);
                try {
                    //Uri outputFileUri = FileProvider.getUriForFile(ProfileActivity.this, BuildConfig.APPLICATION_ID, photoFile);
                    mediaFileUri = FileProvider.getUriForFile(ProfileActivity.this, "com.footballio.android.fileprovider", photoFile);
                    //takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mediaFileUri);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                } catch (Exception e) {
                    String mm = e.getMessage();
                }

            } else {
                Utils.showToast("Invalid File Path", this);
            }

        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "FOOTBALL_" + timeStamp + "_";
        //:todo if(api <=29) // ask read and write permission   if >29 store image in MediaStore.Images table and permission is not required
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        //File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private File notifyGalleryApp() throws IOException {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
        return f;

    }

//    public Bitmap loadFromUri(Uri photoUri) {
//        Bitmap image = null;
//        try {
//            // check version of Android on device
//            if (Build.VERSION.SDK_INT > 27) {
//                // on newer versions of Android, use the new decodeBitmap method
//                ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), photoUri);
//                image = ImageDecoder.decodeBitmap(source);
//            } else {
//                // support older versions of Android by using getBitmap
//                image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return image;
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {
                mediaFileUri = Uri.fromFile(notifyGalleryApp());
                startCropImageActivity(mediaFileUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICK_PHOTO_CODE && resultCode == RESULT_OK) {
            mediaFileUri = data.getData();
            startCropImageActivity(mediaFileUri);
            //todo: check uri less than 27 api level devices
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                uploadToServer(result.getUri());
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Utils.showToast(error + "", ProfileActivity.this);
            } else {
                Utils.showToast("Error", ProfileActivity.this);
            }
        }

    }

    private void uploadToServer(Uri croppedResultUri) {
        try {
            showProgressBar();
            profileModel.uploadProfilePicToServer(mediaFileUri, croppedResultUri).observe(this, new Observer<MyResponse>() {
                @Override
                public void onChanged(MyResponse myResponse) {
                    Glide.with(img_profilePicture)
                            .load(croppedResultUri)
                            .placeholder(R.drawable.profile_header)
                            .error(R.drawable.profile_header)
                            .fallback(R.drawable.profile_header)
                            .into(img_profilePicture);
                    hideProgressBar();
                }
            });
        } catch (Exception e) {
            hideProgressBar();
            Utils.showToast("Error updating profile Picture Please Retry", ProfileActivity.this);
        }

    }

    private void startCropImageActivity(Uri contentUri) {
        CropImage.activity(contentUri)
                .start(this);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            break;
                        }
                    }
                    profileOptionAlertDialog();
                }
                return;
            }
        }
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}
