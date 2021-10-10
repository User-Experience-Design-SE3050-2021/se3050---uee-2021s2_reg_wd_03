package com.ads.lk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    Button button,button7,btnsignin2;
    EditText editTextTextEmailAddress,editTextTextPassword;
    RadioButton male,female;
    DatabaseReference db;
    FirebaseDatabase rootNode;

    User userob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userob = new User();

        button = findViewById(R.id.button);
        button7=findViewById(R.id.button7);
        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
        male=findViewById(R.id.Male);
        female=findViewById(R.id.Female);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(MainActivity.this, Register.class));
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(MainActivity.this, Register.class));
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(MainActivity.this, ForgotPassword.class));
            }
        });


    }


    public void Login(View view) {

        String enteredUsername = editTextTextEmailAddress.getText().toString().trim();
        String enteredPassword = editTextTextPassword.getText().toString().trim();

        db = FirebaseDatabase.getInstance().getReference().child("User");

        Query checkUser = db.orderByChild("name").equalTo(enteredUsername);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    String passwordFromDB = dataSnapshot.child(enteredUsername).child("password").getValue(String.class);

                    if(passwordFromDB.equals(enteredPassword)){
                        String emailFromDB = dataSnapshot.child(enteredUsername).child("email").getValue(String.class);
                        String nameFromDB = dataSnapshot.child(enteredUsername).child("name").getValue(String.class);
                        String m1=male.getText().toString();
                        String m2=female.getText().toString();

                        if(male.isChecked()){
                            Intent intent = new Intent(getApplicationContext(),ProfileActivity2.class);
                            intent.putExtra("name",nameFromDB);
                            intent.putExtra("email",emailFromDB);
                            intent.putExtra("password",passwordFromDB);
                            saveToDevice(nameFromDB, emailFromDB, passwordFromDB);

                            Toast.makeText(getApplicationContext()," Successfully Logged in",Toast.LENGTH_LONG).show();
                            startActivity(intent);
                            ClearControls();
                        }else{
                            Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                            intent.putExtra("name",nameFromDB);
                            intent.putExtra("email",emailFromDB);
                            intent.putExtra("password",passwordFromDB);
                            saveToDevice(nameFromDB, emailFromDB, passwordFromDB);

                            Toast.makeText(getApplicationContext()," Successfully Logged in",Toast.LENGTH_LONG).show();
                            startActivity(intent);
                            ClearControls();
                        }



                    }else {

                        Toast.makeText(getApplicationContext(),"Password Error",Toast.LENGTH_LONG).show();
                        ClearControls();


                }

                }else {

                    Toast.makeText(getApplicationContext(),"Username Error",Toast.LENGTH_LONG).show();
                    ClearControls();
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        editTextTextEmailAddress.setText("");
        editTextTextPassword.setText("");


    }
    private void saveToDevice(String name, String email, String password) {
        SharedPreferences sharedPref = getSharedPreferences("myPref", MODE_PRIVATE);
        sharedPref.edit().putString("name", name).apply();
        sharedPref.edit().putString("email", email).apply();
        sharedPref.edit().putString("password", password).apply();

    }

    public void ClearControls() {
        editTextTextEmailAddress.setText("");
        editTextTextPassword.setText("");
    }

}