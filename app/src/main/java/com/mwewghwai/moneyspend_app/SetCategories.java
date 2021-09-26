package com.mwewghwai.moneyspend_app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import java.util.ArrayList;



public class SetCategories extends SettingsActivity {

//Item declaration
    DatabaseHelper dataBase;
    Button back_button;
    Button add_category_button;
    ListView category_add_list;
    EditText category_to_add;

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
        category_to_add = findViewById(R.id.category_to_add);

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
                String category = category_to_add.getText().toString();
                if(category.matches("")){
                    //ChangeToasts
                    Toast.makeText(SetCategories.this, "Field empty!", Toast.LENGTH_LONG).show();
                }
                else{
                    if(addData(category) == true){
                        updateCategoryList();
                        category_to_add.setText("");

                        hideKeyboard(SetCategories.this);

                    }
                    else{
                        //ChangeToasts
                        Toast.makeText(SetCategories.this, "Category already exists!", Toast.LENGTH_LONG).show();
                    }

                }
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

    //AddData
    private boolean addData(String category){
        boolean itemExists = dataBase.itemExistsInCategories(category);

        if(itemExists == false){
            boolean insertData = dataBase.addDataCategories(category);
            if(insertData == true){
                Log.d("DataBase", "Inserted to Category table: " + category);

                return true;
            }
            else{
                Log.d("DataBase", "Data insertion to Category table failed");
                //ChangeToasts
                Toast.makeText(SetCategories.this, "Something went wrong!", Toast.LENGTH_LONG);

                return false;
            }

        }
        else
            return false;

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
