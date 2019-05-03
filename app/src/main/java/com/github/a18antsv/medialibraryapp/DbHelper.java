package com.github.a18antsv.medialibraryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        db.execSQL(CREATE_PERSON_TABLE);
        db.execSQL(CREATE_ROLE_TABLE);
        db.execSQL(CREATE_PERSONROLE_TABLE);
        db.execSQL(CREATE_MOVIE_TABLE);
        db.execSQL(CREATE_BOOK_TABLE);
        db.execSQL(CREATE_GAME_TABLE);
        db.execSQL(CREATE_SONG_TABLE);

        insertList(db, "Movies");
        insertList(db, "Books");
        insertList(db, "Games");
        insertList(db, "Songs");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_LIST_TABLE);
        db.execSQL(DROP_PRODUCT_TABLE);
        db.execSQL(DROP_PERSON_TABLE);
        db.execSQL(DROP_ROLE_TABLE);
        db.execSQL(DROP_PERSONROLE_TABLE);
        db.execSQL(DROP_MOVIE_TABLE);
        db.execSQL(DROP_BOOK_TABLE);
        db.execSQL(DROP_GAME_TABLE);
        db.execSQL(DROP_SONG_TABLE);
        onCreate(db);
    }

    public boolean insertList(SQLiteDatabase db, String name) {
        ContentValues values = new ContentValues();
        values.put(LIST_COL_NAME, name);
        long result = db.insert(LIST_TABLE_NAME, null, values);
        return (result == -1) ? false : true;
    }
}