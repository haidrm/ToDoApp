package com.codepath.simpletodo;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import junit.runner.Version;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haidrm on 2/15/2017.
 */

public class ItemsDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "ItemsDB";

    //Database Info
    private static final String DATABASE_NAME = "itemsDatabase";
    private static final int DATABASE_VERSION = 1;

    //Table Name
    private static final String ITEMS_TABLE = "items";

    //Table Columns
    private static final String ITEM_ID = "id";
    private static final String ITEM = "item";

    private static ItemsDatabaseHelper sInstance;

    public static synchronized ItemsDatabaseHelper getsInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ItemsDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private ItemsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ITEMS_TABLE = "CREATE TABLE " + ITEMS_TABLE +
                "(" +
                    ITEM_ID + " INTEGER PRIMARY KEY," +
                    ITEM + " TEXT" +
                ")";
        db.execSQL(CREATE_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP IF TABLE EXISTS " + ITEMS_TABLE);
            onCreate(db);
        }
    }

    public long addItem(Item item) {
        SQLiteDatabase db = getWritableDatabase();
        long ret = -1;
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            //values.put(ITEM_ID, item.getItemId());
            values.put(ITEM, item.getItemText());

            ret = db.insert(ITEMS_TABLE, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add item to database");
        } finally {
            db.endTransaction();
        }

        return ret;
    }

    public int updateItem(Item item) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ITEM, item.getItemText());

        return db.update(ITEMS_TABLE, values, ITEM_ID + "= ?",
                new String[]{ String.valueOf(item.getRowId())});
    }

    public int deleteItem(Item item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ITEM, item.getItemText());

        return db.delete(ITEMS_TABLE, ITEM_ID + " = ?",
                new String[]{ String.valueOf(item.getRowId())});
    }

    public ArrayList<Item> getAllItems() {
        ArrayList<Item> items = new ArrayList<>();

        String ITEMS_SELECT_QUERY =
                String.format("SELECT * FROM %s", ITEMS_TABLE);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(ITEMS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Item item = new Item();
                    item.setRowId(cursor.getLong(cursor.getColumnIndex(ITEM_ID)));
                    item.setItemText(cursor.getString(cursor.getColumnIndex(ITEM)));
                    items.add(item);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get items from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return items;
    }
}
