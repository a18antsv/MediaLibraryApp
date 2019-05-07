package com.github.a18antsv.medialibraryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static com.github.a18antsv.medialibraryapp.DataContract.Entry.*;

public class ProductDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_content);

        Intent intent = getIntent();
        String mediaType = intent.getStringExtra("MEDIATYPE");
        int productkey = intent.getIntExtra(PRODUCT_COL_KEY, -1);
        String title = intent.getStringExtra(PRODUCT_COL_TITLE);
        int price = intent.getIntExtra(PRODUCT_COL_PRICE, -1);
        String release = intent.getStringExtra(PRODUCT_COL_RELEASE);
        String genre = intent.getStringExtra(PRODUCT_COL_GENRE);
        String comment = intent.getStringExtra(PRODUCT_COL_COMMENT);

        setTitle(mediaType.substring(0,1).toUpperCase() + mediaType.substring(1) + " details");

        switch (mediaType) {
            case BOOK_TABLE_NAME:
                int bookPages = intent.getIntExtra(BOOK_COL_PAGES, -1);
                String bookType = intent.getStringExtra(BOOK_COL_TYPE);
                String bookPublisher = intent.getStringExtra(BOOK_COL_PUBLISHER);
                String bookIsbn = intent.getStringExtra(BOOK_COL_ISBN);
                break;
            case MOVIE_TABLE_NAME:
                int movieLength = intent.getIntExtra(MOVIE_COL_LENGTH, -1);
                int movieAge = intent.getIntExtra(MOVIE_COL_AGE, -1);
                String movieCompany = intent.getStringExtra(MOVIE_COL_COMPANY);
                int movieRating = intent.getIntExtra(MOVIE_COL_RATING, -1);
                break;
            case SONG_TABLE_NAME:
                int songLength = intent.getIntExtra(SONG_COL_LENGTH, -1);
                String songLabel = intent.getStringExtra(SONG_COL_LABEL);
                String songArtist = intent.getStringExtra(SONG_COL_ARTIST);
                break;
            case GAME_TABLE_NAME:
                String gamePlatform = intent.getStringExtra(GAME_COL_PLATFORM);
                int gameAge = intent.getIntExtra(GAME_COL_AGE, -1);
                String gameDeveloper = intent.getStringExtra(GAME_COL_DEVELOPER);
                String gamePublisher = intent.getStringExtra(GAME_COL_PUBLISHER);
                break;
        }
    }
}
