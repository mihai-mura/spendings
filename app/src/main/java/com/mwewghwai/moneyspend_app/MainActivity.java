package com.mwewghwai.moneyspend_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

//Item declaration
    DatabaseHelper dataBase;
    BottomSheetBehavior add_popup;
    BottomSheetBehavior category_popup;
    Button add_button;
    Button category_button;
    Button settings_button;
    Button expenses_button;
    Button expenses_add_button;
    ToggleButton cash_button;
    ToggleButton card_button;
    ListView category_list;
    TextView category_header_text;
    EditText amount_add;
    EditText note_add;
    TextView monthly_spent_text;

//Variables


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);

//Variables
        final ArrayList<String> category_array = new ArrayList<>();
        final ArrayAdapter category_list_adapter = new ArrayAdapter(this,R.layout.textcenter_listview,category_array);


//Item link
        dataBase = new DatabaseHelper(this);
        add_button = findViewById(R.id.add);
        category_button = findViewById(R.id.category_button);
        add_popup = BottomSheetBehavior.from(findViewById(R.id.add_bottom_sheet_root_layout));
        category_popup = BottomSheetBehavior.from(findViewById(R.id.category_bottom_sheet_root_layout));
        category_list = findViewById(R.id.category_list);
        settings_button = findViewById(R.id.settings);
        expenses_add_button = findViewById(R.id.expenses_add_button);
        cash_button = findViewById(R.id.cash_button);
        card_button = findViewById(R.id.card_button);
        amount_add = findViewById(R.id.introduce_amount);
        note_add = findViewById(R.id.introduce_note);
        expenses_button = findViewById(R.id.expenses);
        monthly_spent_text = findViewById(R.id.monthly_spend_text);

//Initializations
        add_popup.setState(BottomSheetBehavior.STATE_COLLAPSED);
        category_popup.setState(BottomSheetBehavior.STATE_COLLAPSED);
        cash_button.setChecked(false);
        cash_button.setClickable(true);
        card_button.setChecked(false);
        card_button.setClickable(true);

        updateAmountTextView("thisMonth");

//Buttons

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //DataBase -> List
                category_array.clear();
                Cursor data = dataBase.getContent("Categories", null);
                while(data.moveToNext()){
                    category_array.add(data.getString(1));
                }
                category_list.setAdapter(category_list_adapter);

                updateCategoryHeader();

                add_popup.setState(BottomSheetBehavior.STATE_EXPANDED);

            }
        });

        //listItemClick
        category_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Object item = category_list_adapter.getItem(position);
                category_button.setText(item.toString());
                category_popup.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        cash_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card_button.setChecked(false);
                cash_button.setClickable(false);
                card_button.setClickable(true);
            }
        });

        card_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cash_button.setChecked(false);
                card_button.setClickable(false);
                cash_button.setClickable(true);
            }
        });


        category_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category_popup.setState(BottomSheetBehavior.STATE_EXPANDED);

            }
        });

        expenses_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean type = false;
                float amount;
                String category;
                String note;
                String date;
                String time;

                if(cash_button.isChecked()){
                    type = false;
                }
                else if(card_button.isChecked())
                    type = true;

                if((cash_button.isChecked() || card_button.isChecked()) && category_button.getText() != "Category" && amount_add.getText().toString() != "") {

                    amount = Float.valueOf(amount_add.getText().toString());
                    category = String.valueOf(category_button.getText());
                    note = note_add.getText().toString();
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat format_date = new SimpleDateFormat("d MMM yyyy");
                    SimpleDateFormat format_time = new SimpleDateFormat("HH:mm");
                    date = format_date.format(calendar.getTime());
                    time = format_time.format(calendar.getTime());

                    addData(type, amount, category, note, date, time);

                    //reinitiate bottom sheet components
                    cash_button.setChecked(false);
                    cash_button.setClickable(true);
                    card_button.setChecked(false);
                    card_button.setClickable(true);
                    amount_add.setText("");
                    category_button.setText("Category");
                    note_add.setText("");

                    //ToDo:fix bottom sheet closing
                    add_popup.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    hideKeyboard(MainActivity.this);
                    add_popup.setState(BottomSheetBehavior.STATE_COLLAPSED);

                }
                else{
                    //ChangeToasts
                    Toast.makeText(MainActivity.this, "Enter all fields!", Toast.LENGTH_LONG).show();
                }

            }
        });

        expenses_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ExpensesActivity.class));
            }
        });

        settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });

    }

    private void addData(boolean type, float amount, String category, String note, String date, String time){
        boolean insertData = dataBase.addDataExpenses(type, amount, category, note, date, time);
        if(insertData == true){
            Log.d("DataBase", "Inserted to Expenses table: type: " + type + ", amount: " + amount + ", category: " + category + ", note: " + note + ", date and time: " + date + " "+ time);

        }
        else{
            Log.d("DataBase", "Data insertion to Expenses table failed");

        }

    }


    private void updateAmountTextView(String interval){
        float amount = dataBase.getAmount(interval);
        if(amount == (int)amount){
            monthly_spent_text.setText((int)amount + " RON");
        }
        else
            monthly_spent_text.setText(amount + " RON");

    }

    private void updateCategoryHeader(){
        category_header_text = findViewById(R.id.category_header_text);
        if(dataBase.isEmpty("Categories")){
            category_header_text.setText("No categories added");
        }
        else{
            category_header_text.setText("Select category");
        }
    }

    @Override
    public void onBackPressed() {
        add_popup = BottomSheetBehavior.from(findViewById(R.id.add_bottom_sheet_root_layout));
        category_popup = BottomSheetBehavior.from(findViewById(R.id.category_bottom_sheet_root_layout));
        if(category_popup.getState() == BottomSheetBehavior.STATE_EXPANDED){
            category_popup.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        else if (add_popup.getState() == BottomSheetBehavior.STATE_EXPANDED){
            add_popup.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        else{
            super.onBackPressed();

        }

    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
