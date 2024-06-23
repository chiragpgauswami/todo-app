package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // List View object
    ListView listView;

    // Define array adapter for ListView
    ArrayAdapter<String> adapter;

    // Define array List for List View data
    ArrayList<String> mylist;

    Button add,update, clear;
    EditText task;
    int position;

    private void saveData(){
        // Creating a shared pref object with a file name "MySharedPref" in private mode
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        // creating a new variable for gson.
        Gson gson = new Gson();

        // getting data from gson and storing it in a string.
        String json = gson.toJson(mylist);

        // write all the data entered by the user in SharedPreference and apply
        myEdit.putString("todo", json);
        myEdit.apply();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        saveData();

        Intent it;
        CharSequence title = item.getTitle();
        if (title.equals("Todo List")) {
            it = new Intent(this, MainActivity.class);
            startActivity(it);
        } else if (title.equals("About")) {
            it = new Intent(this, AboutActivity.class);
            startActivity(it);
        } else if (title.equals("Log Out")) {

            // Creating a shared pref object with a file name "MySharedPref" in private mode
            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();

            // write all the data entered by the user in SharedPreference and apply
            myEdit.putBoolean("isLoggedIn", false);
            myEdit.apply();
            Toast.makeText(this, "Sucessfully Logged Out!", Toast.LENGTH_SHORT).show();

            it = new Intent(this, LoginActivity.class);
            startActivity(it);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // method to load arraylist from shared prefs
        // initializing our shared prefs with name as
        // shared preferences.
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        Boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if(!isLoggedIn) {
            Intent it=new Intent(this, LoginActivity.class);
            startActivity(it);
        }

        setContentView(R.layout.activity_main);


        getSupportActionBar().setTitle("Todo List");



        // creating a variable for gson.
        Gson gson = new Gson();

        // below line is to get to string present from our
        // shared prefs if not present setting it as null.
        String json = sharedPreferences.getString("todo", null);

        // below line is to get the type of our array list.
        Type type = new TypeToken<ArrayList<String>>() {}.getType();

        // in below line we are getting data from gson
        // and saving it to our array list
        mylist = gson.fromJson(json, type);

        // checking below if the array list is empty or not
        if (mylist == null) {
            // if the array list is empty
            // creating a new array list.
            mylist = new ArrayList<>();
        }

        add = findViewById(R.id.addBtn);
        update = findViewById(R.id.updateBtn);
        clear = findViewById(R.id.clearBtn);
        task = findViewById(R.id.task);

        // initialise ListView with id
        listView = findViewById(R.id.todoList);


        // Set adapter to ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, mylist);
        listView.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (task.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Please Enter Task ", Toast.LENGTH_SHORT).show();
                }else{
                    mylist.add(task.getText().toString());
                    adapter.notifyDataSetChanged();
                    task.setText("");
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
                task.setText(mylist.get(i));
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(mylist.isEmpty()){
                    Toast.makeText(MainActivity.this, "No Task Found", Toast.LENGTH_SHORT).show();
                } else {
                    mylist.remove(i);
                    adapter.notifyDataSetChanged();
                }
                return false;
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (task.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Please Enter Task ", Toast.LENGTH_SHORT).show();
                } else{
                    if (mylist.isEmpty()){
                        mylist.add(task.getText().toString());
                        task.setText("");
                    } else{
                        mylist.set(position,task.getText().toString());
                        task.setText("");
                    }
                }

            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mylist.clear();
                adapter.notifyDataSetChanged();
            }
        });
        
    }


    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu with items using MenuInflator
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        // Initialise menu item search bar
        // with id and take its object
        MenuItem searchViewItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);

        // attach setOnQueryTextListener
        // to search view defined above
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // Override onQueryTextSubmit method which is call when submit query is searched
            @Override
            public boolean onQueryTextSubmit(String query) {
                // If the list contains the search query than filter the adapter
                // using the filter method with the query as its argument
                if (mylist.contains(query)) {
                    adapter.getFilter().filter(query);
                } else {
                    // Search query not found in List View
                    Toast.makeText(MainActivity.this, "Not found", Toast.LENGTH_LONG).show();
                }
                return false;
            }

            // This method is overridden to filter the adapter according
            // to a search query when the user is typing search
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}