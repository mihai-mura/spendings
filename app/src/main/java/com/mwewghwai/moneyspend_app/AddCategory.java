package com.mwewghwai.moneyspend_app;

import android.os.Bundle;
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
                AddData(category);
                finish();
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
    public void AddData(String category){
        boolean insertData = dataBase.addDataTable1(category);
        if(insertData == true){
//TO CHANGE TO LOG
            Toast.makeText(AddCategory.this, "Data inserted", Toast.LENGTH_SHORT).show();
        }
        else{
//TO CHANGE TO LOG
            Toast.makeText(AddCategory.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
    }
}
