package com.mwewghwai.moneyspend_app;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class RecyclerViewCustomAdapter extends RecyclerView.Adapter<RecyclerViewCustomAdapter.ViewHolder> {


    private Context context;
    private ArrayList<Boolean> type;
    private ArrayList amount, category, note, date, time;

    RecyclerViewCustomAdapter(Context context, ArrayList type, ArrayList amount, ArrayList category, ArrayList note, ArrayList date, ArrayList time){
        this.context = context;
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.note = note;
        this.date = date;
        this.time = time;
        Collections.reverse(this.type);
        Collections.reverse(this.amount);
        Collections.reverse(this.category);
        Collections.reverse(this.note);
        Collections.reverse(this.date);
        Collections.reverse(this.time);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.expense_item_list_layout, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(!type.get(position)){
            holder.cash_card.setBackgroundResource(R.drawable.ic_cash_outline);
        }
        else if(type.get(position)){
            holder.cash_card.setBackgroundResource(R.drawable.ic_card_outline);
        }
        holder.amount.setText(amount.get(position) + " RON");
        holder.category.setText((CharSequence) category.get(position));
        holder.note.setText((CharSequence) note.get(position));
        holder.date.setText((CharSequence) date.get(position));
        holder.time.setText((CharSequence) time.get(position));

    }

    @Override
    public int getItemCount() {
        return  amount.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View cash_card;
        TextView amount, category, note, date, time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cash_card = itemView.findViewById(R.id.recycler_expense_cash_card_view);
            amount = itemView.findViewById(R.id.recycler_expense_amount);
            category = itemView.findViewById(R.id.recycler_expense_category);
            note = itemView.findViewById(R.id.recycler_expense_note);
            date = itemView.findViewById(R.id.recycler_expense_date);
            time = itemView.findViewById(R.id.recycler_expense_time);
        }
    }
}
