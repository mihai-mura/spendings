package com.mwewghwai.moneyspend_app;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.google.android.material.snackbar.Snackbar;

public class AddCategory extends SetCategories {

    DatabaseHelper dataBase;
    EditText category_to_add;
    Button cancel;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.add_category_layout);

        dataBase = new DatabaseHelper(this);
        category_to_add = findViewById(R.id.category_to_add);
        cancel = findViewById(R.id.category_add_cancel_button);
        add = findViewById(R.id.category_add_add_button);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = category_to_add.getText().toString();
                if(category.matches("")){
                    Snackbar.make(findViewById(R.id.add_category_layout_root), "Field empty!", Snackbar.LENGTH_LONG).show();
                }
                else{
                    if(AddData(category) == true){
                        finish();
                    }
                    else{
                        //snackbar top
                        Snackbar snack = Snackbar.make(findViewById(R.id.add_category_layout_root), "Category already exists!", Snackbar.LENGTH_LONG);
                        View view = snack.getView();
                        FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
                        params.gravity = Gravity.TOP;
                        view.setLayoutParams(params);
                        snack.show();
                    }

                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    //AddData
    private boolean AddData(String category){
        boolean itemExists = dataBase.itemExistsInCategories(category);

        if(itemExists == false){
            boolean insertData = dataBase.addDataCategories(category);
            if(insertData == true){
                Log.d("DataBase", "Inserted " + category + " to Category table");

                return true;
            }
            else{
                Log.d("DataBase", "Data insertion to Category table failed");
                Snackbar.make(findViewById(R.id.add_category_layout_root), "Something went wrong!", Snackbar.LENGTH_LONG).show();


                return false;
            }

        }
        else
            return false;

    }
}
