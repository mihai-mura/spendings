package com.mwewghwai.moneyspend_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

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
    Button spent_add_button;
    ToggleButton cash_button;
    ToggleButton card_button;
    ListView category_list;
    TextView category_header_text;
    EditText ammount_add;
    EditText note_add;

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
        spent_add_button = findViewById(R.id.spent_add_button);
        cash_button = findViewById(R.id.cash_button);
        card_button = findViewById(R.id.card_button);
        ammount_add = findViewById(R.id.introduce_ammount);
        note_add = findViewById(R.id.introduce_note);

//Initializations
        add_popup.setState(BottomSheetBehavior.STATE_COLLAPSED);
        category_popup.setState(BottomSheetBehavior.STATE_COLLAPSED);
        cash_button.setChecked(false);
        cash_button.setClickable(true);
        card_button.setChecked(false);
        card_button.setClickable(true);

//Buttons

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //DataBase -> List
                category_array.clear();
                Cursor data = dataBase.getContentCategories();
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

        spent_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean type = false;
                float ammount;
                String category;
                String note;
                Date date;

                if(cash_button.isChecked()){
                    type = false;
                }
                else if(card_button.isChecked())
                    type = true;

                if((cash_button.isChecked() || card_button.isChecked()) && category_button.getText() != "Category" && ammount_add.getText().toString() != "") {

                    ammount = Float.valueOf(ammount_add.getText().toString());
                    category = String.valueOf(category_button.getText());
                    note = note_add.getText().toString();
                    date = Calendar.getInstance().getTime();

                    addData(type, ammount, category, note, date);

                    //reinitiate bottom sheet components
                    cash_button.setChecked(false);
                    cash_button.setClickable(true);
                    card_button.setChecked(false);
                    card_button.setClickable(true);
                    ammount_add.setText("");
                    category_button.setText("Category");
                    note_add.setText("");

                    add_popup.setState(BottomSheetBehavior.STATE_COLLAPSED);

                }
                else{
                    //ChangeToasts
                    Toast.makeText(MainActivity.this, "Enter all fields!", Toast.LENGTH_LONG).show();
                }

            }
        });

        settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });

    }

    private void addData(boolean type, float ammount, String category, String note, Date date){
        boolean insertData = dataBase.addDataSpent(type, ammount, category, note, date);
        if(insertData == true){
            Log.d("DataBase", "Inserted to Spent table: type: " + type + ", ammount: " + ammount + ", category: " + category + ", note: " + note + ", date: " + date);

        }
        else{
            Log.d("DataBase", "Data insertion to Spent table failed");

        }

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
}
