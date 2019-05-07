package com.github.a18antsv.medialibraryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import static com.github.a18antsv.medialibraryapp.DataContract.*;
import static com.github.a18antsv.medialibraryapp.DataContract.Entry.*;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "medialibrary.db";
    public static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LIST_TABLE);
        db.execSQL(CREATE_PRODUCT_TABLE);
        db.execSQL(CREATE_LISTHASPRODUCT_TABLE);
        db.execSQL(CREATE_PERSON_TABLE);
        db.execSQL(CREATE_ROLE_TABLE);
        db.execSQL(CREATE_PERSONROLE_TABLE);
        db.execSQL(CREATE_MOVIE_TABLE);
        db.execSQL(CREATE_BOOK_TABLE);
        db.execSQL(CREATE_GAME_TABLE);
        db.execSQL(CREATE_SONG_TABLE);

        /*insertList(db, "Movies");
        insertList(db, "Books");
        insertList(db, "Games");
        insertList(db, "Songs");
        insertList(db, "Favourite songs");
        insertList(db, "Best movies");*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_LIST_TABLE);
        db.execSQL(DROP_PRODUCT_TABLE);
        db.execSQL(DROP_LISTHASPRODUCT_TABLE);
        db.execSQL(DROP_PERSON_TABLE);
        db.execSQL(DROP_ROLE_TABLE);
        db.execSQL(DROP_PERSONROLE_TABLE);
        db.execSQL(DROP_MOVIE_TABLE);
        db.execSQL(DROP_BOOK_TABLE);
        db.execSQL(DROP_GAME_TABLE);
        db.execSQL(DROP_SONG_TABLE);
        onCreate(db);
    }

    public boolean insertList(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LIST_COL_NAME, name);
        long result = db.insert(LIST_TABLE_NAME, null, values);
        return (result == -1) ? false : true;
    }

    public int updateList(String newListName, String oldListName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LIST_COL_NAME, newListName);
        return db.update(LIST_TABLE_NAME, values, LIST_COL_NAME + "=?", new String[] {oldListName});
    }

    //Update to affect a lists content later
    public int deleteList(String listName) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(LIST_TABLE_NAME, LIST_COL_NAME + "=?", new String[] {listName});
    }

    public int deleteProductFromList(int productkey, String listname) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(LISTHASPRODUCT_TABLE_NAME, FOREIGNKEY_COL_PRODUCTKEY + "=? AND " + FOREIGNKEY_COL_LISTNAME + "=?", new String[] {String.valueOf(productkey), listname});
    }

    public boolean duplicateData(String table, String column, String value) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + table + " WHERE " + column + "=?", new String[] {value});
        if(c.getCount() <= 0) {
            c.close();
            return false;
        }
        c.close();
        return true;
    }




    public Cursor getData(String query, String[] selectionArgs) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(query, selectionArgs);
    }

    public Cursor getAllData(String table) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + table, null);
        return data;
    }
}