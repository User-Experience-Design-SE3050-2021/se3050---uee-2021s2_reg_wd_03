package com.example.adsmobileapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterSellerAds extends RecyclerView.Adapter<AdapterSellerAds.HolderSellerAds> implements Filterable {

    private Context context;
    public ArrayList<ModeAdDetails> adList,filterList;
    private FilterAds filter;

    public AdapterSellerAds(Context context, ArrayList<ModeAdDetails> adList) {
        this.context = context;
        this.adList = adList;
        this.filterList=adList;
    }

    @NonNull
    @Override
    public HolderSellerAds onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate layout
        View view = LayoutInflater.from(context).inflate(R.layout.seller_ads,parent,false);
        return new HolderSellerAds(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderSellerAds holder, int position) {
        // get data
        ModeAdDetails modeAdDetails = adList.get(position);
        String id = modeAdDetails.getProductId();
        String title = modeAdDetails.getProductTitle();
        String description = modeAdDetails.getProductDescription();
        String category = modeAdDetails.getProductCategory();
        String price = modeAdDetails.getProductPrice();
        String icon = modeAdDetails.getProductIcon();


        // set data
        holder.tvTitle.setText(title);
        holder.tvDescription.setText(description);
        holder.tvCategory.setText(category);
        holder.tvPrice.setText(price);
        holder.ivItem.setImageResource(R.drawable.ic_baseline_add_shopping_cart_24);
        Picasso.get().load(icon).into(holder.ivItem);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // handle item clicks, show item details
            }
        });
    }



    @Override
    public int getItemCount() {
        return adList.size();
    }

    @Override
    public Filter getFilter() {
        if(filter==null){
            filter = new FilterAds(this,filterList);
        }
        return filter;
    }

    class HolderSellerAds extends RecyclerView.ViewHolder{
        // holds view of recyclerview
        private ImageView ivItem;
        private TextView tvTitle,tvDescription,tvCategory,tvPrice;

        public HolderSellerAds(@NonNull View itemView) {
            super(itemView);

            ivItem = itemView.findViewById(R.id.ivItem);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvPrice = itemView.findViewById(R.id.tvPrice);

        }
    }
}
