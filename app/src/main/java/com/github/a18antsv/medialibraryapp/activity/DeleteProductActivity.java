package com.github.a18antsv.medialibraryapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.github.a18antsv.medialibraryapp.R;
import com.github.a18antsv.medialibraryapp.adapter.ListContentAdapter;
import com.github.a18antsv.medialibraryapp.database.DbHelper;
import com.github.a18antsv.medialibraryapp.object.Book;
import com.github.a18antsv.medialibraryapp.object.Game;
import com.github.a18antsv.medialibraryapp.object.Movie;
import com.github.a18antsv.medialibraryapp.object.Product;
import com.github.a18antsv.medialibraryapp.object.Song;

import java.util.ArrayList;
import java.util.List;

import static com.github.a18antsv.medialibraryapp.database.DataContract.Entry.*;


public class DeleteProductActivity extends AppCompatActivity {
    private DbHelper dbHelper;
    private ListContentAdapter adapter;
    List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_product);

        dbHelper = new DbHelper(this);
        dbHelper.getWritableDatabase();

        ListView listView = findViewById(R.id.listview_delete_items);
        productList = new ArrayList<>();

        getBookData();
        getMovieData();
        getSongData();
        getGameData();

        adapter = new ListContentAdapter(this, R.layout.listview_list_content_item, productList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Product p = productList.get(i);

                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteProductActivity.this);
                builder.setTitle("Are you sure?");
                builder.setMessage(p.getTitle() + " will be deleted everywhere, database and lists!");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dbHelper.deleteProductFromList(p.getProductkey(), null);
                        if(p instanceof Book) {
                            dbHelper.deleteProduct(p.getProductkey(), BOOK_TABLE_NAME, true);
                        } else if(p instanceof Movie) {
                            dbHelper.deleteProduct(p.getProductkey(), MOVIE_TABLE_NAME, true);
                        } else if(p instanceof Song) {
                            dbHelper.deleteProduct(p.getProductkey(), SONG_TABLE_NAME, true);
                        } else if(p instanceof Game) {
                            dbHelper.deleteProduct(p.getProductkey(), GAME_TABLE_NAME, true);
                        }
                        dbHelper.deleteProduct(p.getProductkey(), PRODUCT_TABLE_NAME, false);
                        productList.remove(p);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), p.getTitle() + " was deleted everywhere", Toast.LENGTH_SHORT).show();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete_sort, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.option_sort_book:
                productList.clear();
                getBookData();
                getMovieData();
                getSongData();
                getGameData();
                adapter.notifyDataSetChanged();
                break;
            case R.id.option_sort_movie:
                productList.clear();
                getMovieData();
                getBookData();
                getSongData();
                getGameData();
                adapter.notifyDataSetChanged();
                break;
            case R.id.option_sort_song:
                productList.clear();
                getSongData();
                getBookData();
                getMovieData();
                getGameData();
                adapter.notifyDataSetChanged();
                break;
            case R.id.option_sort_game:
                productList.clear();
                getGameData();
                getBookData();
                getMovieData();
                getSongData();
                adapter.notifyDataSetChanged();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getBookData() {
        Cursor cBook = dbHelper.getData(
                "SELECT * FROM " +
                PRODUCT_TABLE_NAME+", "+BOOK_TABLE_NAME+
                " WHERE "+PRODUCT_COL_KEY+" = "+FOREIGNKEY_COL_PRODUCTKEY,
                null
        );
        for(cBook.moveToFirst(); !cBook.isAfterLast(); cBook.moveToNext()) {
            productList.add(new Book(
                    cBook.getInt(cBook.getColumnIndex(PRODUCT_COL_KEY)),
                    cBook.getString(cBook.getColumnIndex(PRODUCT_COL_TITLE)),
                    cBook.getInt(cBook.getColumnIndex(PRODUCT_COL_PRICE)),
                    cBook.getString(cBook.getColumnIndex(PRODUCT_COL_RELEASE)),
                    cBook.getString(cBook.getColumnIndex(PRODUCT_COL_GENRE)),
                    cBook.getString(cBook.getColumnIndex(PRODUCT_COL_COMMENT)),
                    cBook.getString(cBook.getColumnIndex(PRODUCT_COL_IMGURL)),
                    cBook.getString(cBook.getColumnIndex(BOOK_COL_AUTHOR)),
                    cBook.getString(cBook.getColumnIndex(BOOK_COL_PUBLISHER)),
                    cBook.getInt(cBook.getColumnIndex(BOOK_COL_PAGES)),
                    cBook.getString(cBook.getColumnIndex(BOOK_COL_TYPE)),
                    cBook.getString(cBook.getColumnIndex(BOOK_COL_ISBN))
            ));
        }
        cBook.close();
    }

    private void getMovieData() {
        Cursor cMovie = dbHelper.getData(
                "SELECT * FROM " +
                PRODUCT_TABLE_NAME+", "+MOVIE_TABLE_NAME+
                " WHERE "+PRODUCT_COL_KEY+" = "+FOREIGNKEY_COL_PRODUCTKEY,
                null
        );
        for(cMovie.moveToFirst(); !cMovie.isAfterLast(); cMovie.moveToNext()) {
            productList.add(new Movie(
                    cMovie.getInt(cMovie.getColumnIndex(PRODUCT_COL_KEY)),
                    cMovie.getString(cMovie.getColumnIndex(PRODUCT_COL_TITLE)),
                    cMovie.getInt(cMovie.getColumnIndex(PRODUCT_COL_PRICE)),
                    cMovie.getString(cMovie.getColumnIndex(PRODUCT_COL_RELEASE)),
                    cMovie.getString(cMovie.getColumnIndex(PRODUCT_COL_GENRE)),
                    cMovie.getString(cMovie.getColumnIndex(PRODUCT_COL_COMMENT)),
                    cMovie.getString(cMovie.getColumnIndex(PRODUCT_COL_IMGURL)),
                    cMovie.getInt(cMovie.getColumnIndex(MOVIE_COL_LENGTH)),
                    cMovie.getInt(cMovie.getColumnIndex(MOVIE_COL_AGE)),
                    cMovie.getInt(cMovie.getColumnIndex(MOVIE_COL_RATING)),
                    cMovie.getString(cMovie.getColumnIndex(MOVIE_COL_COMPANY))
            ));
        }
        cMovie.close();
    }

    private void getSongData() {
        Cursor cSong = dbHelper.getData(
                "SELECT * FROM " +
                PRODUCT_TABLE_NAME+", "+SONG_TABLE_NAME+
                " WHERE "+PRODUCT_COL_KEY+" = "+FOREIGNKEY_COL_PRODUCTKEY,
                null
        );
        for(cSong.moveToFirst(); !cSong.isAfterLast(); cSong.moveToNext()) {
            productList.add(new Song(
                    cSong.getInt(cSong.getColumnIndex(PRODUCT_COL_KEY)),
                    cSong.getString(cSong.getColumnIndex(PRODUCT_COL_TITLE)),
                    cSong.getInt(cSong.getColumnIndex(PRODUCT_COL_PRICE)),
                    cSong.getString(cSong.getColumnIndex(PRODUCT_COL_RELEASE)),
                    cSong.getString(cSong.getColumnIndex(PRODUCT_COL_GENRE)),
                    cSong.getString(cSong.getColumnIndex(PRODUCT_COL_COMMENT)),
                    cSong.getString(cSong.getColumnIndex(PRODUCT_COL_IMGURL)),
                    cSong.getString(cSong.getColumnIndex(SONG_COL_LABEL)),
                    cSong.getString(cSong.getColumnIndex(SONG_COL_ARTIST)),
                    cSong.getInt(cSong.getColumnIndex(SONG_COL_LENGTH))
            ));
        }
        cSong.close();
    }

    private void getGameData() {
        Cursor cGame = dbHelper.getData(
                "SELECT * FROM " +
                PRODUCT_TABLE_NAME+", "+GAME_TABLE_NAME+
                " WHERE "+PRODUCT_COL_KEY+" = "+FOREIGNKEY_COL_PRODUCTKEY,
                null
        );
        for(cGame.moveToFirst(); !cGame.isAfterLast(); cGame.moveToNext()) {
            productList.add(new Game(
                    cGame.getInt(cGame.getColumnIndex(PRODUCT_COL_KEY)),
                    cGame.getString(cGame.getColumnIndex(PRODUCT_COL_TITLE)),
                    cGame.getInt(cGame.getColumnIndex(PRODUCT_COL_PRICE)),
                    cGame.getString(cGame.getColumnIndex(PRODUCT_COL_RELEASE)),
                    cGame.getString(cGame.getColumnIndex(PRODUCT_COL_GENRE)),
                    cGame.getString(cGame.getColumnIndex(PRODUCT_COL_COMMENT)),
                    cGame.getString(cGame.getColumnIndex(PRODUCT_COL_IMGURL)),
                    cGame.getString(cGame.getColumnIndex(GAME_COL_PLATFORM)),
                    cGame.getString(cGame.getColumnIndex(GAME_COL_PUBLISHER)),
                    cGame.getString(cGame.getColumnIndex(GAME_COL_DEVELOPER)),
                    cGame.getInt(cGame.getColumnIndex(GAME_COL_AGE))
            ));
        }
        cGame.close();
    }
}
