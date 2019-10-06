package com.hfad.pack_things;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


public class CreateActivity extends AppCompatActivity {

    Intent intent;
    DatabaseHelper myDb;
    EditText editId, editLocation, editItem;
    Button btnAddData, btnViewData, btnUpdateData, btnDeleteData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        myDb = new DatabaseHelper(this);

//        editId = (EditText)findViewById(R.id.editText_Id);
        editLocation = (EditText)findViewById(R.id.editText_Location);
//        editBox = (EditText)findViewById(R.id.editText_BoxNum);
        editItem = (EditText) findViewById(R.id.editText_Item);
        btnAddData = (Button) findViewById(R.id.btnAdd);
//        btnViewData = (Button) findViewById(R.id.btnView);
//        btnUpdateData = (Button) findViewById(R.id.btnUpdate);
//        btnDeleteData = (Button) findViewById(R.id.btnDelete);
        AddData();
//        viewData();
//        updateData();
//        deleteData();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_home:

                        Intent home = new Intent(CreateActivity.this,MainActivity.class);
                        startActivity(home);
                        break;
                    case R.id.navigation_dashboard:
                        Intent add = new Intent(CreateActivity.this,CreateActivity.class);
                        startActivity(add);
                        break;
                    case R.id.navigation_notifications:
                        Intent delete = new Intent(CreateActivity.this,DeleteActivity.class);
                        startActivity(delete);
                        break;
                }
                return false;
            }
        });
    }



    public void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    /***
                     *
                     * @param view
                     */
                    @Override
                    public void onClick(View view) {

                        String location = editLocation.getText().toString()+"\n";
                        //String box = editBox.getText().toString();
                        String item = editItem.getText().toString();

                        DatabaseHelper databaseHelper = new DatabaseHelper(CreateActivity.this);
                        databaseHelper.insertDetails(location, item);

                        Toast.makeText(getApplicationContext(), "Details Inserted Successfully",Toast.LENGTH_SHORT).show();
                        Intent home = new Intent(CreateActivity.this,MainActivity.class);
                        startActivity(home);
                    }
                }
        );
    }

    /***
     *
     */
    public void updateData() {
        btnUpdateData.setOnClickListener(
                new View.OnClickListener() {

                    /***
                     *
                     * @param view
                     */
                    @Override
                    public void onClick(View view) {

                        boolean isUpdated = myDb.updateData(
                                editId.getText().toString(),
                                editLocation.getText().toString(),
                                editItem.getText().toString());

                        if (isUpdated == true)
                            Toast.makeText(getApplicationContext(), "Data Updated Successfully",Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(), "Data not Updated ",Toast.LENGTH_SHORT).show();
                    }
                }
        );

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
                            Toast.makeText(getApplicationContext(), "Deleted Successfully",Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(), "Data not Deleted ",Toast.LENGTH_SHORT).show();

                    }
                }
        );


    }


    public void viewData() {
        btnViewData.setOnClickListener(
                new View.OnClickListener() {
                    /***
                     *
                     * @param view
                     */
                    @Override
                    public void onClick(View view) {
                        ArrayList<HashMap<String, String>> userList = myDb.getAllData();
                        intent = new Intent(CreateActivity.this, MainActivity.class);
                        startActivity(intent);
//                        if (res.getCount() == 0) {
//                            // show message
//                            showMessage("Error", "Nothing Found");
//                            return;
//
//
//                        }
//                        StringBuffer buffer = new StringBuffer();
//                        while (res.moveToNext()) {
//                            // Return id column starting at index 0
//                            buffer.append("id :" + res.getString(0) + "\n");
//                            buffer.append("location :" + res.getString(1));
//                            buffer.append("box :" + res.getString(2) + "\n");
//                            buffer.append("item :" + res.getString(3) + "\n\n");
//                        }
//                        //Show all data
//                        showMessage("Data", buffer.toString());
                    }
                }
        );
    }

    /***
     *
     * @param title
     * @param message
     */
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }


}
