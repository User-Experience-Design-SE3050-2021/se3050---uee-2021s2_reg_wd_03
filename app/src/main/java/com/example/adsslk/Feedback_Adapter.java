package com.example.adsslk;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.w3c.dom.Comment;

import java.util.HashMap;
import java.util.Map;

public class Feedback_Adapter extends FirebaseRecyclerAdapter<Feedback_Model,Feedback_Adapter.myViewHolder > {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Feedback_Adapter(@NonNull FirebaseRecyclerOptions<Feedback_Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull Feedback_Model model) {
        holder.Name.setText(model.getName());
        holder.Comment.setText(model.getComment());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.edit.getContext())
                        .setContentHolder(new ViewHolder(R.layout.edit_popup))
                        .setExpanded(true,1150)
                        .create();

//                dialogPlus.show();
                View view =dialogPlus.getHolderView();
                EditText name = view.findViewById(R.id.txt_name);
                EditText comment = view.findViewById(R.id.txt_comment);
                 Button btnUpdate = view.findViewById(R.id.btn_edit_send);

                 name.setText(model.getName());
                 comment.setText(model.getComment());
                 dialogPlus.show();

                 btnUpdate.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         Map<String,Object> map = new HashMap<>();
                         map.put("Name",name.getText().toString());
                         map.put("Comment",comment.getText().toString());

                         if(name.length()==0){
                             name.setError("This field is required");
                         }
                         else if(comment.length()==0)
                         {
                             comment.setError("This field is required");
                         }
                         else {
                             FirebaseDatabase.getInstance().getReference().child("feedback")
                                     .child(getRef(position).getKey()).updateChildren(map)
                                     .addOnSuccessListener(new OnSuccessListener<Void>() {
                                         @Override
                                         public void onSuccess(Void unused) {
                                             Toast.makeText(v.getContext(), "Feedback updated successfully ", Toast.LENGTH_SHORT).show();
                                             dialogPlus.dismiss();
                                         }
                                     })
                                     .addOnFailureListener(new OnFailureListener() {
                                         @Override
                                         public void onFailure(@NonNull Exception e) {
                                             Toast.makeText(v.getContext(), "Error..!", Toast.LENGTH_SHORT).show();
                                             dialogPlus.dismiss();
                                         }
                                     });
                         }
                     }
                 });

//                 holder.delete.setOnClickListener(new View.OnClickListener() {
//                     @Override
//                     public void onClick(View view) {
//                         AlertDialog.Builder builder = new AlertDialog.Builder(holder.Name.getContext());
//                         builder.setTitle("Are you sure about this deletion?");
//                         builder.setMessage("Deleted data cannot be undo");
//
//                         builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
//                             @Override
//                             public void onClick(DialogInterface dialogInterface, int i) {
//                                 FirebaseDatabase.getInstance().getReference().child("feedback")
//                                         .child(getRef(position).getKey()).removeValue();
//                                 Toast.makeText(v.getContext(), "Feedback deleted successfully.", Toast.LENGTH_SHORT).show();
//
//                             }
//                         });
//
//                         builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                             @Override
//                             public void onClick(DialogInterface dialogInterface, int i) {
//                                 Toast.makeText(v.getContext(), "Canceled..", Toast.LENGTH_SHORT).show();
//
//                             }
//                         });
//                         builder.show();
//                     }
//                 });

            }

        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.Name.getContext());
                builder.setTitle("Are you sure about this deletion?");
                builder.setMessage("Deleted data cannot be undo");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("feedback")
                                .child(getRef(position).getKey()).removeValue();
                        Toast.makeText(view.getContext(), "Feedback deleted successfully.", Toast.LENGTH_SHORT).show();

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(view.getContext(), "Canceled..", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.show();
            }
        });
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_feedback_card,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        TextView Name, Comment;
        ImageButton edit,delete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            Name=(TextView) itemView.findViewById(R.id.feed_show_name);
            Comment =(TextView) itemView.findViewById(R.id.feed_show_comment);
            edit=(ImageButton) itemView.findViewById(R.id.button_edit);
            delete=(ImageButton) itemView.findViewById(R.id.button_dlt);

        }
    }
}

