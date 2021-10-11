package com.tantalum.adslk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class PaymentEdit extends AppCompatActivity {

    EditText name,bankName,accNo;
    TextView username;
    Button editPayMethod,back;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_edit);

        String usernamegot = getIntent().getStringExtra("username");
        String namegot = getIntent().getStringExtra("name");
        String banknamegot = getIntent().getStringExtra("bankname");
        String accnogot = getIntent().getStringExtra("accno");

        name = (EditText) findViewById(R.id.editTextTextPersonName);
        bankName = (EditText) findViewById(R.id.txtbankName);
        accNo = (EditText) findViewById(R.id.txtaccNo);
        username = (TextView) findViewById(R.id.editTextusername);

        editPayMethod = (Button) findViewById(R.id.btnEdit);
        back = (Button) findViewById(R.id.btnB);

        username.setText(usernamegot);
        name.setText(namegot);
        accNo.setText(accnogot);
        bankName.setText(banknamegot);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentEdit.this,PackageSelection.class);
                intent.putExtra("username",usernamegot);
                startActivity(intent);
            }
        });


        editPayMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updatedUsername = username.getText().toString();
                String updatedName = name.getText().toString();
                String updatedBankname = bankName.getText().toString();
                String updatedAccno = accNo.getText().toString();


                validationInfo(updatedName,updatedBankname,updatedAccno);
                updateData(updatedUsername,updatedName,updatedBankname,updatedAccno);

                if(validationInfo(updatedName,updatedBankname,updatedAccno)==true){


                }else{
                    editPayMethod.setEnabled(false);
                }
            }

        });

    }

    private void updateData(String updatedUsername, String updatedName, String updatedBankname, String updatedAccno) {

        HashMap paymentDetails = new HashMap();
        paymentDetails.put("username",updatedUsername);
        paymentDetails.put("name",updatedName);
        paymentDetails.put("bankname",updatedBankname);
        paymentDetails.put("accountnumber",updatedAccno);

        databaseReference = FirebaseDatabase.getInstance().getReference("paymentdetails");
        databaseReference.child(updatedUsername).updateChildren(paymentDetails).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {

                if(task.isSuccessful()){


                    Toast.makeText(PaymentEdit.this, "Details Updated", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(PaymentEdit.this, "Details Not Updated", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    //text fiels validation
    private boolean validationInfo(String updatedName,String updatedBankname, String updatedAccno) {


        if(updatedName.length()==0){
            name.requestFocus();
            name.setError("Enter your name");
            return false;
        }
        else if(!updatedName.matches("[^0-9]*")){
            name.requestFocus();
            name.setError("Name must not have digits");
            return false;
        }
        else if(updatedBankname.length()==0){
            bankName.requestFocus();
            bankName.setError("Plaese enter bank name");
            return false;
        }
        else if(!updatedBankname.matches("[^0-9]*")){
            bankName.requestFocus();
            bankName.setError("Bank name must not have digits");
            return false;
        }
        else if(updatedAccno.length()==0){
            accNo.requestFocus();
            accNo.setError("Plaese enter account number");
            return false;
        }
        else if(updatedAccno.length()<5){
            accNo.requestFocus();
            accNo.setError("Enter valid account number");
            return false;
        }else{
            return true;
        }

    }


}