package com.tantalum.adslk;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class PublishAdPackages extends AppCompatActivity {

    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    TextView amount;
    ImageView pickdate;
    private int mDate,mMonth,mYear;
    EditText dateget;
    Button pkgGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_ad_packages);

        String username = getIntent().getStringExtra("username");

        spinner = (Spinner) findViewById(R.id.txtpkgname);
        amount = (TextView)findViewById(R.id.etxtamount);
        pickdate = (ImageView)findViewById(R.id.claender);
        dateget = (EditText)findViewById(R.id.editTextDate);
        pkgGet= (Button)findViewById(R.id.buy_prmote_pkgBtn);

        //set button disable
        pkgGet.setEnabled(false);
        //check whether it's empty or not
        dateget.addTextChangedListener(textWatcher);



        //package selection drop down
        adapter = ArrayAdapter.createFromResource(this, R.array.publish_packages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String jar = parent.getItemAtPosition(position).toString();

                if(jar.equals("Lands Package")){
                    String dayfour = "800.00";
                    amount.setText(dayfour);
                }else if(jar.equals("Vehicles Package")){
                    String dayseven = "500.00";
                    amount.setText(dayseven);
                }else if(jar.equals("Electrics Package")){
                    String dayfteen = "200.00";
                    amount.setText(dayfteen);
                }else{
                    amount.setText(null);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //date picker get
        pickdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar calendar = Calendar.getInstance();
                mDate = calendar.get(Calendar.DATE);
                mMonth = calendar.get(Calendar.MONTH);
                mYear = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(PublishAdPackages.this,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        month=month+1;
                        dateget.setText(date+" - "+month+" - "+year);
                    }
                },mDate,mMonth,mYear);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()-1000);
                datePickerDialog.show();
            }
        });


        pkgGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataAdded(username);
            }
        });

    }

    private void dataAdded(String username) {

        Map<String,Object> map = new HashMap<>();
        map.put("username",username);
        map.put("publishPkgName",spinner.getSelectedItem().toString());
        map.put("amount",amount.getText().toString());
        map.put("date",dateget.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("PublishAdPackages").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(PublishAdPackages.this,"Package Inserted Successfully",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(PublishAdPackages.this, "Package Not Inserted", Toast.LENGTH_SHORT).show();
                    }
                });

        dateget.setText("");

    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            String  datepkg = dateget.getText().toString();

            if (!datepkg.isEmpty())
            {
                pkgGet.setEnabled(true);
            }else{
                pkgGet.setEnabled(false);
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}