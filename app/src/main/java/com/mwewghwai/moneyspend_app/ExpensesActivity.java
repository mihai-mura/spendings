package com.mwewghwai.moneyspend_app;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExpensesActivity extends MainActivity {

//Item declaration
    DatabaseHelper dataBase;
    RecyclerViewCustomAdapter customAdapter;
    Button back_arrow;
    Button calendar_button;
    TextView expenses_ammount;
    ToggleButton today_tbutton;
    ToggleButton thisMonth_tbutton;
    ToggleButton all_tbutton;
    ConstraintLayout empty_state_layout;
    RecyclerView expenses_recyclerView;

//Variables
    ArrayList<Boolean> type = new ArrayList<>();
    ArrayList<String> amount = new ArrayList<>();
    ArrayList<String> category = new ArrayList<>();
    ArrayList<String> note = new ArrayList<>();
    ArrayList<String> date = new ArrayList<>();
    ArrayList<String> time = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.expenses_layout);

//Variables



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
        today_tbutton.setChecked(false);
        today_tbutton.setEnabled(true);
        thisMonth_tbutton.setChecked(true);
        thisMonth_tbutton.setEnabled(false);
        all_tbutton.setChecked(false);
        all_tbutton.setEnabled(true);

        populateRecycleView("thisMonth");


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

        today_tbutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    populateRecycleView("today");

                }
            }
        });

        thisMonth_tbutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    populateRecycleView("thisMonth");

                }
            }
        });

        all_tbutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    populateRecycleView(null);

                }
            }
        });

    }

    private void populateRecycleView(String interval){
        //empty arrays
        type.clear();
        amount.clear();
        category.clear();
        note.clear();
        date.clear();
        time.clear();

        //dataBase->Arrays
        Cursor data = dataBase.getContent("Expenses", interval);
        if(data.getCount() == 0){
                empty_state_layout.setVisibility(ConstraintLayout.VISIBLE);

        }
        else{
            empty_state_layout.setVisibility(ConstraintLayout.INVISIBLE);

            while(data.moveToNext()){

                if(data.getInt(1) == 0){
                    type.add(false);
                }
                else if(data.getInt(1) == 1){
                    type.add(true);
                }


                if(data.getFloat(2) == data.getInt(2)) {
                    amount.add(String.valueOf(data.getInt(2)));
                }
                else{
                    amount.add(String.valueOf(data.getFloat(2)));
                }
                category.add(data.getString(3));
                note.add(data.getString(4));
                date.add(data.getString(5));
                time.add(data.getString(6));

            }
        }

        //without this recyclerView does not show
        LinearLayoutManager llm = new LinearLayoutManager(ExpensesActivity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        expenses_recyclerView.setLayoutManager(llm);

        customAdapter = new RecyclerViewCustomAdapter(ExpensesActivity.this, type, amount, category, note, date, time);
        expenses_recyclerView.setAdapter(customAdapter);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
