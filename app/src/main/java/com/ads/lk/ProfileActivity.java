package com.ads.lk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    TextView editTextTextEmailAddress2,editTextTextEmailAddress,editTextTextPhone,editTextTextPassword2;
    Button btnsignin,logout,btnsignin4;
    DatabaseReference db;
    User userob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bprofile);
        db = FirebaseDatabase.getInstance().getReference("User");
        userob = new User();
        editTextTextEmailAddress2 = findViewById(R.id.editTextTextEmailAddress2);
        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        editTextTextPhone = findViewById(R.id.editTextTextPhone);
        editTextTextPassword2 = findViewById(R.id.editTextTextPassword2);
        logout=findViewById(R.id.logout);
        btnsignin=findViewById(R.id.btnsignin);
        btnsignin4=findViewById(R.id.btnsignin4);
        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, EditActivity.class));

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }
        });
        btnsignin4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, ChangepwActivity.class));

            }
        });
//show

        showUserdata();
    }

    private void showUserdata() {

        //from sign in
        Intent intent = getIntent();
        String user_email = intent.getStringExtra("email");
        String user_name = intent.getStringExtra("name");


        //from device
        if (user_name == null) {
            SharedPreferences sharedPref = getSharedPreferences("myPref", MODE_PRIVATE);
            user_email = sharedPref.getString("email", "");
            user_name = sharedPref.getString("name", "");

        }

        editTextTextEmailAddress.setText(user_email);
        editTextTextEmailAddress2.setText(user_name);


    }
    public void UpdateProfile(View view) {

        DatabaseReference upRef = FirebaseDatabase.getInstance().getReference().child("User");
        upRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild(editTextTextEmailAddress2.getText().toString().trim())){

                    userob.setName(editTextTextEmailAddress2.getText().toString().trim());
                    userob.setEmail(editTextTextEmailAddress.getText().toString().trim());

                    String name = editTextTextEmailAddress2.getText().toString().trim();

                    db = FirebaseDatabase.getInstance().getReference().child("User").child(name);
                    db.setValue(userob);


                    //Feedback to the user via a Toast..

                    Toast.makeText(getApplicationContext(), "Profile Updated Successfully",Toast.LENGTH_SHORT).show();


                }else
                    Toast.makeText(getApplicationContext(),"No Source to Update", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void ClearControls() {
        editTextTextEmailAddress2.setText("");
        editTextTextEmailAddress.setText("");


    }
}