package com.mwewghwai.moneyspend_app;

import android.os.Bundle;

public class SettingsActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.settings_layout);


    }

    @Override
    public void onBackPressed(){
        finish();
    }
}
