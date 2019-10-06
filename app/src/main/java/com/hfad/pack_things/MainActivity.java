package com.hfad.pack_things;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ListView lvItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent home = new Intent(MainActivity.this, CreateActivity.class);
                        startActivity(home);
                        break;
                    case R.id.navigation_dashboard:
                        Intent add = new Intent(MainActivity.this, CreateActivity.class);
                        startActivity(add);
                        break;
                    case R.id.navigation_notifications:
                        Intent delete = new Intent(MainActivity.this, DeleteActivity.class);
                        startActivity(delete);
                        break;
                }
                return false;
            }
        });

        DatabaseHelper myDb = new DatabaseHelper(this);

        ArrayList<HashMap<String, String>> userList = myDb.getAllData();

        lvItems = (ListView) findViewById(R.id.box_list);

        ListAdapter adapter = new SimpleAdapter(
                MainActivity.this,
                userList, R.layout.list_row, new String[]{"room", "id", "item"},
                new int[]{R.id.location, R.id.box_id, R.id.item});


        lvItems.setAdapter(adapter);


    }

}
