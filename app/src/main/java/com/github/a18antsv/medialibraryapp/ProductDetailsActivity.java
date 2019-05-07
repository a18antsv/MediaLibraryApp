package com.github.a18antsv.medialibraryapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.id;
import static android.R.attr.min;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.github.a18antsv.medialibraryapp.DataContract.Entry.*;

public class ProductDetailsActivity extends AppCompatActivity {
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        ViewStub stub = (ViewStub) findViewById(R.id.media_type_extras);

        dbHelper = new DbHelper(this);
        dbHelper.getWritableDatabase();

        Intent intent = getIntent();
        final String mediaType = intent.getStringExtra("MEDIATYPE");
        final String fromList = intent.getStringExtra("LISTNAME");
        final int clickedItemPos = intent.getIntExtra("ITEMPOS", -1);
        final int productkey = intent.getIntExtra(PRODUCT_COL_KEY, -1);
        final String title = intent.getStringExtra(PRODUCT_COL_TITLE);
        int price = intent.getIntExtra(PRODUCT_COL_PRICE, -1);
        String release = intent.getStringExtra(PRODUCT_COL_RELEASE);
        String genre = intent.getStringExtra(PRODUCT_COL_GENRE);
        String comment = intent.getStringExtra(PRODUCT_COL_COMMENT);

        setTitle(mediaType.substring(0,1).toUpperCase() + mediaType.substring(1) + " details");

        ImageView imageViewProduct = (ImageView) findViewById(R.id.imageView_product);
        EditText editTextTitle = (EditText) findViewById(R.id.editText_title);
        EditText editTextPrice = (EditText) findViewById(R.id.editText_price);
        EditText editTextRelease = (EditText) findViewById(R.id.editText_release);
        EditText editTextGenre = (EditText) findViewById(R.id.editText_genre);
        EditText editTextComment = (EditText) findViewById(R.id.editText_comment);
        Button buttonEdit = (Button) findViewById(R.id.button_edit);
        Button buttonDelete = (Button) findViewById(R.id.button_delete);

        //Add url as property to the product object and into the database and put a variable for it here - Test image for now
        new DownloadImage(imageViewProduct).execute("https://thumbs.dreamstime.com/z/tv-test-image-card-rainbow-multi-color-bars-geometric-signals-retro-hardware-s-minimal-pop-art-print-suitable-89603635.jpg");

        editTextTitle.setText(title);
        editTextPrice.setText(Integer.toString(price));
        editTextRelease.setText(release);
        editTextGenre.setText(genre);
        editTextComment.setText(comment);

        switch (mediaType) {
            case BOOK_TABLE_NAME:
                stub.setLayoutResource(R.layout.extras_book);
                stub.inflate();

                int bookPages = intent.getIntExtra(BOOK_COL_PAGES, -1);
                String bookType = intent.getStringExtra(BOOK_COL_TYPE);
                String bookPublisher = intent.getStringExtra(BOOK_COL_PUBLISHER);
                String bookIsbn = intent.getStringExtra(BOOK_COL_ISBN);

                EditText editTextBookPages = (EditText) findViewById(R.id.editText_bookPages);
                EditText editTextBookType = (EditText) findViewById(R.id.editText_bookType);
                EditText editTextBookPublisher = (EditText) findViewById(R.id.editText_bookPublisher);
                EditText editTextBookIsbn = (EditText) findViewById(R.id.editText_bookIsbn);

                editTextBookPages.setText(Integer.toString(bookPages));
                editTextBookType.setText(bookType);
                editTextBookPublisher.setText(bookPublisher);
                editTextBookIsbn.setText(bookIsbn);
                break;
            case MOVIE_TABLE_NAME:
                stub.setLayoutResource(R.layout.extras_movie);
                stub.inflate();

                int movieLength = intent.getIntExtra(MOVIE_COL_LENGTH, -1);
                int movieAge = intent.getIntExtra(MOVIE_COL_AGE, -1);
                String movieCompany = intent.getStringExtra(MOVIE_COL_COMPANY);
                int movieRating = intent.getIntExtra(MOVIE_COL_RATING, -1);

                EditText editTextMovieLength = (EditText) findViewById(R.id.editText_movieLength);
                EditText editTextMovieAge = (EditText) findViewById(R.id.editText_movieAge);
                EditText editTextMovieCompany = (EditText) findViewById(R.id.editText_movieCompany);
                EditText editTextMovieRating = (EditText) findViewById(R.id.editText_movieRating);

                editTextMovieLength.setText(Integer.toString(movieLength));
                editTextMovieAge.setText(Integer.toString(movieAge));
                editTextMovieCompany.setText(movieCompany);
                editTextMovieRating.setText(Integer.toString(movieRating));
                break;
            case SONG_TABLE_NAME:
                stub.setLayoutResource(R.layout.extras_song);
                stub.inflate();

                int songLength = intent.getIntExtra(SONG_COL_LENGTH, -1);
                String songLabel = intent.getStringExtra(SONG_COL_LABEL);
                String songArtist = intent.getStringExtra(SONG_COL_ARTIST);

                EditText editTextSongLength = (EditText) findViewById(R.id.editText_songLength);
                EditText editTextSongLabel = (EditText) findViewById(R.id.editText_songLabel);
                EditText editTextSongArtist = (EditText) findViewById(R.id.editText_songArtist);

                editTextSongLength.setText(Integer.toString(songLength));
                editTextSongLabel.setText(songLabel);
                editTextSongArtist.setText(songArtist);
                break;
            case GAME_TABLE_NAME:
                stub.setLayoutResource(R.layout.extras_game);
                stub.inflate();

                String gamePlatform = intent.getStringExtra(GAME_COL_PLATFORM);
                int gameAge = intent.getIntExtra(GAME_COL_AGE, -1);
                String gameDeveloper = intent.getStringExtra(GAME_COL_DEVELOPER);
                String gamePublisher = intent.getStringExtra(GAME_COL_PUBLISHER);

                EditText editTextGamePlatform = (EditText) findViewById(R.id.editText_gamePlatform);
                EditText editTextGameAge = (EditText) findViewById(R.id.editText_gameAge);
                EditText editTextGameDeveloper = (EditText) findViewById(R.id.editText_gameDeveloper);
                EditText editTextGamePublisher = (EditText) findViewById(R.id.editText_gamePublisher);

                editTextGamePlatform.setText(gamePlatform);
                editTextGameAge.setText(Integer.toString(gameAge));
                editTextGameDeveloper.setText(gameDeveloper);
                editTextGamePublisher.setText(gamePublisher);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(ProductDetailsActivity.this);
                builder.setTitle("Are you sure?");
                builder.setMessage("This " + mediaType + " will be deleted from the list named " + fromList + "!");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int deletedRows = dbHelper.deleteProductFromList(productkey, fromList);
                        if(deletedRows == 1) {
                            Toast.makeText(getApplicationContext(), "Successfully deleted " + title + " from the list named " + fromList, Toast.LENGTH_SHORT).show();
                        }
                        Intent intent = new Intent();
                        intent.putExtra("ITEMPOS", clickedItemPos);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.create().show();
            }
        });
    }
}
