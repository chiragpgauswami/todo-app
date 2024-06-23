package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity {


    EditText userName,password;
    Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // method to load arraylist from shared prefs
        // initializing our shared prefs with name as
        // shared preferences.
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        Boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if(isLoggedIn) {

            Intent it=new Intent(this, MainActivity.class);
            startActivity(it);
        }
         setContentView(R.layout.activity_login);

         getSupportActionBar().hide();

         userName = findViewById(R.id.userName);
         password = findViewById(R.id.password);
         btnLogin = findViewById(R.id.loginBtn);


         btnLogin.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 if(userName.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {


                     // Creating a shared pref object with a file name "MySharedPref" in private mode
                     SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                     SharedPreferences.Editor myEdit = sharedPreferences.edit();

                     // write all the data entered by the user in SharedPreference and apply
                     myEdit.putBoolean("isLoggedIn", true);
                     myEdit.apply();


                     Intent it=new Intent(getApplicationContext(), MainActivity.class);
                     startActivity(it);

                 } else {
                     Toast.makeText(LoginActivity.this, "Invalid User!", Toast.LENGTH_SHORT).show();
                 }

             }
         });
    }
}