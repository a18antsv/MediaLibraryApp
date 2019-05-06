package com.github.a18antsv.medialibraryapp;

import android.provider.BaseColumns;
import static com.github.a18antsv.medialibraryapp.DataContract.Entry.*;


public class DataContract {

    public static final String CREATE_LIST_TABLE = "CREATE TABLE IF NOT EXISTS " +
        LIST_TABLE_NAME + " (" +
        LIST_COL_NAME + " TEXT PRIMARY KEY)";

    public static final String CREATE_PRODUCT_TABLE = "CREATE TABLE IF NOT EXISTS " +
            PRODUCT_TABLE_NAME + " (" +
            PRODUCT_COL_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            PRODUCT_COL_TITLE + " TEXT NOT NULL," +
            PRODUCT_COL_PRICE + " INTEGER," +
            PRODUCT_COL_RELEASE + " TEXT," +
            PRODUCT_COL_GENRE + " TEXT," +
            PRODUCT_COL_COMMENT + " TEXT)";

    public static final String CREATE_LISTHASPRODUCT_TABLE = "CREATE TABLE IF NOT EXISTS " +
            LISTHASPRODUCT_TABLE_NAME + " (" +
            FOREIGNKEY_COL_PRODUCTKEY + " INTEGER NOT NULL," +
            FOREIGNKEY_COL_LISTNAME + " TEXT NOT NULL," +
            " FOREIGN KEY ("+FOREIGNKEY_COL_PRODUCTKEY+") REFERENCES " + PRODUCT_TABLE_NAME + "("+PRODUCT_COL_KEY+")," +
            " FOREIGN KEY ("+FOREIGNKEY_COL_LISTNAME+") REFERENCES " + LIST_TABLE_NAME + "("+LIST_COL_NAME+")," +
            " PRIMARY KEY ("+FOREIGNKEY_COL_PRODUCTKEY+", "+FOREIGNKEY_COL_LISTNAME+"))";

    public static final String CREATE_PERSON_TABLE = "CREATE TABLE IF NOT EXISTS " +
            PERSON_TABLE_NAME + " (" +
            PERSON_COL_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            PERSON_COL_NAME + " TEXT NOT NULL," +
            PERSON_COL_BIRTHDAY + " TEXT," +
            PERSON_COL_COMMENT + " TEXT)";

    public static final String CREATE_ROLE_TABLE = "CREATE TABLE IF NOT EXISTS " +
            ROLE_TABLE_NAME + " (" +
            ROLE_COL_ROLE + " TEXT PRIMARY KEY)";

    public static final String CREATE_PERSONROLE_TABLE = "CREATE TABLE IF NOT EXISTS " +
            PERSONROLE_TABLE_NAME + " (" +
            FOREIGNKEY_COL_PERSONKEY + " INTEGER NOT NULL," +
            FOREIGNKEY_COL_ROLENAME + " TEXT NOT NULL," +
            " FOREIGN KEY ("+FOREIGNKEY_COL_PERSONKEY+") REFERENCES " + PERSON_TABLE_NAME + "("+PERSON_COL_KEY+")," +
            " FOREIGN KEY ("+FOREIGNKEY_COL_ROLENAME+") REFERENCES " + ROLE_TABLE_NAME + "("+ROLE_COL_ROLE+")," +
            " PRIMARY KEY ("+FOREIGNKEY_COL_PERSONKEY+", "+FOREIGNKEY_COL_ROLENAME+"))";

    public static final String CREATE_MOVIE_TABLE = "CREATE TABLE IF NOT EXISTS " +
            MOVIE_TABLE_NAME + " (" +
            FOREIGNKEY_COL_PRODUCTKEY + " INTEGER PRIMARY KEY," +
            MOVIE_COL_LENGTH + " INTEGER," +
            MOVIE_COL_AGE + " INTEGER," +
            MOVIE_COL_COMPANY + " TEXT," +
            MOVIE_COL_RATING + " INTEGER," +
            " FOREIGN KEY ("+FOREIGNKEY_COL_PRODUCTKEY+") REFERENCES " + PRODUCT_TABLE_NAME + "("+PRODUCT_COL_KEY+"))";

    public static final String CREATE_BOOK_TABLE = "CREATE TABLE IF NOT EXISTS " +
            BOOK_TABLE_NAME + " (" +
            FOREIGNKEY_COL_PRODUCTKEY + " INTEGER PRIMARY KEY," +
            BOOK_COL_PAGES + " INTEGER," +
            BOOK_COL_TYPE + " TEXT," +
            BOOK_COL_PUBLISHER + " TEXT," +
            BOOK_COL_ISBN + " TEXT," +
            " FOREIGN KEY ("+FOREIGNKEY_COL_PRODUCTKEY+") REFERENCES " + PRODUCT_TABLE_NAME + "("+PRODUCT_COL_KEY+"))";

    public static final String CREATE_GAME_TABLE = "CREATE TABLE IF NOT EXISTS " +
            GAME_TABLE_NAME + " (" +
            FOREIGNKEY_COL_PRODUCTKEY + " INTEGER PRIMARY KEY," +
            GAME_COL_PLATFORM + " TEXT," +
            GAME_COL_AGE + " INTEGER," +
            GAME_COL_DEVELOPER + " TEXT," +
            GAME_COL_PUBLISHER + " TEXT," +
            " FOREIGN KEY ("+FOREIGNKEY_COL_PRODUCTKEY+") REFERENCES " + PRODUCT_TABLE_NAME + "("+PRODUCT_COL_KEY+"))";

    public static final String CREATE_SONG_TABLE = "CREATE TABLE IF NOT EXISTS " +
            SONG_TABLE_NAME + " (" +
            FOREIGNKEY_COL_PRODUCTKEY + " INTEGER PRIMARY KEY," +
            SONG_COL_LENGTH + " INTEGER," +
            SONG_COL_LABEL + " TEXT," +
            SONG_COL_ARTIST + " TEXT," +
            " FOREIGN KEY ("+FOREIGNKEY_COL_PRODUCTKEY+") REFERENCES " + PRODUCT_TABLE_NAME + "("+PRODUCT_COL_KEY+"))";



    public static final String
            DROP_LIST_TABLE = "DROP TABLE IF EXISTS " + LIST_TABLE_NAME,
            DROP_PRODUCT_TABLE = "DROP TABLE IF EXISTS " + PRODUCT_TABLE_NAME,
            DROP_LISTHASPRODUCT_TABLE = "DROP TABLE IF EXISTS " + LISTHASPRODUCT_TABLE_NAME,
            DROP_PERSON_TABLE = "DROP TABLE IF EXISTS " + PERSON_TABLE_NAME,
            DROP_ROLE_TABLE = "DROP TABLE IF EXISTS " + ROLE_TABLE_NAME,
            DROP_PERSONROLE_TABLE = "DROP TABLE IF EXISTS " + PERSONROLE_TABLE_NAME,
            DROP_MOVIE_TABLE = "DROP TABLE IF EXISTS " + MOVIE_TABLE_NAME,
            DROP_BOOK_TABLE = "DROP TABLE IF EXISTS " + BOOK_TABLE_NAME,
            DROP_GAME_TABLE = "DROP TABLE IF EXISTS " + GAME_TABLE_NAME,
            DROP_SONG_TABLE = "DROP TABLE IF EXISTS " + SONG_TABLE_NAME;

    private DataContract() {}

    public static class Entry implements BaseColumns {
        public static final String
                LIST_TABLE_NAME = "list",
                LIST_COL_NAME = "listname";

        public static final String
                PRODUCT_TABLE_NAME = "product",
                PRODUCT_COL_KEY = "productkey",
                PRODUCT_COL_TITLE = "title",
                PRODUCT_COL_PRICE = "price",
                PRODUCT_COL_RELEASE = "release",
                PRODUCT_COL_GENRE = "genre",
                PRODUCT_COL_COMMENT = "comment";

        public static final String
                LISTHASPRODUCT_TABLE_NAME = "listhasproduct",
                FOREIGNKEY_COL_PRODUCTKEY = "forproductkey",
                FOREIGNKEY_COL_LISTNAME = "forlistname";


        public static final String
                PERSON_TABLE_NAME = "person",
                PERSON_COL_KEY = "personkey",
                PERSON_COL_NAME = "name",
                PERSON_COL_BIRTHDAY = "birthday",
                PERSON_COL_COMMENT = "comment";

        public static final String
                ROLE_TABLE_NAME = "role",
                ROLE_COL_ROLE = "role";

        public static final String
                PERSONROLE_TABLE_NAME = "personhasrole",
                FOREIGNKEY_COL_PERSONKEY = "forpersonkey",
                FOREIGNKEY_COL_ROLENAME = "rolename";

        public static final String
                MOVIE_TABLE_NAME = "movie",
                MOVIE_COL_LENGTH = "length",
                MOVIE_COL_AGE = "age",
                MOVIE_COL_RATING = "rating",
                MOVIE_COL_COMPANY = "company";

        public static final String
                BOOK_TABLE_NAME = "book",
                BOOK_COL_PUBLISHER = "publisher",
                BOOK_COL_PAGES = "pages",
                BOOK_COL_TYPE = "type",
                BOOK_COL_ISBN = "isbn";

        public static final String
                GAME_TABLE_NAME = "game",
                GAME_COL_PLATFORM = "platform",
                GAME_COL_AGE = "age",
                GAME_COL_PUBLISHER = "publisher",
                GAME_COL_DEVELOPER = "developer";

        public static final String
                SONG_TABLE_NAME = "song",
                SONG_COL_LABEL = "label",
                SONG_COL_LENGTH = "length",
                SONG_COL_ARTIST = "artist";
    }
}