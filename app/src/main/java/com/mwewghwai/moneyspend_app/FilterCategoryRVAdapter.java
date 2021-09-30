package com.mwewghwai.moneyspend_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FilterCategoryRVAdapter extends RecyclerView.Adapter<FilterCategoryRVAdapter.ViewHolder> {

    private Context context;
    private ArrayList filter_category;

    FilterCategoryRVAdapter(Context context, ArrayList filter_category){
        this.context = context;
        this.filter_category = filter_category;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.filter_category_list_layout, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.filter_category_text.setText((CharSequence) filter_category.get(position));

    }

    @Override
    public int getItemCount() {
        return  filter_category.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView filter_category_text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            filter_category_text = itemView.findViewById(R.id.filter_category_text);
        }
    }
}
