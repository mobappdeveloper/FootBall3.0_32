package com.footballio.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.footballio.R;
import com.footballio.Utils.AppConst;
import com.footballio.Utils.Utils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class GenderActivity extends AppCompatActivity {
    private Button button_next;
    private ImageView img_male, img_female, img_other, img_check_male, img_check_female, img_check_other;
    private String gender="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);
        init();
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!gender.isEmpty())
                {
                    Bundle bundle = getIntent().getExtras();
                    if (bundle != null) {
                        startActivity(new Intent(GenderActivity.this, DOBActivity.class)
                                .putExtra(AppConst.G, gender)
                                .putExtra(AppConst.LOGIN_BUNDLE, bundle.getBundle(AppConst.LOGIN_BUNDLE)));
                    } else {
                        Utils.showToast("Error", GenderActivity.this);
                    }

                }
                else
                {
                    Utils.showToast("Choose Gender", GenderActivity.this);
                }

            }
        });

        img_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = getResources().getString(R.string.txt_male);
                img_male.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.bright_turquoise));
                img_check_male.setColorFilter(ContextCompat.getColor(v.getContext(), R.color.bright_turquoise));

                img_female.setColorFilter(ContextCompat.getColor(v.getContext(), R.color.doveGray));
                img_other.setColorFilter(ContextCompat.getColor(v.getContext(), R.color.doveGray));
                img_check_female.setColorFilter(ContextCompat.getColor(v.getContext(), R.color.doveGray));
                img_check_other.setColorFilter(ContextCompat.getColor(v.getContext(), R.color.doveGray));

            }

        });

        img_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = getResources().getString(R.string.txt_female);
                img_female.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.bright_turquoise));
                img_check_female.setColorFilter(ContextCompat.getColor(v.getContext(), R.color.bright_turquoise));


                img_male.setColorFilter(ContextCompat.getColor(v.getContext(), R.color.doveGray));
                img_other.setColorFilter(ContextCompat.getColor(v.getContext(), R.color.doveGray));
                img_check_male.setColorFilter(ContextCompat.getColor(v.getContext(), R.color.doveGray));
                img_check_other.setColorFilter(ContextCompat.getColor(v.getContext(), R.color.doveGray));
            }
        });

        img_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = getResources().getString(R.string.txt_other);
                img_other.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.bright_turquoise));
                img_check_other.setColorFilter(ContextCompat.getColor(v.getContext(), R.color.bright_turquoise));

                img_male.setColorFilter(ContextCompat.getColor(v.getContext(), R.color.doveGray));
                img_female.setColorFilter(ContextCompat.getColor(v.getContext(), R.color.doveGray));
                img_check_male.setColorFilter(ContextCompat.getColor(v.getContext(), R.color.doveGray));
                img_check_female.setColorFilter(ContextCompat.getColor(v.getContext(), R.color.doveGray));

            }
        });


    }

    private void init() {
        button_next = findViewById(R.id.butt_gender_next);
        img_male = findViewById(R.id.img_gender_male);
        img_female = findViewById(R.id.img_gender_female);
        img_other = findViewById(R.id.img_gender_other);
        img_check_male = findViewById(R.id.img_gender_check_male);
        img_check_female = findViewById(R.id.img_gender_check_female);
        img_check_other = findViewById(R.id.img_gender_check_other);
    }
}
