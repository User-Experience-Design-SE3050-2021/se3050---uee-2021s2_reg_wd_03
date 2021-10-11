package com.tantalum.adslk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Member;

public class Home extends AppCompatActivity {

    public RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    ImageView pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        pay = (ImageView)findViewById(R.id.payIcon);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("postAds");

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this,PaymentAdd.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();

        FirebaseRecyclerOptions<ItemHome> options =
                new FirebaseRecyclerOptions.Builder<ItemHome>()
                .setQuery(reference,ItemHome.class)
                .build();

        FirebaseRecyclerAdapter<ItemHome,ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<ItemHome, ViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull ItemHome model) {
                        holder.setDetails(getApplication(),model.getProductPrice(),model.getProductTitle(),model.getProductIcon());
                    }

                    @NonNull
                    @Override
                    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.home_item,parent,false);

                        return new ViewHolder(view);
                    }
                };

        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.searchorder,menu);

        MenuItem item=menu.findItem(R.id.search_order);

        SearchView searchView=(SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                textSearchOrdr(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                textSearchOrdr(query);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void textSearchOrdr(String str){
        FirebaseRecyclerOptions<ItemHome> options =
                new FirebaseRecyclerOptions.Builder<ItemHome>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("postAds").orderByChild("productTitle").startAt(str).endAt(str+"~"), ItemHome.class)
                        .build();

        FirebaseRecyclerAdapter<ItemHome,ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<ItemHome, ViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull ItemHome model) {
                        holder.setDetails(getApplication(),model.getProductPrice(),model.getProductTitle(),model.getProductIcon());
                    }

                    @NonNull
                    @Override
                    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.home_item,parent,false);

                        return new ViewHolder(view);
                    }
                };

        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }
}