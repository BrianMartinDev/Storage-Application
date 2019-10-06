package com.hfad.pack_things;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeleteActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editId;
    Button btnDeleteData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        myDb = new DatabaseHelper(this);

        editId = (EditText) findViewById(R.id.editText_Id);


        btnDeleteData = (Button) findViewById(R.id.btnDelete);
        deleteData();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent home = new Intent(DeleteActivity.this, MainActivity.class);
                        startActivity(home);
                        break;
                    case R.id.navigation_dashboard:
                        Intent add = new Intent(DeleteActivity.this, CreateActivity.class);
                        startActivity(add);
                        break;
                    case R.id.navigation_notifications:
                        Intent delete = new Intent(DeleteActivity.this, DeleteActivity.class);
                        startActivity(delete);
                        break;
                }
                return false;
            }
        });
    }

    public void deleteData() {
        btnDeleteData.setOnClickListener(
                new View.OnClickListener() {
                    /***
                     *
                     * @param view
                     */
                    @Override
                    public void onClick(View view) {
                        Integer isDeleted = myDb.deleteData(editId.getText().toString());

                        if (isDeleted > 0)
                            Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(), "Data not Deleted ", Toast.LENGTH_SHORT).show();

                        Intent home = new Intent(DeleteActivity.this, MainActivity.class);
                        startActivity(home);

                    }
                }
        );


    }


}


