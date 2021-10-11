package com.tantalum.adslk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PackageSelection extends AppCompatActivity {

    Button edit,delete,adPromotaBtn,asPublishBtn;
    TextView nametxt,banknametxt,accnotxt;
    DatabaseReference reference;
    ImageView home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_selection);

        String username = getIntent().getStringExtra("username");



        edit= (Button)findViewById(R.id.p_edit_btn);
        delete= (Button)findViewById(R.id.p_delete_btn);
        adPromotaBtn= (Button)findViewById(R.id.buy_prmote_pkgBtn);
        asPublishBtn= (Button)findViewById(R.id.p_addPublish_btn);
        nametxt= (TextView) findViewById(R.id.p_NameGetText);
        banknametxt= (TextView) findViewById(R.id.P_BnameGetText);
        accnotxt= (TextView) findViewById(R.id.P_AccNumberGetText);
        home= (ImageView) findViewById(R.id.homeIcon);

        adPromotaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PackageSelection.this,PromoteAdPackages.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

        asPublishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PackageSelection.this,PublishAdPackages.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PackageSelection.this,Home.class);
                startActivity(intent);
            }
        });

            readData(username);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    gotoedit(username);
//
//                startActivity(new Intent(getApplicationContext(),PaymentEdit .class));
                }

            });



        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(PackageSelection.this)
                        .setIcon(R.drawable.icon_warning)
                        .setTitle("Confirm Deletion")
                        .setMessage("Are you sure to delete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                deleteDetails(username);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

//                deleteDetails(username);
            }


        });

    }

    private void deleteDetails(String username) {
        reference  = FirebaseDatabase.getInstance().getReference("paymentdetails");
        reference.child(username).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    nametxt.setText("");
                    banknametxt.setText("");
                    accnotxt.setText("");

                    Toast.makeText(PackageSelection.this, "Details Deleted Successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(PackageSelection.this, "Faild to delete records", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void gotoedit(String username) {

        reference  = FirebaseDatabase.getInstance().getReference("paymentdetails");
        reference.child(username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if(task.isSuccessful()){

                    if(task.getResult().exists()){

                        DataSnapshot dataSnapshot = task.getResult();
                        String name =  String.valueOf(dataSnapshot.child("name").getValue());
                        String bankname =  String.valueOf(dataSnapshot.child("bankname").getValue());
                        String accno =  String.valueOf(dataSnapshot.child("accountnumber").getValue());
                        String username =  String.valueOf(dataSnapshot.child("username").getValue());

                        Intent intent = new Intent(PackageSelection.this,PaymentEdit.class);
                        intent.putExtra("username",username);
                        intent.putExtra("name",name);
                        intent.putExtra("bankname",bankname);
                        intent.putExtra("accno",accno);
                        startActivity(intent);

                    }else{
                        Toast.makeText(PackageSelection.this, "Faild to read records belogs to this user", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(PackageSelection.this, "Faild to read records", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void readData(String username) {

        reference  = FirebaseDatabase.getInstance().getReference("paymentdetails");
        reference.child(username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if(task.isSuccessful()){

                    if(task.getResult().exists()){

                        DataSnapshot dataSnapshot = task.getResult();
                        String name =  String.valueOf(dataSnapshot.child("name").getValue());
                        String bankname =  String.valueOf(dataSnapshot.child("bankname").getValue());
                        String accno =  String.valueOf(dataSnapshot.child("accountnumber").getValue());

                        nametxt.setText(name);
                        banknametxt.setText(bankname);
                        accnotxt.setText(accno);



                    }else{
                        Toast.makeText(PackageSelection.this, "Faild to read records belogs to this user", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(PackageSelection.this, "Faild to read records", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}