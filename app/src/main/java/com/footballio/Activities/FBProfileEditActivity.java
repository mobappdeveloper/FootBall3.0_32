package com.footballio.Activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.footballio.R;
import com.footballio.Utils.APPConst;
import com.footballio.Utils.Loader;
import com.footballio.Utils.Prefs;
import com.footballio.model.profile.ProfileViewResponse;
import com.footballio.retrofit.ApiClient;
import com.footballio.retrofit.ApiInterface;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FBProfileEditActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = FBProfileEditActivity.class.getSimpleName();

    @BindView(R.id.fb_settings_profile_edit_image)
    ImageView fbSettingsProfileEditImage;
    @BindView(R.id.fb_settings_profile_edit_username)
    EditText fbSettingsProfileEditUsername;
    @BindView(R.id.fb_settings_profile_edit_first_name)
    EditText fbSettingsProfileEditFirstName;
    @BindView(R.id.fb_settings_profile_edit_last_name)
    EditText fbSettingsProfileEditLastName;
    @BindView(R.id.fb_settings_profile_edit_dob)
    EditText fbSettingsProfileEditDob;
    @BindView(R.id.fb_settings_profile_edit_email)
    EditText fbSettingsProfileEditEmail;
    @BindView(R.id.fb_settings_profile_edit_society)
    EditText fbSettingsProfileEditSociety;
    @BindView(R.id.fb_settings_profile_edit_position)
    EditText fbSettingsProfileEditPosition;
    @BindView(R.id.fb_settings_profile_edit_strng_foot)
    EditText fbSettingsProfileEditStrngFoot;
    @BindView(R.id.fb_settings_profile_edit_size)
    EditText fbSettingsProfileEditSize;
    @BindView(R.id.fb_settings_profile_edit_weight)
    EditText fbSettingsProfileEditWeight;
    @BindView(R.id.fb_settings_profile_edit_nationality)
    EditText fbSettingsProfileEditNationality;
    @BindView(R.id.fb_register_terms_conditions)
    CheckBox fbRegisterTermsConditions;
    @BindView(R.id.btn_fb_settings_profile_edit)
    Button btnFbSettingsProfileEdit;
    @BindView(R.id.fb_settings_profile_edit_back)
    LinearLayout fbSettingsProfileEditBack;

    private Context mycontext;
    private ProfileViewResponse profileViewResponse;
    private Calendar myCalendar;
    private ProfileViewResponse profileUpdateResponse;
    private String firstname = "", lastname = "", height = "", weight = "", dob = "", club = "", nationality = "", position = "", username = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        ButterKnife.bind(this);

        initUi();
    }

    private void initUi() {
        mycontext = this;
        myCalendar = Calendar.getInstance();
        dashboard_category();
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            Log.d(TAG, "onDateSet: " + fbSettingsProfileEditDob.getText().toString());
            // TODO Auto-generated method stub
            if (fbSettingsProfileEditDob.getText().toString().isEmpty()) {
                Log.d(TAG, "onDateSet:data1 " + fbSettingsProfileEditDob.getText().toString());
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            } else {
                Log.d(TAG, "onDateSet:2 " + fbSettingsProfileEditDob.getText().toString());
                String[] str = fbSettingsProfileEditDob.getText().toString().trim().split("/");
                myCalendar.set(Calendar.YEAR, Integer.parseInt(str[2]));
                myCalendar.set(Calendar.MONTH, Integer.parseInt(str[1]));
                myCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(str[0]));

            }
            updateLabel();
        }

    };

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        fbSettingsProfileEditDob.setText(sdf.format(myCalendar.getTime()));
    }

    @OnClick({R.id.fb_settings_profile_edit_back, R.id.btn_fb_settings_profile_edit, R.id.fb_settings_profile_edit_dob})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fb_settings_profile_edit_back:
                finish();
                break;
            case R.id.btn_fb_settings_profile_edit:
                validate();
                break;
            case R.id.fb_settings_profile_edit_dob:
                if (fbSettingsProfileEditDob.getText().toString().isEmpty()) {
                    Log.d(TAG, "onDateSet:data1 " + fbSettingsProfileEditDob.getText().toString());
                    new DatePickerDialog(mycontext, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                } else {
                    Log.d(TAG, "onDateSet:2 " + fbSettingsProfileEditDob.getText().toString());
                    String[] str = fbSettingsProfileEditDob.getText().toString().trim().split("/");
                    Log.d(TAG, "onDateSet:str " + str.length);
                    for (int i = 0; i < str.length; i++) {
                        Log.d(TAG, "onDateSet: " + str[i]);
                    }
                    new DatePickerDialog(mycontext, date, myCalendar
                            .get(Integer.parseInt(str[2])), myCalendar.get(Integer.parseInt(str[1])),
                            myCalendar.get(Integer.parseInt(str[0]))).show();

                }
                break;
        }
    }

    private void validate() {
        firstname = fbSettingsProfileEditFirstName.getText().toString();
        lastname = fbSettingsProfileEditLastName.getText().toString();
        height = fbSettingsProfileEditStrngFoot.getText().toString();
        weight = fbSettingsProfileEditWeight.getText().toString();
        dob = fbSettingsProfileEditDob.getText().toString();
        club = fbSettingsProfileEditSociety.getText().toString();
        nationality = fbSettingsProfileEditNationality.getText().toString();
        position = fbSettingsProfileEditPosition.getText().toString();
        username = fbSettingsProfileEditUsername.getText().toString();
        if (username.isEmpty()) {
            Toast.makeText(mycontext, "Please enter username", Toast.LENGTH_SHORT).show();
        } else if (firstname.isEmpty()) {
            Toast.makeText(mycontext, "Please enter firstname", Toast.LENGTH_SHORT).show();
        } else if (lastname.isEmpty()) {
            Toast.makeText(mycontext, "Please enter lastname", Toast.LENGTH_SHORT).show();
        } else if (dob.isEmpty()) {
            Toast.makeText(mycontext, "Please enter DOB", Toast.LENGTH_SHORT).show();
        } else if (club.isEmpty()) {
            Toast.makeText(mycontext, "Please enter club", Toast.LENGTH_SHORT).show();
        } else if (position.isEmpty()) {
            Toast.makeText(mycontext, "Please enter position", Toast.LENGTH_SHORT).show();
        } else if (height.isEmpty()) {
            Toast.makeText(mycontext, "Please enter height", Toast.LENGTH_SHORT).show();
        } else if (weight.isEmpty()) {
            Toast.makeText(mycontext, "Please enter weight", Toast.LENGTH_SHORT).show();
        } else if (nationality.isEmpty()) {
            Toast.makeText(mycontext, "Please enter nationality", Toast.LENGTH_SHORT).show();
        } else {
            dashboard_category(firstname, lastname, height, weight, dob, club, nationality, position, username);
        }
    }

    Call<ProfileViewResponse> profileViewResponseCall;

    private void dashboard_category() {

        Loader.showLoad(mycontext, true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        profileViewResponseCall = apiService.getProfileView(Prefs.getString(APPConst.TOKEN, ""));


        profileViewResponseCall.enqueue(new Callback<ProfileViewResponse>() {
            @Override
            public void onResponse(Call<ProfileViewResponse> call, Response<ProfileViewResponse> response) {
                Log.d(TAG, "onResponse:ProfileViewResponse " + new Gson().toJson(response.body()));
                profileViewResponse = response.body();

                fbSettingsProfileEditUsername.setText(profileViewResponse.getUserName());
                fbSettingsProfileEditFirstName.setText(profileViewResponse.getFirstName());
                fbSettingsProfileEditLastName.setText(profileViewResponse.getLastName());
                fbSettingsProfileEditDob.setText(profileViewResponse.getDateofBirth());
                fbSettingsProfileEditEmail.setText(profileViewResponse.getEmail());
                fbSettingsProfileEditSociety.setText(profileViewResponse.getClub());
                fbSettingsProfileEditPosition.setText(profileViewResponse.getPosition());
                fbSettingsProfileEditStrngFoot.setText(profileViewResponse.getHeight());
                fbSettingsProfileEditSize.setText(profileViewResponse.getSize());
                fbSettingsProfileEditWeight.setText(profileViewResponse.getSize());
                fbSettingsProfileEditNationality.setText(profileViewResponse.getNationality());

                Glide.with(mycontext)
                        .load(profileViewResponse.getPhoto())
                        .placeholder(R.drawable.ic_profile_img)
                        .error(R.drawable.ic_profile_img)
                        .into(fbSettingsProfileEditImage);

                Loader.showLoad(mycontext, false);
            }

            @Override
            public void onFailure(Call<ProfileViewResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: loginUser" + t.getMessage().toString());
                Loader.showLoad(mycontext, false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });

    }


    Call<ProfileViewResponse> profileUpdateResponseCall;

    private void dashboard_category(String firstname, String lastname, String height, String weight, String dob, String club,
                                    String nationality, String position, String username) {

        Loader.showLoad(mycontext, true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //   int text = spinner_itemtype.getSelectedItemPosition()+1;
        profileUpdateResponseCall = apiService.getProfileUpdate(Prefs.getString(APPConst.TOKEN, ""), firstname, lastname,
                height, weight, dob, club, nationality, position, username);


        profileUpdateResponseCall.enqueue(new Callback<ProfileViewResponse>() {
            @Override
            public void onResponse(Call<ProfileViewResponse> call, Response<ProfileViewResponse> response) {
                Log.d(TAG, "onResponse:ProfileViewResponse " + new Gson().toJson(response.body()));
                profileUpdateResponse = response.body();
                finish();
               /* if (response.body().isStatus()) {
                    Toast.makeText(mycontext, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(mycontext, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }*/

                Loader.showLoad(mycontext, false);
            }

            @Override
            public void onFailure(Call<ProfileViewResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: loginUser" + t.getMessage().toString());
                Loader.showLoad(mycontext, false);
                Toast.makeText(mycontext, "Please check with server", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
