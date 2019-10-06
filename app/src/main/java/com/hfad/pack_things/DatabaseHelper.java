package com.hfad.pack_things;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version refreshing the database tables.

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "finaldb";
    private static final String TABLE_NAME = "boxdetails";
    private static final String KEY_ID = "id";
    private static final String COLUMN_NAME_ROOM = "room";
    private static final String COLUMN_NAME_BOX = "box";
    private static final String COLUMN_NAME_ITEM = "item";
    /**
     * This is a method description that is
     * long enough to exceed right margin.
     * <p>
     * Another paragraph of the description
     * placed after blank line.
     * <p/>
     * Line with manual line feed.
     *
     * @param i                  short named
     *                           parameter description
     * @param longParameterName  long named
     *                           parameter description
     * @param missingDescription
     * @return return description.
     * @throws XXXException description.
     * @throws YException   description.
     * @throws ZException
     * @invalidTag
     */

    /***
     * Create a helper object to create, open, and/or manage a database.
     * @param context to use for locating paths to the the database This value may be null.
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /***
     * Creates an SQLite Database and executes tables
     * @param db to use for creating a database if database doesn't already exist.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME_ROOM + " TEXT,"

                + COLUMN_NAME_ITEM + " TEXT" + ")";

        db.execSQL(SQL_CREATE_TABLE);
    }

    /***
     * Upgrades database if version number is incremented
     *
     * @param db to use for checking a database if database doesn't already exist.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        // Drop older table if exist
        db.execSQL(SQL_DELETE_TABLE);
        // Create tables again
        onCreate(db);
    }


    /***
     * CRUD (Create, Read, Update, Delete) Operations
     * @param description insert object.
     * @param item insert object.
     */
    void insertDetails(String description, String item) {

        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        //Create a new map of values, where column names are the keys
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_ROOM, description);
        //contentValues.put(COLUMN_NAME_BOX, box);
        contentValues.put(COLUMN_NAME_ITEM, item);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_NAME, null, contentValues);
        db.close();

    }

    /***
     * Define a projection that specifies which columns from the database you will actually use after this query.
     *
     * @return
     */
    public ArrayList<HashMap<String, String>> getAllData() {

        SQLiteDatabase db = this.getWritableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                KEY_ID,
                COLUMN_NAME_ROOM,
                COLUMN_NAME_BOX,
                COLUMN_NAME_ITEM
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = COLUMN_NAME_ROOM + " = ?";
        //String[] selectionArgs = {String.valueOf(userid)};

        // How you want the results sorted in the resulting Cursor
        String sortOrder = COLUMN_NAME_ROOM + " DESC";

//        Cursor cursor = db.query(
//                TABLE_NAME,          // The table to query
//                projection,             // The array of columns to return (pass null to get all)
//                selection,          // The columns for the WHERE clause
//                selectionArgs,         // The values for the WHERE clause
//                null,               // don't group the rows
//                null,        // don't filter by row groups
//                sortOrder             // The sort order
//        );

        String query = "SELECT room, id, item FROM " + TABLE_NAME;


        ArrayList<HashMap<String, String>> userList = new ArrayList<>();

        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> user = new HashMap<>();
            user.put("room", cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ROOM)));
            user.put("id", cursor.getString(cursor.getColumnIndex(KEY_ID)));
            user.put("item", cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ITEM)));
            userList.add(user);
        }
        return userList;
//        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
//        return res;
    }

    /***
     *
     * Put information into a database
     *
     * @param id creates an id object.
     * @param description creates a description object.
     * @param item creates an item object.
     * @return
     */
    public boolean updateData(String id, String room, String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID, id);
        contentValues.put(COLUMN_NAME_ROOM, room);
        //contentValues.put(COLUMN_NAME_BOX, box);
        contentValues.put(COLUMN_NAME_ITEM, item);
        db.update(TABLE_NAME, contentValues, "id = ?", new String[]{id});

        return true;

    }

    /***
     * Put information into a database
     * @param id deletes an id object.
     * @return
     */
    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();


        return db.delete(TABLE_NAME, "id = ?", new String[]{id});
    }


}
