package com.mwewghwai.moneyspend_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;



public class SetCategories extends SettingsActivity {

//Item declaration
    DatabaseHelper dataBase;
    Button add_category_button;
    ListView category_add_list;

//Variables
    public ArrayList<String> category_array_set_cat = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.set_categories);

//Item link
        dataBase = new DatabaseHelper(this);
        add_category_button = findViewById(R.id.add_category_button);
        category_add_list = findViewById(R.id.category_add_list);

//ToDo:make separate methode so the list updates when you are on the page
//DataBase -> List
        Cursor data = dataBase.getContentTable1();
        while(data.moveToNext()){
            category_array_set_cat.add(data.getString(1));
        }
        final ArrayAdapter category_add_list_adapter = new ArrayAdapter(this,R.layout.textcenter_listview,category_array_set_cat);
        category_add_list.setAdapter(category_add_list_adapter);

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
                final Object item = category_add_list_adapter.getItem(position);

                AlertDialog.Builder dialog = new AlertDialog.Builder(SetCategories.this)
                        .setTitle("Delete category")
                        .setMessage("Delete " + item.toString())
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dataBase.removeCategory(item.toString());
                                Log.d("DataBase", "Deleted " + item.toString() + " from Category table");
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

}
