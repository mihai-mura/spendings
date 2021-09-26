package com.mwewghwai.moneyspend_app;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ExpensesActivity extends AppCompatActivity {

//Item declaration
    DatabaseHelper dataBase;
    RecyclerViewCustomAdapter customAdapter;
    Button back_arrow;
    Button calendar_button;
    TextView expenses_amount;
    ToggleButton today_tbutton;
    ToggleButton thisMonth_tbutton;
    ToggleButton all_tbutton;
    ConstraintLayout empty_state_layout;
    RecyclerView expenses_recyclerView;
    TextView cash_amount_text;
    TextView card_amount_text;
    TextView selected_date;

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
        expenses_amount = findViewById(R.id.expenses_layout_amount);
        today_tbutton = findViewById(R.id.today_tbutton);
        thisMonth_tbutton = findViewById(R.id.this_month_tbutton);
        all_tbutton = findViewById(R.id.all_tbutton);
        empty_state_layout = findViewById(R.id.empty_state_layout);
        expenses_recyclerView = findViewById(R.id.expenses_recyclerView);
        cash_amount_text = findViewById(R.id.cash_amount_text);
        card_amount_text = findViewById(R.id.card_amount_text);
        selected_date = findViewById(R.id.selected_date_text);

//Initializations
        today_tbutton.setChecked(false);
        today_tbutton.setEnabled(true);
        thisMonth_tbutton.setChecked(true);
        thisMonth_tbutton.setEnabled(false);
        all_tbutton.setChecked(false);
        all_tbutton.setEnabled(true);

        populateRecycleView("thisMonth");
        updateAmountTextViews("thisMonth");
        updateSelectedDate(null);

        //
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                SimpleDateFormat format_date = new SimpleDateFormat("d MMM yyyy");
                Calendar calendar = Calendar.getInstance();
                String today = format_date.format(calendar.getTime());
                String date;
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                date = format_date.format(calendar.getTime());

                populateRecycleView(date);
                updateAmountTextViews(date);
                updateSelectedDate(date);

                //three toggle update
                if(date.equals(today)){
                    today_tbutton.setChecked(true);
                    today_tbutton.setEnabled(false);
                    thisMonth_tbutton.setChecked(false);
                    thisMonth_tbutton.setEnabled(true);
                    all_tbutton.setChecked(false);
                    all_tbutton.setEnabled(true);
                }
                else{
                    today_tbutton.setChecked(false);
                    today_tbutton.setEnabled(true);
                    thisMonth_tbutton.setChecked(false);
                    thisMonth_tbutton.setEnabled(true);
                    all_tbutton.setChecked(false);
                    all_tbutton.setEnabled(true);
                }

            }

        };


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
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(ExpensesActivity.this, R.style.DatePickerDialog, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                        .show();

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
                    updateAmountTextViews("today");
                    updateSelectedDate(null);

                }
            }
        });

        thisMonth_tbutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    populateRecycleView("thisMonth");
                    updateAmountTextViews("thisMonth");
                    updateSelectedDate(null);

                }
            }
        });

        all_tbutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    populateRecycleView(null);
                    updateAmountTextViews("");
                    updateSelectedDate(null);

                }
            }
        });

    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String interval = "";
            if(today_tbutton.isChecked())
                interval = "today";
            if(thisMonth_tbutton.isChecked())
                interval = "thisMonth";
            if(all_tbutton.isChecked())
                interval = "";

            updateAmountTextViews(interval);

            Cursor data = dataBase.getContent("Expenses", interval);
            if(data != null && data.getCount() == 0){
                empty_state_layout.setVisibility(ConstraintLayout.VISIBLE);
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(RecyclerViewCustomAdapter.BROADCAST_FILTER));
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
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

    private void updateAmountTextViews(String interval){
        float amount = dataBase.getAmount(interval, "");
        float cash_amount = dataBase.getAmount(interval, "cash");
        float card_amount = dataBase.getAmount(interval, "card");

        if(amount == (int)amount){
            expenses_amount.setText((int)amount + " RON");
        }
        else
            expenses_amount.setText(amount + " RON");

        if(cash_amount == (int)cash_amount){
            cash_amount_text.setText((int)cash_amount + " RON");
        }
        else
            cash_amount_text.setText(cash_amount + " RON");

        if(card_amount == (int)card_amount){
            card_amount_text.setText((int)card_amount + " RON");
        }
        else
            card_amount_text.setText(card_amount + " RON");

    }

    private void updateSelectedDate(String date){
        if(date == null)
            selected_date.setText("");
        else
            selected_date.setText(date);
    }

}
