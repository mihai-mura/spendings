package com.mwewghwai.moneyspend_app;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


public class SetCategories extends SettingsActivity {

//Item declaration
    Button add_category_button;
    ListView category_add_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.set_categories);

//Item link
        add_category_button = findViewById(R.id.add_category_button);
        category_add_list = findViewById(R.id.category_add_list);

//ListAdapter
        ArrayAdapter category_add_list_adapter = new ArrayAdapter(this,R.layout.textcenter_listview,category_array);
        category_add_list.setAdapter(category_add_list_adapter);

//AddButton
        add_category_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        category_add_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}
