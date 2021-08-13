package com.mwewghwai.moneyspend_app;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;

public class ExpensesActivity extends MainActivity {

//Item declaration
    DatabaseHelper dataBase;
    Button back_arrow;
    Button calendar_button;
    TextView expenses_ammount;
    ToggleButton today_tbutton;
    ToggleButton thisMonth_tbutton;
    ToggleButton all_tbutton;
    ConstraintLayout empty_state_layout;
    RecyclerView expenses_recyclerView;

//Variables


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.expenses_layout);

//Variables
        ArrayList<Boolean> type = new ArrayList<>();
        ArrayList<String> amount = new ArrayList<>();
        ArrayList<String> category = new ArrayList<>();
        ArrayList<String> note = new ArrayList<>();
        ArrayList<String> date = new ArrayList<>();
        ArrayList<String> time = new ArrayList<>();


//Item link
        dataBase = new DatabaseHelper(this);
        back_arrow = findViewById(R.id.expenses_back_button);
        calendar_button = findViewById(R.id.calendar_button);
        expenses_ammount = findViewById(R.id.expenses_layout_amount);
        today_tbutton = findViewById(R.id.today_tbutton);
        thisMonth_tbutton = findViewById(R.id.this_month_tbutton);
        all_tbutton = findViewById(R.id.all_tbutton);
        empty_state_layout = findViewById(R.id.empty_state_layout);
        expenses_recyclerView = findViewById(R.id.expenses_recyclerView);

//Initializations
        today_tbutton.setChecked(true);
        today_tbutton.setEnabled(false);
        thisMonth_tbutton.setChecked(false);
        thisMonth_tbutton.setEnabled(true);
        all_tbutton.setChecked(false);
        all_tbutton.setEnabled(true);


//dataBase->Arrays
        Cursor data = dataBase.getContent("Expenses");
        if(data.getCount() == 0){
            empty_state_layout.setVisibility(ConstraintLayout.VISIBLE);
        }
        else{
            while(data.moveToNext()){

                if(data.getInt(1) == 0){
                    type.add(false);
                }
                else if(data.getInt(1) == 1){
                    type.add(true);
                }

                amount.add(String.valueOf(data.getFloat(2)));
                category.add(data.getString(3));
                note.add(data.getString(4));
                date.add(data.getString(5));
                time.add(data.getString(6));

            }
        }


//Buttons

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        calendar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        today_tbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thisMonth_tbutton.setChecked(false);
                all_tbutton.setChecked(false);
                today_tbutton.setEnabled(false);
                thisMonth_tbutton.setEnabled(true);
                all_tbutton.setEnabled(true);

            }
        });

        thisMonth_tbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                today_tbutton.setChecked(false);
                all_tbutton.setChecked(false);
                thisMonth_tbutton.setEnabled(false);
                today_tbutton.setEnabled(true);
                all_tbutton.setEnabled(true);

            }
        });

        all_tbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                today_tbutton.setChecked(false);
                thisMonth_tbutton.setChecked(false);
                all_tbutton.setEnabled(false);
                today_tbutton.setEnabled(true);
                thisMonth_tbutton.setEnabled(true);

            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
