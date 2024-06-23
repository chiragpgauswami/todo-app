package com.example.todo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

public class AboutActivity extends MenuForAll {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().setTitle("About Me");

    }
}