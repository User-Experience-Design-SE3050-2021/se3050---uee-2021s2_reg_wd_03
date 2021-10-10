package com.ads.lk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Changepw2Activity extends AppCompatActivity {
    Button back;
    ImageView profileIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepw2);
        back=findViewById(R.id.back);
        profileIcon=findViewById(R.id.profileIcon);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Changepw2Activity.this, ProfileActivity.class));

            }
        });
        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Changepw2Activity.this, ProfileActivity.class));
            }
        });
    }
}