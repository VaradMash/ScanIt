package com.example.scanit;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "historyManager";
    private static final int DATABASE_VERSION = 1;
    public static final String  TABLE_HISTORY = "historyTable";
    public static final String KEY_DATA = "data";
    public static final String KEY_TIMESTAMP = "timestamp";

    

    public DatabaseHandler(Context context) {
        /*
         *  Constructor
         */
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
         *  Input   :   Database
         *  Utility :   Create History table.
         *  Output  :   None
         */
        String CREATE_TABLE = "CREATE TABLE " + TABLE_HISTORY + "("
                + KEY_DATA + " TEXT, "
                + KEY_TIMESTAMP + "TEXT " + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*
         *  Input   :   Database
         *  Utility :   Drop older table if updated and recreate new table.
         *  Output  :   None
         */
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);

        // Create tables again
        onCreate(db);
    }

    public void addEntry(HistoryElement h)
    {
        /*
         *  Input   :   History Element.
         *  Utility :   Add new element to History table.
         *  Output  :   None.
         */
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATA, h.getData());
        values.put(KEY_TIMESTAMP, h.getTime_stamp());

        // Insert row
        db.insert(TABLE_HISTORY, null, values);
        // Close database connection.
        db.close();
    }

    public List<HistoryElement> getHistory()
    {
        /*
         *  Input   :   None
         *  Utility :   Get Data from database.
         *  Output  :   List.
         */
        // Initialize list
        List<HistoryElement> result = new ArrayList<HistoryElement>();

        // Initialize query
        String QUERY = "SELECT * FROM " + TABLE_HISTORY;

        // Initialize database
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery(QUERY, null);

        // Looping through the obtained result.
        if(cursor.moveToFirst())
        {
            do
            {
                HistoryElement new_Element = new HistoryElement();
                new_Element.setData(cursor.getString(0));
                new_Element.setTime_stamp(cursor.getString(1));
                result.add(new_Element);
            }while (cursor.moveToNext());
        }
        return result;
    }
}
