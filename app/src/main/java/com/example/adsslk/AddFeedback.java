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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddFeedback extends AppCompatActivity {
   EditText name,comment;
   Button btn_send;
   ImageButton arrow;

    boolean isAllFieldsChecked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feedback);

        name=(EditText)findViewById(R.id.feed_name);
        comment=(EditText) findViewById(R.id.feed_comment);
        btn_send=(Button) findViewById(R.id.btn_feed_send);
        arrow=(ImageButton)findViewById(R.id.arrow_all_feed);



        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),View_Feedback.class));
            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllFieldsChecked = CheckAllFields();
                if(isAllFieldsChecked) {
                    insertData();
                }

            }
        });

    }

    private  void insertData(){
        Map<String,Object> map = new HashMap<>();
        map.put("Name",name.getText().toString());
        map.put("Comment",comment.getText().toString());
        FirebaseDatabase.getInstance().getReference().child("feedback").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddFeedback.this, "Feedback Added Successfully", Toast.LENGTH_SHORT).show();
                        clearAll();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddFeedback.this, "Error...!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void clearAll(){
        name.setText("");
        comment.setText("");
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

    private boolean CheckAllFields() {
        if (name.length() == 0) {
            name.setError("This field is required");
            return false;
        }

        if (comment.length() == 0) {
            comment.setError("This field is required");
            return false;
        }
        else {
        return true;
        }
    }
}