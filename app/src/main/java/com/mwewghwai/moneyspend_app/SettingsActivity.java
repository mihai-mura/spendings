package com.mwewghwai.moneyspend_app;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class SettingsActivity extends AppCompatActivity {

//Item declaration
    Button back_button;
    Switch dark_theme_switch;
    ConstraintLayout set_categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.settings_layout);

//Item link
        back_button = findViewById(R.id.settings_back_button);
        dark_theme_switch = findViewById(R.id.dark_theme_switch);
        set_categories = findViewById(R.id.set_categories);
//Initialization
        dark_theme_switch.setChecked(false);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//Theme Switch
        dark_theme_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                }
                else{

                }
            }
        });

//Categories
        set_categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, SetCategories.class));
            }
        });

    }

}
