package com.github.a18antsv.medialibraryapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.github.a18antsv.medialibraryapp.database.DataContract.Entry.*;
import static com.github.a18antsv.medialibraryapp.database.DataContract.*;


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
        db.execSQL(CREATE_MOVIE_TABLE);
        db.execSQL(CREATE_BOOK_TABLE);
        db.execSQL(CREATE_GAME_TABLE);
        db.execSQL(CREATE_SONG_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_LIST_TABLE);
        db.execSQL(DROP_PRODUCT_TABLE);
        db.execSQL(DROP_LISTHASPRODUCT_TABLE);
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

    public boolean insertIntoList(int productkey, String listname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FOREIGNKEY_COL_PRODUCTKEY, productkey);
        values.put(FOREIGNKEY_COL_LISTNAME, listname);
        long result = db.insert(LISTHASPRODUCT_TABLE_NAME, null, values);
        return (result == -1) ? false : true;
    }

    public boolean insertIntoProduct(String title, int price, String release, String genre, String comment, String imgUrl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRODUCT_COL_TITLE, title);
        values.put(PRODUCT_COL_PRICE, price);
        values.put(PRODUCT_COL_RELEASE, release);
        values.put(PRODUCT_COL_GENRE, genre);
        values.put(PRODUCT_COL_COMMENT, comment);
        values.put(PRODUCT_COL_IMGURL, imgUrl);
        long result = db.insert(PRODUCT_TABLE_NAME, null, values);
        return (result == -1) ? false : true;
    }

    public boolean insertIntoBook(int productkey, String author, int pages, String type, String publisher, String isbn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FOREIGNKEY_COL_PRODUCTKEY, productkey);
        values.put(BOOK_COL_AUTHOR, author);
        values.put(BOOK_COL_PAGES, pages);
        values.put(BOOK_COL_TYPE, type);
        values.put(BOOK_COL_PUBLISHER, publisher);
        values.put(BOOK_COL_ISBN, isbn);
        long result = db.insert(BOOK_TABLE_NAME, null, values);
        return (result == -1) ? false : true;
    }

    public boolean insertIntoMovie(int productkey, int length, int age, String company, int rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FOREIGNKEY_COL_PRODUCTKEY, productkey);
        values.put(MOVIE_COL_LENGTH, length);
        values.put(MOVIE_COL_AGE, age);
        values.put(MOVIE_COL_COMPANY, company);
        values.put(MOVIE_COL_RATING, rating);
        long result = db.insert(MOVIE_TABLE_NAME, null, values);
        return (result == -1) ? false : true;
    }

    public boolean insertIntoSong(int productkey, int length, String label, String artist) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FOREIGNKEY_COL_PRODUCTKEY, productkey);
        values.put(SONG_COL_LENGTH, length);
        values.put(SONG_COL_LABEL, label);
        values.put(SONG_COL_ARTIST, artist);
        long result = db.insert(SONG_TABLE_NAME, null, values);
        return (result == -1) ? false : true;
    }

    public boolean insertIntoGame(int productkey, String platform, int age , String developer, String publisher) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FOREIGNKEY_COL_PRODUCTKEY, productkey);
        values.put(GAME_COL_PLATFORM, platform);
        values.put(GAME_COL_AGE,  age);
        values.put(GAME_COL_DEVELOPER, developer);
        values.put(GAME_COL_PUBLISHER, publisher);
        long result = db.insert(GAME_TABLE_NAME, null, values);
        return (result == -1) ? false : true;
    }

    public int updateList(String newListName, String oldListName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values1 = new ContentValues();
        values1.put(LIST_COL_NAME, newListName);
        ContentValues values2 = new ContentValues();
        values2.put(FOREIGNKEY_COL_LISTNAME, newListName);
        int a = db.update(LIST_TABLE_NAME, values1, LIST_COL_NAME + "=?", new String[] {oldListName});
        int b = db.update(LISTHASPRODUCT_TABLE_NAME, values2, FOREIGNKEY_COL_LISTNAME + "=?", new String[] {oldListName});
        return a + b;
    }

    public int deleteList(String listName) {
        SQLiteDatabase db = this.getWritableDatabase();
        int a = db.delete(LISTHASPRODUCT_TABLE_NAME, FOREIGNKEY_COL_LISTNAME + "=?", new String[] {listName});
        int b = db.delete(LIST_TABLE_NAME, LIST_COL_NAME + "=?", new String[] {listName});
        return a + b;
    }

    public int deleteProductFromList(int productkey, String listname) {
        SQLiteDatabase db = this.getWritableDatabase();
        if(listname == null) {
            return db.delete(LISTHASPRODUCT_TABLE_NAME, FOREIGNKEY_COL_PRODUCTKEY + "=?", new String[] {String.valueOf(productkey)});
        } else {
            return db.delete(LISTHASPRODUCT_TABLE_NAME, FOREIGNKEY_COL_PRODUCTKEY + "=? AND " + FOREIGNKEY_COL_LISTNAME + "=?", new String[] {String.valueOf(productkey), listname});
        }
    }

    public int deleteProduct(int productkey, String table, boolean isChild) {
        SQLiteDatabase db = this.getWritableDatabase();
        if(isChild) {
            return db.delete(table, FOREIGNKEY_COL_PRODUCTKEY + "=?", new String[] {String.valueOf(productkey)});
        } else {
            return db.delete(table, PRODUCT_COL_KEY + "=?", new String[] {String.valueOf(productkey)});
        }

    }

    public int updateProduct(int productkey, String title, int price, String release, String genre, String comment, String imgUrl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRODUCT_COL_TITLE, title);
        values.put(PRODUCT_COL_PRICE, price);
        values.put(PRODUCT_COL_RELEASE, release);
        values.put(PRODUCT_COL_GENRE, genre);
        values.put(PRODUCT_COL_COMMENT, comment);
        values.put(PRODUCT_COL_IMGURL, imgUrl);
        return db.update(PRODUCT_TABLE_NAME, values, PRODUCT_COL_KEY + "=?", new String[] {String.valueOf(productkey)});
    }

    public int updateBook(int productkey, String author, int pages, String type, String publisher, String isbn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BOOK_COL_AUTHOR, author);
        values.put(BOOK_COL_PAGES, pages);
        values.put(BOOK_COL_TYPE, type);
        values.put(BOOK_COL_PUBLISHER, publisher);
        values.put(BOOK_COL_ISBN, isbn);
        return db.update(BOOK_TABLE_NAME, values, FOREIGNKEY_COL_PRODUCTKEY + "=?", new String[] {String.valueOf(productkey)});
    }

    public int updateMovie(int productkey, int length, int age, String company, int rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MOVIE_COL_LENGTH, length);
        values.put(MOVIE_COL_AGE, age);
        values.put(MOVIE_COL_COMPANY, company);
        values.put(MOVIE_COL_RATING, rating);
        return db.update(MOVIE_TABLE_NAME, values, FOREIGNKEY_COL_PRODUCTKEY + "=?", new String[] {String.valueOf(productkey)});
    }

    public int updateSong(int productkey, int length, String label, String artist) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SONG_COL_LENGTH, length);
        values.put(SONG_COL_LABEL, label);
        values.put(SONG_COL_ARTIST, artist);
        return db.update(SONG_TABLE_NAME, values, FOREIGNKEY_COL_PRODUCTKEY + "=?", new String[] {String.valueOf(productkey)});
    }

    public int updateGame(int productkey, String platform, int age , String developer, String publisher) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GAME_COL_PLATFORM, platform);
        values.put(GAME_COL_AGE,  age);
        values.put(GAME_COL_DEVELOPER, developer);
        values.put(GAME_COL_PUBLISHER, publisher);
        return db.update(GAME_TABLE_NAME, values, FOREIGNKEY_COL_PRODUCTKEY + "=?", new String[] {String.valueOf(productkey)});
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

    public boolean duplicateProductInList(int productkey, String listname) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT * FROM " + LISTHASPRODUCT_TABLE_NAME + " WHERE " +
                FOREIGNKEY_COL_PRODUCTKEY + "=? AND " +
                FOREIGNKEY_COL_LISTNAME + "=?",
                new String[] {String.valueOf(productkey), listname}
        );
        if(c.getCount() <= 0) {
            return false;
        } else {
            c.close();
            return true;
        }
    }

    public int duplicateProduct(String title, int price, String release, String genre, String comment, String imgUrl) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT * FROM " + PRODUCT_TABLE_NAME + " WHERE " +
                PRODUCT_COL_TITLE + "=? AND " +
                PRODUCT_COL_PRICE + "=? AND " +
                PRODUCT_COL_RELEASE + "=? AND " +
                PRODUCT_COL_GENRE + "=? AND " +
                PRODUCT_COL_COMMENT + "=? AND " +
                PRODUCT_COL_IMGURL + "=?",
                new String[] {title, String.valueOf(price), release, genre, comment, imgUrl}
        );
        if(c.getCount() <= 0) {
            c.close();
            return -1;
        } else {
            c.moveToFirst();
            int productkey = c.getInt(c.getColumnIndex(PRODUCT_COL_KEY));
            c.close();
            return productkey;

        }
    }


    public Cursor getData(String query, String[] selectionArgs) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(query, selectionArgs);
    }
}