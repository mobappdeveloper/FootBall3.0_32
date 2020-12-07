package com.footballio.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.footballio.R;
import com.footballio.Utils.AppConst;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ClubDetailsActivity extends AppCompatActivity {
    private Button button_next;
    private EditText etxt_society, etxt_position, etxt_favouritPlayer, etxt_club;
    private RadioButton rbutton_doIplayforClub;
    private Bundle bundle;
    private boolean isChecked = false;
    private ImageView imageView_back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_details);
        init();
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle_up = getClubDetails();
                startActivity(new Intent(ClubDetailsActivity.this, ChooseTrainingActivity.class)
                        .putExtra(AppConst.G, bundle.getString(AppConst.G))
                        .putExtra(AppConst.H, bundle.getString(AppConst.H))
                        .putExtra(AppConst.W, bundle.getString(AppConst.W))
                        .putExtra(AppConst.DOB,bundle.getString(AppConst.DOB))
                        .putExtra(AppConst.LOGIN_BUNDLE,bundle.getBundle(AppConst.LOGIN_BUNDLE))
                        .putExtra(AppConst.CLUB_BUNDLE, bundle_up));
            }
        });
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rbutton_doIplayforClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isChecked) {
                    rbutton_doIplayforClub.setButtonDrawable(R.drawable.ic_checkbox);
                    isChecked = true;

                } else {
                    rbutton_doIplayforClub.setButtonDrawable(R.drawable.ic_check_square);
                    isChecked = false;
                }

            }
        });


    }

    private Bundle getClubDetails() {
        Bundle bundle = new Bundle();
        String society = etxt_society.getText().toString();
        String position = etxt_position.getText().toString();
        String favouritePlayer = etxt_favouritPlayer.getText().toString();
        String club = etxt_club.getText().toString();

        bundle.putString(AppConst.SOC, society);
        bundle.putString(AppConst.POS, position);
        bundle.putString(AppConst.FP, favouritePlayer);
        bundle.putString(AppConst.NATIONALITY, club);
        bundle.putBoolean(AppConst.AUINC, isChecked);

        return bundle;
    }

    private void init() {
        button_next = findViewById(R.id.butt_club_next);
        etxt_society = findViewById(R.id.etxt_club_society);
        etxt_position = findViewById(R.id.etxt_club_position);
        etxt_favouritPlayer = findViewById(R.id.etxt_club_favouritePlayer);
        etxt_club = findViewById(R.id.etxt_club_clubname);
        rbutton_doIplayforClub = findViewById(R.id.rbutton_club_isClub);
        bundle = getIntent().getExtras();
        imageView_back = findViewById(R.id.img_back_cda);
    }
}
