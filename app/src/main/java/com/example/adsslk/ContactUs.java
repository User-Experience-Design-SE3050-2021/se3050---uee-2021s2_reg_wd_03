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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ContactUs extends AppCompatActivity {
   EditText name,email,problem;
   Button contact_send;

    Boolean isAllFieldsChecked= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        name=(EditText) findViewById(R.id.contact_name);
        email=(EditText) findViewById(R.id.contact_email);
        problem=(EditText) findViewById(R.id.contact_problem);
        contact_send=(Button) findViewById(R.id.contact_send);

        contact_send.setOnClickListener(new View.OnClickListener() {
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
        map.put("name",name.getText().toString());
        map.put("email",email.getText().toString());
        map.put("problem",problem.getText().toString());
        FirebaseDatabase.getInstance().getReference().child("ContactUs").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ContactUs.this, "You sent your problem successfully", Toast.LENGTH_SHORT).show();
                        clearAll();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ContactUs.this, "Error...!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void clearAll(){
        name.setText("");
        email.setText("");
        problem.setText("");
    }

    private boolean CheckAllFields() {
        if (name.length() == 0) {
            name.setError("This field is required");
            return false;
        }

        if (email.length() == 0) {
            email.setError("This field is required");
            return false;
        }
        if (problem.length() == 0) {
            problem.setError("This field is required");
            return false;
        }
        else {
            return true;
        }
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