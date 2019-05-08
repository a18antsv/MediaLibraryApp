package com.github.a18antsv.medialibraryapp.activity;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.a18antsv.medialibraryapp.R;
import com.github.a18antsv.medialibraryapp.database.DbHelper;

import static com.github.a18antsv.medialibraryapp.database.DataContract.Entry.*;

public class AddProductActivity extends AppCompatActivity {
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        dbHelper = new DbHelper(this);
        dbHelper.getWritableDatabase();

        String mediaTypes[] = {BOOK_TABLE_NAME, MOVIE_TABLE_NAME, SONG_TABLE_NAME, GAME_TABLE_NAME};

        final Spinner spinner = findViewById(R.id.spinner_mediaTypes);
        final Button button = findViewById(R.id.button_add_to_database);
        final ViewStub stub1 = findViewById(R.id.viewStub_book);
        final ViewStub stub2 = findViewById(R.id.viewStub_movie);
        final ViewStub stub3 = findViewById(R.id.viewStub_song);
        final ViewStub stub4 = findViewById(R.id.viewStub_game);

        stub1.setLayoutResource(R.layout.extras_book);
        stub1.inflate();
        stub2.setLayoutResource(R.layout.extras_movie);
        stub2.inflate();
        stub3.setLayoutResource(R.layout.extras_song);
        stub3.inflate();
        stub4.setLayoutResource(R.layout.extras_game);
        stub4.inflate();

        stub1.setVisibility(View.VISIBLE);
        stub2.setVisibility(View.GONE);
        stub3.setVisibility(View.GONE);
        stub4.setVisibility(View.GONE);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_mediatype_row, R.id.textView_mediaType, mediaTypes);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (adapterView.getSelectedItem().toString()) {
                    case BOOK_TABLE_NAME:
                        stub1.setVisibility(View.VISIBLE);
                        stub2.setVisibility(View.GONE);
                        stub3.setVisibility(View.GONE);
                        stub4.setVisibility(View.GONE);
                        break;
                    case MOVIE_TABLE_NAME:
                        stub1.setVisibility(View.GONE);
                        stub2.setVisibility(View.VISIBLE);
                        stub3.setVisibility(View.GONE);
                        stub4.setVisibility(View.GONE);
                        break;
                    case SONG_TABLE_NAME:
                        stub1.setVisibility(View.GONE);
                        stub2.setVisibility(View.GONE);
                        stub3.setVisibility(View.VISIBLE);
                        stub4.setVisibility(View.GONE);
                        break;
                    case GAME_TABLE_NAME:
                        stub1.setVisibility(View.GONE);
                        stub2.setVisibility(View.GONE);
                        stub3.setVisibility(View.GONE);
                        stub4.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editTextTitle = findViewById(R.id.editText_title);
                EditText editTextPrice = findViewById(R.id.editText_price);
                EditText editTextRelease = findViewById(R.id.editText_release);
                EditText editTextGenre = findViewById(R.id.editText_genre);
                EditText editTextComment = findViewById(R.id.editText_comment);

                boolean insertedRow = false;
                if(editTextTitle.getText().toString().trim().length() > 0 && editTextPrice.getText().toString().length() > 0) {
                    switch (spinner.getSelectedItem().toString()) {
                        case BOOK_TABLE_NAME:
                            EditText editTextBookPages = findViewById(R.id.editText_bookPages);
                            EditText editTextBookType = findViewById(R.id.editText_bookType);
                            EditText editTextBookPublisher = findViewById(R.id.editText_bookPublisher);
                            EditText editTextBookIsbn = findViewById(R.id.editText_bookIsbn);

                            if(editTextBookPages.getText().toString().length() > 0) {
                                int productkey = insertProductGetKey(
                                        editTextTitle.getText().toString(),
                                        Integer.parseInt(editTextPrice.getText().toString()),
                                        editTextRelease.getText().toString(),
                                        editTextGenre.getText().toString(),
                                        editTextComment.getText().toString()
                                );

                                insertedRow = dbHelper.insertIntoBook(
                                        productkey,
                                        Integer.parseInt(editTextBookPages.getText().toString()),
                                        editTextBookType.getText().toString(),
                                        editTextBookPublisher.getText().toString(),
                                        editTextBookIsbn.getText().toString()
                                );

                                Toast.makeText(getApplicationContext(), "Inserted new Book", Toast.LENGTH_SHORT).show();
                                editTextBookPages.getText().clear();
                                editTextBookType.getText().clear();
                                editTextBookPublisher.getText().clear();
                                editTextBookIsbn.getText().clear();
                            } else {
                                Toast.makeText(getApplicationContext(), "A book needs at least a number of pages...", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case MOVIE_TABLE_NAME:
                            EditText editTextMovieLength = findViewById(R.id.editText_movieLength);
                            EditText editTextMovieAge = findViewById(R.id.editText_movieAge);
                            EditText editTextMovieCompany = findViewById(R.id.editText_movieCompany);
                            EditText editTextMovieRating = findViewById(R.id.editText_movieRating);

                            if(editTextMovieLength.getText().toString().length() > 0 && editTextMovieAge.getText().toString().length() > 0 && editTextMovieRating.getText().toString().length() > 0) {
                                int productkey = insertProductGetKey(
                                        editTextTitle.getText().toString(),
                                        Integer.parseInt(editTextPrice.getText().toString()),
                                        editTextRelease.getText().toString(),
                                        editTextGenre.getText().toString(),
                                        editTextComment.getText().toString()
                                );

                                insertedRow = dbHelper.insertIntoMovie(
                                        productkey,
                                        Integer.parseInt(editTextMovieLength.getText().toString()),
                                        Integer.parseInt(editTextMovieAge.getText().toString()),
                                        editTextMovieCompany.getText().toString(),
                                        Integer.parseInt(editTextMovieRating.getText().toString())
                                );

                                Toast.makeText(getApplicationContext(), "Inserted new Movie", Toast.LENGTH_SHORT).show();
                                editTextMovieLength.getText().clear();
                                editTextMovieAge.getText().clear();
                                editTextMovieCompany.getText().clear();
                                editTextMovieRating.getText().clear();
                            } else {
                                Toast.makeText(getApplicationContext(), "A movie needs at least a length, min age and rating...", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case SONG_TABLE_NAME:
                            EditText editTextSongLength = findViewById(R.id.editText_songLength);
                            EditText editTextSongLabel = findViewById(R.id.editText_songLabel);
                            EditText editTextSongArtist = findViewById(R.id.editText_songArtist);

                            if(editTextSongLength.getText().toString().length() > 0) {
                                int productkey = insertProductGetKey(
                                        editTextTitle.getText().toString(),
                                        Integer.parseInt(editTextPrice.getText().toString()),
                                        editTextRelease.getText().toString(),
                                        editTextGenre.getText().toString(),
                                        editTextComment.getText().toString()
                                );

                                insertedRow = dbHelper.insertIntoSong(
                                        productkey,
                                        Integer.parseInt(editTextSongLength.getText().toString()),
                                        editTextSongLabel.getText().toString(),
                                        editTextSongArtist.getText().toString()
                                );

                                Toast.makeText(getApplicationContext(), "Inserted new Song", Toast.LENGTH_SHORT).show();
                                editTextSongLength.getText().clear();
                                editTextSongLabel.getText().clear();
                                editTextSongArtist.getText().clear();
                            } else {
                                Toast.makeText(getApplicationContext(), "A song needs at least a length...", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case GAME_TABLE_NAME:
                            EditText editTextGamePlatform = findViewById(R.id.editText_gamePlatform);
                            EditText editTextGameAge = findViewById(R.id.editText_gameAge);
                            EditText editTextGameDeveloper = findViewById(R.id.editText_gameDeveloper);
                            EditText editTextGamePublisher = findViewById(R.id.editText_gamePublisher);

                            if(editTextGameAge.getText().toString().length() > 0) {
                                int productkey = insertProductGetKey(
                                        editTextTitle.getText().toString(),
                                        Integer.parseInt(editTextPrice.getText().toString()),
                                        editTextRelease.getText().toString(),
                                        editTextGenre.getText().toString(),
                                        editTextComment.getText().toString()
                                );

                                insertedRow = dbHelper.insertIntoGame(
                                        productkey,
                                        editTextGamePlatform.getText().toString(),
                                        Integer.parseInt(editTextGameAge.getText().toString()),
                                        editTextGameDeveloper.getText().toString(),
                                        editTextGamePublisher.getText().toString()
                                );

                                Toast.makeText(getApplicationContext(), "Inserted new Game", Toast.LENGTH_SHORT).show();
                                editTextGamePlatform.getText().clear();
                                editTextGameAge.getText().clear();
                                editTextGameDeveloper.getText().clear();
                                editTextGamePublisher.getText().clear();
                            } else {
                                Toast.makeText(getApplicationContext(), "A game needs at least a min age...", Toast.LENGTH_SHORT).show();
                            }
                            break;
                    }

                    if(insertedRow) {
                        editTextTitle.getText().clear();
                        editTextPrice.getText().clear();
                        editTextRelease.getText().clear();
                        editTextGenre.getText().clear();
                        editTextComment.getText().clear();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "A product needs at least a title and a price...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private int insertProductGetKey(String title, int price, String release, String genre, String comment) {
        dbHelper.insertIntoProduct(title, price, release, genre, comment);
        Cursor c = dbHelper.getData(
                "SELECT " + PRODUCT_COL_KEY +
                " FROM " + PRODUCT_TABLE_NAME +
                " ORDER BY " + PRODUCT_COL_KEY + " DESC" +
                " LIMIT 1",
                null
        );
        c.moveToFirst();
        int productkey = c.getInt(c.getColumnIndex(PRODUCT_COL_KEY));
        c.close();
        return productkey;
    }
}