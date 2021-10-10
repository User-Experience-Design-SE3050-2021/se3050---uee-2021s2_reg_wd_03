package com.ads.lk;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Register extends AppCompatActivity {

     Button button7,btnsignin;
    EditText editTextTextEmailAddress2,editTextTextEmailAddress,editTextTextPassword,editTextTextPassword2;
     RadioButton male,female;
    User userob;

    FirebaseDatabase rootNode;
    DatabaseReference db;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextTextEmailAddress2 = findViewById(R.id.editTextTextEmailAddress2);
        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
        editTextTextPassword2 = findViewById(R.id.editTextTextPassword2);

        btnsignin = findViewById(R.id.btnsignin);

        userob = new User();
        male=findViewById(R.id.Male);
        female=findViewById(R.id.Female);
        button7 = findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(Register.this, MainActivity.class));
            }
        });

    }


    public void Signup(View view) {

        rootNode = FirebaseDatabase.getInstance();
        db = rootNode.getReference("User");


        try {
            if(TextUtils.isEmpty(editTextTextEmailAddress2.getText().toString().trim())){
                Toast.makeText(getApplicationContext(),"Enter Your Name",Toast.LENGTH_LONG).show();
            } else if (TextUtils.isEmpty(editTextTextEmailAddress.getText().toString().trim())){
                Toast.makeText(getApplicationContext(),"Enter Your Email",Toast.LENGTH_LONG).show();
            }
            else if (TextUtils.isEmpty(editTextTextPassword.getText().toString().trim())){
                Toast.makeText(getApplicationContext(),"Enter a Password",Toast.LENGTH_LONG).show();

            } else {
                userob.setName(editTextTextEmailAddress2.getText().toString().trim());
                userob.setEmail(editTextTextEmailAddress.getText().toString().trim());
                userob.setPassword(editTextTextPassword.getText().toString().trim());
                String m1=male.getText().toString();
                String m2=female.getText().toString();
                if(male.isChecked()){
                    userob.setType(m1);
                }else{
                    userob.setType(m2);
                }



                String Name = editTextTextEmailAddress2.getText().toString().trim();


                db.child(Name).setValue(userob);

                Toast.makeText(getApplicationContext(),"Profile Created",Toast.LENGTH_LONG).show();


                    startActivity(new Intent(Register.this, MainActivity.class));



                ClearControls();

            }


        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Number Format Exception", Toast.LENGTH_LONG).show();
        }
    }

    public void tosignin(View view) {


    }

    public void ClearControls() {
        editTextTextEmailAddress2.setText("");
        editTextTextEmailAddress.setText("");
        editTextTextPassword.setText("");
        editTextTextPassword2.setText("");
        male.setText("");
        female.setText("");


    }

}