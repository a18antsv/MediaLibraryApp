package com.github.a18antsv.medialibraryapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.a18antsv.medialibraryapp.DownloadImage;
import com.github.a18antsv.medialibraryapp.R;
import com.github.a18antsv.medialibraryapp.database.DbHelper;

import java.net.URL;
import java.net.URLConnection;

import static com.github.a18antsv.medialibraryapp.database.DataContract.Entry.*;

public class ProductDetailsActivity extends AppCompatActivity {
    private DbHelper dbHelper;
    private boolean hasUpdated;
    private String mediaType;
    private int clickedItemPos;
    private EditText editTextTitle, editTextPrice, editTextRelease, editTextGenre, editTextComment, editTextImgUrl;
    private EditText editTextBookAuthor, editTextBookPages, editTextBookType, editTextBookPublisher, editTextBookIsbn;
    private EditText editTextMovieLength, editTextMovieAge, editTextMovieCompany, editTextMovieRating;
    private EditText editTextSongLength, editTextSongLabel, editTextSongArtist;
    private EditText editTextGamePlatform, editTextGameAge, editTextGameDeveloper, editTextGamePublisher;
    private ImageView imageViewProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        dbHelper = new DbHelper(this);
        dbHelper.getWritableDatabase();
        hasUpdated = false;

        ViewStub stub = findViewById(R.id.media_type_extras);

        Intent intent = getIntent();
        mediaType = intent.getStringExtra("MEDIATYPE");
        final String fromList = intent.getStringExtra("LISTNAME");
        final int productkey = intent.getIntExtra(PRODUCT_COL_KEY, -1);
        clickedItemPos = intent.getIntExtra("ITEMPOS", -1);
        final String title = intent.getStringExtra(PRODUCT_COL_TITLE);
        int price = intent.getIntExtra(PRODUCT_COL_PRICE, -1);
        String genre = intent.getStringExtra(PRODUCT_COL_GENRE);
        String release = intent.getStringExtra(PRODUCT_COL_RELEASE);
        String comment = intent.getStringExtra(PRODUCT_COL_COMMENT);
        final String imgUrl = intent.getStringExtra(PRODUCT_COL_IMGURL);

        setTitle(mediaType.substring(0,1).toUpperCase() + mediaType.substring(1) + " details");

        imageViewProduct = findViewById(R.id.imageView_product);
        editTextTitle = findViewById(R.id.editText_title);
        editTextPrice = findViewById(R.id.editText_price);
        editTextRelease = findViewById(R.id.editText_release);
        editTextGenre = findViewById(R.id.editText_genre);
        editTextComment = findViewById(R.id.editText_comment);
        editTextImgUrl = findViewById(R.id.editText_imgurl);
        Button buttonEdit = findViewById(R.id.button_update);
        Button buttonDelete = findViewById(R.id.button_delete);


        String extension = imgUrl.substring(imgUrl.lastIndexOf(".") + 1);
        if(extension.equals("jpg") || extension.equals("png") || extension.equals("jpeg") || extension.equals("gif")) {
            new DownloadImage(imageViewProduct).execute(imgUrl);
        }

        editTextTitle.setText(title);
        editTextPrice.setText(Integer.toString(price));
        editTextRelease.setText(release);
        editTextGenre.setText(genre);
        editTextComment.setText(comment);
        editTextImgUrl.setText(imgUrl);

        switch(mediaType) {
            case BOOK_TABLE_NAME:
                stub.setLayoutResource(R.layout.extras_book);
                stub.inflate();

                String bookAuthor = intent.getStringExtra(BOOK_COL_AUTHOR);
                int bookPages = intent.getIntExtra(BOOK_COL_PAGES, -1);
                String bookType = intent.getStringExtra(BOOK_COL_TYPE);
                String bookPublisher = intent.getStringExtra(BOOK_COL_PUBLISHER);
                String bookIsbn = intent.getStringExtra(BOOK_COL_ISBN);

                editTextBookAuthor = findViewById(R.id.editText_bookAuthor);
                editTextBookPages = findViewById(R.id.editText_bookPages);
                editTextBookType = findViewById(R.id.editText_bookType);
                editTextBookPublisher = findViewById(R.id.editText_bookPublisher);
                editTextBookIsbn = findViewById(R.id.editText_bookIsbn);

                editTextBookAuthor.setText(bookAuthor);
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

                editTextMovieLength = findViewById(R.id.editText_movieLength);
                editTextMovieAge = findViewById(R.id.editText_movieAge);
                editTextMovieCompany = findViewById(R.id.editText_movieCompany);
                editTextMovieRating = findViewById(R.id.editText_movieRating);

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

                editTextSongLength = findViewById(R.id.editText_songLength);
                editTextSongLabel = findViewById(R.id.editText_songLabel);
                editTextSongArtist = findViewById(R.id.editText_songArtist);

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

                editTextGamePlatform = findViewById(R.id.editText_gamePlatform);
                editTextGameAge = findViewById(R.id.editText_gameAge);
                editTextGameDeveloper = findViewById(R.id.editText_gameDeveloper);
                editTextGamePublisher = findViewById(R.id.editText_gamePublisher);

                editTextGamePlatform.setText(gamePlatform);
                editTextGameAge.setText(Integer.toString(gameAge));
                editTextGameDeveloper.setText(gameDeveloper);
                editTextGamePublisher.setText(gamePublisher);
                break;
        }

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int updatedParentRows = 0;
                int updatedChildRows = 0;
                if(editTextTitle.getText().toString().trim().length() > 0 && editTextPrice.getText().toString().length() > 0) {
                    updatedParentRows = dbHelper.updateProduct(
                            productkey,
                            editTextTitle.getText().toString(),
                            Integer.parseInt(editTextPrice.getText().toString()),
                            editTextRelease.getText().toString(),
                            editTextGenre.getText().toString(),
                            editTextComment.getText().toString(),
                            editTextImgUrl.getText().toString()
                    );
                    switch (mediaType) {
                        case BOOK_TABLE_NAME:
                            if(editTextBookPages.getText().toString().length() > 0) {
                                updatedChildRows = dbHelper.updateBook(
                                        productkey,
                                        editTextBookAuthor.getText().toString(),
                                        Integer.parseInt(editTextBookPages.getText().toString()),
                                        editTextBookType.getText().toString(),
                                        editTextBookPublisher.getText().toString(),
                                        editTextBookIsbn.getText().toString()
                                );
                            } else {
                                Toast.makeText(getApplicationContext(), "A book needs at least a number of pages...", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case MOVIE_TABLE_NAME:
                            if(editTextMovieLength.getText().toString().length() > 0 && editTextMovieAge.getText().toString().length() > 0 && editTextMovieRating.getText().toString().length() > 0) {
                                updatedChildRows = dbHelper.updateMovie(
                                        productkey,
                                        Integer.parseInt(editTextMovieLength.getText().toString()),
                                        Integer.parseInt(editTextMovieAge.getText().toString()),
                                        editTextMovieCompany.getText().toString(),
                                        Integer.parseInt(editTextMovieRating.getText().toString())
                                );
                            } else {
                                Toast.makeText(getApplicationContext(), "A movie needs at least a length, min age and rating...", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case SONG_TABLE_NAME:
                            if(editTextSongLength.getText().toString().length() > 0) {
                                updatedChildRows = dbHelper.updateSong(
                                        productkey,
                                        Integer.parseInt(editTextSongLength.getText().toString()),
                                        editTextSongLabel.getText().toString(),
                                        editTextSongArtist.getText().toString()
                                );
                            } else {
                                Toast.makeText(getApplicationContext(), "A song needs at least a length...", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case GAME_TABLE_NAME:
                            if(editTextGameAge.getText().toString().length() > 0) {
                                updatedChildRows = dbHelper.updateGame(
                                        productkey,
                                        editTextGamePlatform.getText().toString(),
                                        Integer.parseInt(editTextGameAge.getText().toString()),
                                        editTextGameDeveloper.getText().toString(),
                                        editTextGamePublisher.getText().toString()
                                );
                            } else {
                                Toast.makeText(getApplicationContext(), "A game needs at least a min age...", Toast.LENGTH_SHORT).show();
                            }
                            break;
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "A product needs at least a title and a price...", Toast.LENGTH_SHORT).show();
                }

                if(updatedParentRows == 1 || updatedChildRows == 1) {
                    hasUpdated = true;
                    String extension = editTextImgUrl.getText().toString().substring(editTextImgUrl.getText().toString().lastIndexOf(".") + 1);
                    if (extension.equals("jpg") || extension.equals("png") || extension.equals("jpeg") || extension.equals("gif")) {
                        new DownloadImage(imageViewProduct).execute(editTextImgUrl.getText().toString());
                    } else {
                        imageViewProduct.setImageResource(R.drawable.baseline_add_photo_alternate_black_48dp);
                    }
                    Toast.makeText(getApplicationContext(), "This product has now been updated!", Toast.LENGTH_SHORT).show();
                }
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
                        intent.putExtra("DELETED", "DELETED");
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

    @Override
    public void onBackPressed() {
        if(hasUpdated) {
            sendData();
        }
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            if(hasUpdated) {
                sendData();
            }
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendData() {
        Intent intent = new Intent();
        intent.putExtra("UPDATED", "UPDATED");
        intent.putExtra("ITEMPOS", clickedItemPos);
        intent.putExtra("MEDIATYPE", mediaType);
        intent.putExtra(PRODUCT_COL_TITLE, editTextTitle.getText().toString());
        intent.putExtra(PRODUCT_COL_PRICE, Integer.parseInt(editTextPrice.getText().toString()));
        intent.putExtra(PRODUCT_COL_GENRE, editTextGenre.getText().toString());
        intent.putExtra(PRODUCT_COL_RELEASE, editTextRelease.getText().toString());
        intent.putExtra(PRODUCT_COL_COMMENT, editTextComment.getText().toString());
        intent.putExtra(PRODUCT_COL_IMGURL, editTextImgUrl.getText().toString());
        switch(mediaType) {
            case BOOK_TABLE_NAME:
                intent.putExtra(BOOK_COL_AUTHOR, editTextBookAuthor.getText().toString());
                intent.putExtra(BOOK_COL_PAGES, Integer.parseInt(editTextBookPages.getText().toString()));
                intent.putExtra(BOOK_COL_TYPE, editTextBookType.getText().toString());
                intent.putExtra(BOOK_COL_PUBLISHER, editTextBookPublisher.getText().toString());
                intent.putExtra(BOOK_COL_ISBN, editTextBookIsbn.getText().toString());
                break;
            case MOVIE_TABLE_NAME:
                intent.putExtra(MOVIE_COL_LENGTH, Integer.parseInt(editTextMovieLength.getText().toString()));
                intent.putExtra(MOVIE_COL_AGE, Integer.parseInt(editTextMovieAge.getText().toString()));
                intent.putExtra(MOVIE_COL_COMPANY, editTextMovieCompany.getText().toString());
                intent.putExtra(MOVIE_COL_RATING, Integer.parseInt(editTextMovieRating.getText().toString()));
                break;
            case SONG_TABLE_NAME:
                intent.putExtra(SONG_COL_LENGTH, Integer.parseInt(editTextSongLength.getText().toString()));
                intent.putExtra(SONG_COL_LABEL, editTextSongLabel.getText().toString());
                intent.putExtra(SONG_COL_ARTIST, editTextSongArtist.getText().toString());
                break;
            case GAME_TABLE_NAME:
                intent.putExtra(GAME_COL_PLATFORM, editTextGamePlatform.getText().toString());
                intent.putExtra(GAME_COL_AGE, Integer.parseInt(editTextGameAge.getText().toString()));
                intent.putExtra(GAME_COL_DEVELOPER, editTextGameDeveloper.getText().toString());
                intent.putExtra(GAME_COL_PUBLISHER, editTextGamePublisher.getText().toString());
                break;
        }
        setResult(RESULT_OK, intent);
    }
}