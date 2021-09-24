package com.mwewghwai.moneyspend_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;



public class SetCategories extends SettingsActivity {

//Item declaration
    DatabaseHelper dataBase;
    Button back_button;
    Button add_category_button;
    ListView category_add_list;

//Variables
    final ArrayList<String> category_array = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.set_categories);

//Variables
        final ArrayAdapter category_list_adapter = new ArrayAdapter(this,R.layout.textcenter_listview,category_array);


//Item link
        dataBase = new DatabaseHelper(this);
        add_category_button = findViewById(R.id.add_category_button);
        category_add_list = findViewById(R.id.category_add_list);
        back_button = findViewById(R.id.set_categories_back_button);

        updateCategoryList();

//BackButton
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//AddButton
        add_category_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SetCategories.this, AddCategory.class));
            }
        });

        category_add_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,final int position, long id) {
//ToDo:design for delete category dialog
                final Object item = category_list_adapter.getItem(position);

                AlertDialog.Builder dialog = new AlertDialog.Builder(SetCategories.this)
                        .setTitle("Delete " + item.toString())
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dataBase.removeFromCategories(item.toString());
                                Log.d("DataBase", "Deleted " + item.toString() + " from Category table");

                                updateCategoryList();
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
    protected void onResume(){
        super.onResume();
        updateCategoryList();
    }

    public void updateCategoryList(){
        final ArrayAdapter category_list_adapter = new ArrayAdapter(this,R.layout.textcenter_listview,category_array);
        category_array.clear();
        //DataBase -> List
        Cursor data = dataBase.getContent("Categories", null);
        while(data.moveToNext()){
            category_array.add(data.getString(1));
        }
        category_add_list.setAdapter(category_list_adapter);
    }

}
