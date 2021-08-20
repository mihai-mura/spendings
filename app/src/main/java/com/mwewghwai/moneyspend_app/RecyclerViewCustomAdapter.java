package com.mwewghwai.moneyspend_app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class RecyclerViewCustomAdapter extends RecyclerView.Adapter<RecyclerViewCustomAdapter.ViewHolder> {

    public static final String BROADCAST_FILTER = "RecycleViewCustomAdapter_broadcast_receiver_intent_filter";
    private DatabaseHelper dataBase;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
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

        //ToDo:change delete dialog
        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(context)
                        .setTitle("Delete record")
                        .setMessage(amount.get(position) + " RON\n" + category.get(position) + "\n" + date.get(position) + " " + time.get(position))
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dataBase = new DatabaseHelper(context);
                                dataBase.removeFromExpenses((String) amount.get(position),(String) date.get(position),(String) time.get(position));
                                Log.d("DataBase", "Removed from expenses " + amount.get(position) + " RON, from: " + date.get(position) + " " + time.get(position));
                                type.remove(position);
                                amount.remove(position);
                                category.remove(position);
                                note.remove(position);
                                date.remove(position);
                                time.remove(position);
                                notifyItemRemoved(position);

                                Intent i = new Intent(BROADCAST_FILTER);
                                i.putExtra("item_deleted", true);
                                context.sendBroadcast(i);

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                dialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return  amount.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View cash_card;
        TextView amount, category, note, date, time;
        LinearLayout rootLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cash_card = itemView.findViewById(R.id.recycler_expense_cash_card_view);
            amount = itemView.findViewById(R.id.recycler_expense_amount);
            category = itemView.findViewById(R.id.recycler_expense_category);
            note = itemView.findViewById(R.id.recycler_expense_note);
            date = itemView.findViewById(R.id.recycler_expense_date);
            time = itemView.findViewById(R.id.recycler_expense_time);
            rootLayout = itemView.findViewById(R.id.recycler_item_root_layout);
        }
    }
}
