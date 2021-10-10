package com.example.adsslk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.database.FirebaseDatabase;

public class View_Feedback extends AppCompatActivity {
    RecyclerView recyclerView;
    Feedback_Adapter feedback_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feedback);

        recyclerView =(RecyclerView) findViewById(R.id.feed_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Feedback_Model> options =
                new FirebaseRecyclerOptions.Builder<Feedback_Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("feedback"), Feedback_Model.class)
                        .build();

//        FirebaseRecyclerOptions<Feedback_Model> options =
//                new FirebaseRecyclerOptions.Builder<Feedback_Model>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference("orders")
//                                .orderByChild("orderstatus").equalTo(" Sent"),Feedback_Model.class)
//                        .build();


        feedback_adapter=new Feedback_Adapter(options);
        recyclerView.setAdapter(feedback_adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        feedback_adapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        feedback_adapter.stopListening();
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