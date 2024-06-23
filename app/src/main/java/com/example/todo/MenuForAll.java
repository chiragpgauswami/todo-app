package com.example.todo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MenuForAll extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent it;
        switch (item.getItemId()) {
            case 1:
                 it = new Intent(this,MainActivity.class);
                startActivity(it);
                break;
            case 2:
                 it = new Intent(this,AboutActivity.class);
                startActivity(it);
                break;
            case 3:

                // Creating a shared pref object with a file name "MySharedPref" in private mode
                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();

                // write all the data entered by the user in SharedPreference and apply
                myEdit.putBoolean("isLoggedIn", false);
                myEdit.apply();
                Toast.makeText(this, "Sucessfully Logged Out!", Toast.LENGTH_SHORT).show();

                it = new Intent(this, LoginActivity.class);
                startActivity(it);
                break;
            default:
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {

        menu.add(0,1,0,"Todo List");
        menu.add(0,2,0,"About");
        menu.add(0,3,0,"Log Out");

        return super.onCreateOptionsMenu(menu);
    }




}
