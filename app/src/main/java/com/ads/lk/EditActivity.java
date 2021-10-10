package com.ads.lk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditActivity extends AppCompatActivity {
    Button back,btnsignin;
    ImageView profileIcon;
    EditText editTextTextEmailAddress2,editTextTextEmailAddress,editTextTextPassword,editTextTextPassword2;
    DatabaseReference db;
    User userob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        back=findViewById(R.id.back);
        profileIcon=findViewById(R.id.profileIcon);
        btnsignin=findViewById(R.id.btnsignin);
        editTextTextEmailAddress2=findViewById(R.id.editTextTextEmailAddress2);
        editTextTextEmailAddress=findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword=findViewById(R.id.editTextTextPassword);
        editTextTextPassword2=findViewById(R.id.editTextTextPassword2);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditActivity.this, ProfileActivity.class));
            }
        });
        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditActivity.this, ProfileActivity.class));
            }
        });

        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference updFeedRef = FirebaseDatabase.getInstance().getReference().child("User");
                updFeedRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot  dataSnapshot) {
                        if(dataSnapshot.hasChild(editTextTextEmailAddress2.getText().toString().trim())){
                            try {
                            userob.setName(editTextTextEmailAddress2.getText().toString().trim());
                            userob.setEmail(editTextTextEmailAddress.getText().toString().trim());
                            userob.setPhone(editTextTextPassword.getText().toString().trim());
                            userob.setLive(editTextTextPassword2.getText().toString().trim());

                            String name = editTextTextEmailAddress2.getText().toString().trim();

                            db = FirebaseDatabase.getInstance().getReference().child("User").child(name);
                            db.setValue(userob);


                            //Feedback to the user via a Toast..

                                Toast.makeText(getApplicationContext(), "Data updated successfully", Toast.LENGTH_SHORT).show();
                            }
                            catch (NumberFormatException e){
                                Toast.makeText(getApplicationContext(), "Invalid Details", Toast.LENGTH_SHORT).show();
                            }

                        }else
                            Toast.makeText(getApplicationContext(),"No Source to Update", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });




            }
        });







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
}