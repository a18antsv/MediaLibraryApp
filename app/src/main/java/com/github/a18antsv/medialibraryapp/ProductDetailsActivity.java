package com.github.a18antsv.medialibraryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.github.a18antsv.medialibraryapp.DataContract.Entry.*;

public class ProductDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        ViewStub stub = (ViewStub) findViewById(R.id.media_type_extras);

        Intent intent = getIntent();
        String mediaType = intent.getStringExtra("MEDIATYPE");
        int productkey = intent.getIntExtra(PRODUCT_COL_KEY, -1);
        String title = intent.getStringExtra(PRODUCT_COL_TITLE);
        int price = intent.getIntExtra(PRODUCT_COL_PRICE, -1);
        String release = intent.getStringExtra(PRODUCT_COL_RELEASE);
        String genre = intent.getStringExtra(PRODUCT_COL_GENRE);
        String comment = intent.getStringExtra(PRODUCT_COL_COMMENT);

        setTitle(mediaType.substring(0,1).toUpperCase() + mediaType.substring(1) + " details");

        ImageView imageViewProduct = (ImageView) findViewById(R.id.imageView_product);
        TextView textViewTitle = (TextView) findViewById(R.id.textView_title);
        TextView textViewPrice = (TextView) findViewById(R.id.textView_price);
        TextView textViewRelease = (TextView) findViewById(R.id.textView_release);
        TextView textViewGenre = (TextView) findViewById(R.id.textView_genre);
        TextView textViewComment = (TextView) findViewById(R.id.textView_comment);
        Button buttonEdit = (Button) findViewById(R.id.button_edit);
        Button buttonDelete = (Button) findViewById(R.id.button_delete);

        //Add url as property to the product object and into the database and put a variable for it here - Test image for now
        new DownloadImage(imageViewProduct).execute("https://thumbs.dreamstime.com/z/tv-test-image-card-rainbow-multi-color-bars-geometric-signals-retro-hardware-s-minimal-pop-art-print-suitable-89603635.jpg");

        textViewTitle.setText(title);
        textViewPrice.setText("Price: $"+Integer.toString(price));
        textViewRelease.setText("Release date: "+release);
        textViewGenre.setText("Genre: "+genre);
        textViewComment.setText("Comment: "+comment);

        switch (mediaType) {
            case BOOK_TABLE_NAME:
                stub.setLayoutResource(R.layout.extras_book);
                stub.inflate();

                int bookPages = intent.getIntExtra(BOOK_COL_PAGES, -1);
                String bookType = intent.getStringExtra(BOOK_COL_TYPE);
                String bookPublisher = intent.getStringExtra(BOOK_COL_PUBLISHER);
                String bookIsbn = intent.getStringExtra(BOOK_COL_ISBN);

                TextView textViewBookPages = (TextView) findViewById(R.id.textView_bookPages);
                TextView textViewBookType = (TextView) findViewById(R.id.textView_bookType);
                TextView textViewBookPublisher = (TextView) findViewById(R.id.textView_bookPublisher);
                TextView textViewBookIsbn = (TextView) findViewById(R.id.textView_bookIsbn);

                textViewBookPages.setText("Pages: "+Integer.toString(bookPages));
                textViewBookType.setText("Type: "+bookType);
                textViewBookPublisher.setText("Publisher: "+bookPublisher);
                textViewBookIsbn.setText("ISBN: "+bookIsbn);
                break;
            case MOVIE_TABLE_NAME:
                stub.setLayoutResource(R.layout.extras_movie);
                stub.inflate();

                int movieLength = intent.getIntExtra(MOVIE_COL_LENGTH, -1);
                int movieAge = intent.getIntExtra(MOVIE_COL_AGE, -1);
                String movieCompany = intent.getStringExtra(MOVIE_COL_COMPANY);
                int movieRating = intent.getIntExtra(MOVIE_COL_RATING, -1);

                TextView textViewMovieLength = (TextView) findViewById(R.id.textView_movieLength);
                TextView textViewMovieAge = (TextView) findViewById(R.id.textView_movieAge);
                TextView textViewMovieCompany = (TextView) findViewById(R.id.textView_movieCompany);
                TextView textViewMovieRating = (TextView) findViewById(R.id.textView_movieRating);

                textViewMovieLength.setText("Length: "+Integer.toString(movieLength)+"min");
                textViewMovieAge.setText("Min. age: "+Integer.toString(movieAge)+" years");
                textViewMovieCompany.setText("Company: "+movieCompany);
                textViewMovieRating.setText("Rating: "+Integer.toString(movieRating)+"/10");
                break;
            case SONG_TABLE_NAME:
                stub.setLayoutResource(R.layout.extras_song);
                stub.inflate();

                int songLength = intent.getIntExtra(SONG_COL_LENGTH, -1);
                String songLabel = intent.getStringExtra(SONG_COL_LABEL);
                String songArtist = intent.getStringExtra(SONG_COL_ARTIST);

                TextView textViewSongLength = (TextView) findViewById(R.id.textView_songLength);
                TextView textViewSongLabel = (TextView) findViewById(R.id.textView_songLabel);
                TextView textViewSongArtist = (TextView) findViewById(R.id.textView_songArtist);

                textViewSongLength.setText("Length: "+Integer.toString(songLength)+"min");
                textViewSongLabel.setText("Label: "+songLabel);
                textViewSongArtist.setText("Artist: "+songArtist);
                break;
            case GAME_TABLE_NAME:
                stub.setLayoutResource(R.layout.extras_game);
                stub.inflate();

                String gamePlatform = intent.getStringExtra(GAME_COL_PLATFORM);
                int gameAge = intent.getIntExtra(GAME_COL_AGE, -1);
                String gameDeveloper = intent.getStringExtra(GAME_COL_DEVELOPER);
                String gamePublisher = intent.getStringExtra(GAME_COL_PUBLISHER);

                TextView textViewGamePlatform = (TextView) findViewById(R.id.textView_gamePlatform);
                TextView textViewGameAge = (TextView) findViewById(R.id.textView_gameAge);
                TextView textViewGameDeveloper = (TextView) findViewById(R.id.textView_gameDeveloper);
                TextView textViewGamePublisher = (TextView) findViewById(R.id.textView_gamePublisher);

                textViewGamePlatform.setText("Platform: "+gamePlatform);
                textViewGameAge.setText("Min. age: "+Integer.toString(gameAge)+" years");
                textViewGameDeveloper.setText("Developer: "+gameDeveloper);
                textViewGamePublisher.setText("Publisher: "+gamePublisher);
                break;
        }

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
