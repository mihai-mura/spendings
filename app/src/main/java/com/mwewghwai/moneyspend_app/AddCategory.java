package com.mwewghwai.moneyspend_app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
//ToDo:better design for toasts
                    Toast.makeText(AddCategory.this, "Field empty!", Toast.LENGTH_LONG).show();
                }
                else{
                    if(AddData(category) == true){
                        finish();
                    }
                    else{
                        Toast.makeText(AddCategory.this, "Category already exists", Toast.LENGTH_LONG).show();

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

//ToDo:better design for toasts
    //AddData
    private boolean AddData(String category){
        boolean itemExists = dataBase.itemExistsInCategories(category);

        if(itemExists == false){
            boolean insertData = dataBase.addDataCategories(category);
            if(insertData == true){
                Log.d("DataBase", "Inserted " + category + " to Category table");
                Toast.makeText(AddCategory.this, "Data inserted", Toast.LENGTH_SHORT).show();

                return true;
            }
            else{
                Log.d("DataBase", "Data insertion to Category table failed");
                Toast.makeText(AddCategory.this, "Something went wrong!", Toast.LENGTH_SHORT).show();

                return false;
            }

        }
        else
            return false;

    }
}
