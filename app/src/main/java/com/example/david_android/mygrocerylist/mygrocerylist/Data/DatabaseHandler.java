package com.example.david_android.mygrocerylist.mygrocerylist.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.SyncStateContract;
import android.util.Log;

import com.example.david_android.mygrocerylist.mygrocerylist.Model.Grocery;
import com.example.david_android.mygrocerylist.mygrocerylist.Util.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private  Context ctx;
    public DatabaseHandler(Context context)  {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_GROCERY_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants.KEY_ID + "INTEGER PRIMARY KEY," + Constants.KEY_GROCERY_ITEM + " TEXT, "
                + Constants.KEY_QTY_NUMBER + " TEXT,"
                + Constants.KEY_DATE_NAME + " LONG);";

        db.execSQL(CREATE_GROCERY_TABLE);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME );

        onCreate(db);

    }

        //**CRUD OPERATIONS: Create, read, update, and delete methods


        //Add Grocery
    public void AddGrocery(Grocery grocery) {
        SQLiteDatabase db= this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_GROCERY_ITEM, grocery.getName());
        values.put(Constants.KEY_QTY_NUMBER, grocery.getQuantity());
        values.put(Constants.KEY_DATE_NAME, java.lang.System.currentTimeMillis());

        //Insert the row
        db.insert(Constants.TABLE_NAME, null, values);

        Log.d("Saved!!", "Saved to Db");

    }

        //Get a grocery item
    private Grocery getGrocery(int id) {
        SQLiteDatabase db= this.getWritableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[] {Constants.KEY_ID, Constants.KEY_GROCERY_ITEM, Constants.KEY_QTY_NUMBER, Constants.KEY_DATE_NAME,
                Constants.KEY_ID + "=?",
                new String [] {String.valueOf(id)}, null, null, null, null);

        if(cursor !=null)
            cursor.moveToFirst();

            Grocery grocery = new Grocery();
            grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
            grocery.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_GROCERY_ITEM)));
            grocery.setQuantity(cursor.getString(cursor.getColumnIndex(Constants.KEY_QTY_NUMBER)));

            //convert timestamp into something readable
            java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
            String formatedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_NAME)))
            .getTime());

            grocery.setDateItemAdded(formatedDate);

        return grocery;
    }

        //Get all Groceries
    public List<Grocery> getAllGroceries(){
        SQLiteDatabase db = this.getReadableDatabase();

        List<Grocery> grocertList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[] {
                Constants.KEY_ID, Constants.KEY_GROCERY_ITEM, Constants.KEY_QTY_NUMBER,
                Constants.KEY_DATE_NAME}, null, null, null, null, Constants.KEY_DATE_NAME + " DESC");

        if (cursor.moveToFirst()){
            do {
                Grocery grocery = new Grocery();
                grocery.setId();

            }while(cursor.moveToNext());
        }

        return null;
    }

        //Updated groceries

    public int updateGrocery(Grocery grocery) {

        return 0;
    }

        //Delete Grocery
    public void deleteGrocery(int id){

    }
        //Get count
    public int getGroceriesCount() {

        return 0;
    }
}