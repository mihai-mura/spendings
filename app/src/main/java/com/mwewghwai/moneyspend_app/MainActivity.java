package com.mwewghwai.moneyspend_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

//Item declaration
    DatabaseHelper dataBase;
    BottomSheetBehavior add_popup;
    BottomSheetBehavior category_popup;
    Button add_button;
    Button category_button;
    Button settings_button;
    ListView category_list;
    TextView category_header_text;

//Variables


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);

//Variables
        final ArrayList<String> category_array = new ArrayList<>();
        final ArrayAdapter category_add_list_adapter = new ArrayAdapter(this,R.layout.textcenter_listview,category_array);


//Item link
        dataBase = new DatabaseHelper(this);
        add_button = findViewById(R.id.add);
        category_button = findViewById(R.id.category_button);
        add_popup = BottomSheetBehavior.from(findViewById(R.id.add_bottom_sheet_root_layout));
        category_popup = BottomSheetBehavior.from(findViewById(R.id.category_bottom_sheet_root_layout));
        category_list = findViewById(R.id.category_list);
        settings_button = findViewById(R.id.settings);

//Initializations
        add_popup.setState(BottomSheetBehavior.STATE_COLLAPSED);
        category_popup.setState(BottomSheetBehavior.STATE_COLLAPSED);

//Buttons
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //DataBase -> List
                category_array.clear();
                Cursor data = dataBase.getContentTable1();
                while(data.moveToNext()){
                    category_array.add(data.getString(1));
                }
                category_list.setAdapter(category_add_list_adapter);

                add_popup.setState(BottomSheetBehavior.STATE_EXPANDED);

            }
        });

        category_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                category_popup.setState(BottomSheetBehavior.STATE_EXPANDED);

            }
        });

        settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });

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


//ToDo:change updateCategoryHeader for database
    public void updateCategoryHeader(){
        category_header_text = findViewById(R.id.category_header_text);
    }


}
