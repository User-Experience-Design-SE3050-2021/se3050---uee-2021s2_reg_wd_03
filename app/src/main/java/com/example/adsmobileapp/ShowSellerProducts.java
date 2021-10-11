package com.example.adsmobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowSellerProducts extends AppCompatActivity {

    private TextView tvFilteredAds;
    private EditText searchAd;
    private ImageButton filterAds;
    private RecyclerView rvAds;

    private ArrayList<ModeAdDetails> adList;
    private AdapterSellerAds adapterSellerAds;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_seller_products);

        tvFilteredAds = findViewById(R.id.tvFilteredAds);
        searchAd = findViewById(R.id.searchAd);
        filterAds = findViewById(R.id.filterAds);
        rvAds = findViewById(R.id.rvAds);

        loadAllAds();

        // search
        searchAd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                try{
                    adapterSellerAds.getFilter().filter(s);

                }catch(Exception e){
                    e.printStackTrace();

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        filterAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ShowSellerProducts.this);
                builder.setTitle("Choose Category : ")
                        .setItems(Constants.productCategories1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                // get selected item
                                String selected = Constants.productCategories1[which];
                                tvFilteredAds.setText(selected);
                                if(selected.equals("All")){
                                    // load all
                                    loadAllAds();
                                }
                                else{
                                    // load filtered
                                    loadFilteredAds(selected);
                                }
                            }
                        })
                        .show();
            }
        });
    }

    private void loadFilteredAds(String selected) {

        adList = new ArrayList<>();

        //DatabaseReference databaseReference = (DatabaseReference) FirebaseDatabase.getInstance().getReference("postAds")
        DatabaseReference reference = (DatabaseReference) FirebaseDatabase.getInstance().getReference("postAds");


                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //adList.clear();
                        for(DataSnapshot ds:snapshot.getChildren()){
                            String productCategory = ""+ds.child("productCategory").getValue();
                            if(selected.equals(productCategory)){
                                ModeAdDetails modeAdDetails = ds.getValue(ModeAdDetails.class);
                                adList.add(modeAdDetails);
                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        adapterSellerAds = new AdapterSellerAds(ShowSellerProducts.this,adList);
        rvAds.setAdapter(adapterSellerAds);
    }


    private void loadAllAds() {
        adList = new ArrayList<>();


        DatabaseReference reference = (DatabaseReference) FirebaseDatabase.getInstance().getReference("postAds");
        reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //adList.clear();
                        for(DataSnapshot ds:snapshot.getChildren()){

                            ModeAdDetails modeAdDetails = ds.getValue(ModeAdDetails.class);
                            adList.add(modeAdDetails);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        adapterSellerAds = new AdapterSellerAds(ShowSellerProducts.this,adList);
        rvAds.setAdapter(adapterSellerAds);
    }
}