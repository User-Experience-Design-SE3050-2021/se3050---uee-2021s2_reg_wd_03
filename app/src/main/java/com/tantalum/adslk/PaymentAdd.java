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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class PaymentAdd extends AppCompatActivity {

    EditText name,bankName,accNo,username;
    Button addPayMethod;
    FirebaseDatabase db;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_add);

        name = (EditText) findViewById(R.id.editTextTextPersonName);
        bankName = (EditText) findViewById(R.id.txtbankName);
        accNo = (EditText) findViewById(R.id.txtaccNo);
        username = (EditText) findViewById(R.id.editTextusername);

        addPayMethod = (Button) findViewById(R.id.btnSub);

        addPayMethod.setEnabled(false);

        //check whether it's empty or not
        name.addTextChangedListener(textWatcher);
        bankName.addTextChangedListener(textWatcher);
        accNo.addTextChangedListener(textWatcher);
        username.addTextChangedListener(textWatcher);



        addPayMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String submitName = name.getText().toString();
                String submitBankName = bankName.getText().toString();
                String submitAccNo = accNo.getText().toString();
                String submitUsername = username.getText().toString();

                validationInfo(submitUsername,submitName,submitBankName,submitAccNo);
                if(validationInfo(submitUsername,submitName,submitBankName,submitAccNo)==true){

                    PaymentModel payment = new PaymentModel(submitUsername,submitName,submitBankName,submitAccNo);
                    db = FirebaseDatabase.getInstance();
                    reference = db.getReference("paymentdetails");
                    reference.child(submitUsername).setValue(payment).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            name.setText("");
                            bankName.setText("");
                            accNo.setText("");
                            Toast.makeText(PaymentAdd.this,"Details Inserted Successfully",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(PaymentAdd.this, "Details Not Inserted", Toast.LENGTH_SHORT).show();
                        }
                    });

                    //to send user name to next interface
                    Intent intent = new Intent(PaymentAdd.this,PackageSelection.class);
                    intent.putExtra("username",submitUsername);
                    startActivity(intent);
//                    startActivity(new Intent(getApplicationContext(),PackageSelection .class));

                }else{
                    addPayMethod.setEnabled(false);
                }

            }
        });


    }
    //text fiels validation
    private boolean validationInfo(String submitUsername,String submitName, String submitBankName, String submitAccNo) {

        if(submitUsername.length()==0){
            username.requestFocus();
            username.setError("Plaese enter user name");
            return false;
        }
        else if(submitName.length()==0){
            name.requestFocus();
            name.setError("Enter your name");
            return false;
        }
        else if(!submitName.matches("[^0-9]*")){
            name.requestFocus();
            name.setError("Name must not have digits");
            return false;
        }
        else if(submitBankName.length()==0){
            bankName.requestFocus();
            bankName.setError("Plaese enter bank name");
            return false;
        }
        else if(!submitBankName.matches("[^0-9]*")){
            bankName.requestFocus();
            bankName.setError("Bank name must not have digits");
            return false;
        }
        else if(submitAccNo.length()==0){
            accNo.requestFocus();
            accNo.setError("Plaese enter account number");
            return false;
        }
        else if(submitAccNo.length()<5){
            accNo.requestFocus();
            accNo.setError("Enter valid account number");
            return false;
        }else{
            return true;
        }

    }


    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String namee = name.getText().toString();
            String  banknamee = bankName.getText().toString();
            String  accNoo = accNo.getText().toString();
            String  usernamee = username.getText().toString();

            if (!namee.isEmpty() && !banknamee.isEmpty() && !accNoo.isEmpty() && !usernamee.isEmpty())
            {
                addPayMethod.setEnabled(true);
            }else{
                addPayMethod.setEnabled(false);
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}