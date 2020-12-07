package com.footballio.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.footballio.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GenderActivity extends AppCompatActivity {
    private Button button_next;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);
        button_next = findViewById(R.id.butt_gender_next);
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GenderActivity.this, DOBActivity.class));
            }
        });
    }
}
