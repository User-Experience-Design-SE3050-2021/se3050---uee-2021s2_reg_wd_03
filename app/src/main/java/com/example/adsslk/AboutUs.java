package com.example.adsslk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class AboutUs extends AppCompatActivity {
   Button contactUs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        contactUs=(Button)findViewById(R.id.btn_contactUs) ;

        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ContactUs.class));
            }
        });
    }

    // 3 dot menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.three_dot_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.about_us:
                startActivity(new Intent(getApplicationContext(),AboutUs.class));
                return true;
            case R.id.contact_us:
                startActivity(new Intent(getApplicationContext(),ContactUs.class));
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    //end of 3dot mnue

}