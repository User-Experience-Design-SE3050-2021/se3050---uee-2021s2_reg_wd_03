package com.example.adsmobileapp;

import android.widget.Filter;

import java.util.ArrayList;


public class FilterAds extends Filter {

    private AdapterSellerAds adapter;
    private ArrayList<ModeAdDetails> filterList;

    public FilterAds(AdapterSellerAds adapter, ArrayList<ModeAdDetails> filterList) {
        this.adapter = adapter;
        this.filterList = filterList;
    }


    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        //validate data for search array
        if(constraint != null && constraint.length()>0){
            // change to uppercase to make case insensitive
            constraint = constraint.toString().toUpperCase();
            // store our filtered list
            ArrayList<ModeAdDetails> filteredModels = new ArrayList<>();
            for(int i = 0; i<filterList.size();i++){
                // search by title and category
                if(filterList.get(i).getProductCategory().toUpperCase().contains(constraint)|| filterList.get(i).getProductTitle().toUpperCase().contains(constraint)){
                    // add filtered list to list
                    filteredModels.add(filterList.get(i));
                }
            }
            results.count = filteredModels.size();
            results.values = filteredModels;
        }
        else {
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.adList = (ArrayList<ModeAdDetails>) results.values;
        // refresh adapter
        adapter.notifyDataSetChanged();

    }
}
