package com.mwewghwai.moneyspend_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    BottomSheetBehavior add_popup;
    BottomSheetBehavior category_popup;
    Button add_button;
    Button category_button;
    Button settings_button;
    ListView category_list;
    TextView category_header_text;

//Variables
    ArrayList<String> category_array = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
//Item link
        add_button = findViewById(R.id.add);
        category_button = findViewById(R.id.category_button);
        add_popup = BottomSheetBehavior.from(findViewById(R.id.add_bottom_sheet_root_layout));
        category_popup = BottomSheetBehavior.from(findViewById(R.id.category_bottom_sheet_root_layout));
        category_list = findViewById(R.id.category_list);
        settings_button = findViewById(R.id.settings);

//Initializations
        add_popup.setState(BottomSheetBehavior.STATE_COLLAPSED);
        category_popup.setState(BottomSheetBehavior.STATE_COLLAPSED);

//Category list adapter
        ArrayAdapter category_array_adapter = new ArrayAdapter(this,R.layout.textcenter_listview,category_array);
        category_list.setAdapter(category_array_adapter);

//Buttons
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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



        category_array.add("Gas");updateCategoryHeader();
        category_array.add("Food");updateCategoryHeader();
        category_array.add("Clothes");updateCategoryHeader();


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

    public void updateCategoryHeader(){
        category_header_text = findViewById(R.id.category_header_text);
        if(category_array.isEmpty()){
            category_header_text.setText("No categories added");
        }
        else
            category_header_text.setText("Select category");
    }


}
