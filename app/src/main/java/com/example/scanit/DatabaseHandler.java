package com.example.scanit;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "historyManager.db";
    private static final int DATABASE_VERSION = 1;
    public static final String  TABLE_HISTORY = "historyTable";
    public static final String KEY_DATA = "data";
    public static final String KEY_TIMESTAMP = "scan_date";
    private Context context;

    public DatabaseHandler(Context context) {
        /*
         *  Constructor
         */
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
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
                + KEY_TIMESTAMP + " TEXT " + ")";
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
        // Initialize Columns
        String[] columns = new String[] {KEY_DATA, KEY_TIMESTAMP};

        // Initialize list
        List<HistoryElement> result = new ArrayList<HistoryElement>();

        String selectQuery = "SELECT  * FROM " + TABLE_HISTORY + " ORDER BY " +
                KEY_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through the obtained result.
        while(cursor.moveToNext())
        {
            HistoryElement new_Element = new HistoryElement();
            new_Element.setData(cursor.getString(cursor.getColumnIndex(KEY_DATA)));
            new_Element.setTime_stamp(cursor.getString(cursor.getColumnIndex(KEY_TIMESTAMP)));
            result.add(new_Element);
        }
        return result;
    }
}
