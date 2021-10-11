package com.tantalum.adslk;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {

   View view;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        view = itemView;


    }

    public void setDetails(Context context,String productPrice,String productTitle,String productIcon){
        TextView product = view.findViewById(R.id.product);
        TextView price = view.findViewById(R.id.price);

        ImageView image = view.findViewById(R.id.imageView);


        product.setText(productTitle);
        price.setText(productPrice);

        Picasso.get().load(productIcon).into(image);
    }
}
