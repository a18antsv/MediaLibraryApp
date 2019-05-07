package com.github.a18antsv.medialibraryapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.a18antsv.medialibraryapp.objects.Book;
import com.github.a18antsv.medialibraryapp.objects.Game;
import com.github.a18antsv.medialibraryapp.objects.Movie;
import com.github.a18antsv.medialibraryapp.objects.Product;
import com.github.a18antsv.medialibraryapp.objects.Song;

import java.util.ArrayList;
import java.util.List;

import static com.github.a18antsv.medialibraryapp.DataContract.Entry.*;

public class ListContentActivity extends AppCompatActivity {
    private List<Product> productList;
    private ListView listView;
    private DbHelper dbHelper;
    private ListContentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_content);

        listView = (ListView) findViewById(R.id.listview_list_content);
        productList = new ArrayList<>();
        dbHelper = new DbHelper(this);
        dbHelper.getWritableDatabase();

        Intent intent = getIntent();
        String listName = intent.getStringExtra("LISTNAME");
        setTitle(listName + " content");

        Cursor c1 = dbHelper.getData(
                "SELECT "+LISTHASPRODUCT_TABLE_NAME+"."+FOREIGNKEY_COL_PRODUCTKEY + " FROM " +
                LIST_TABLE_NAME+", "+LISTHASPRODUCT_TABLE_NAME+
                " WHERE "+LIST_TABLE_NAME+"."+LIST_COL_NAME+" = "+LISTHASPRODUCT_TABLE_NAME+"."+FOREIGNKEY_COL_LISTNAME+
                " AND "+LIST_TABLE_NAME+"."+LIST_COL_NAME+"=?",
                new String[] {listName}
        );
        for(c1.moveToFirst(); !c1.isAfterLast(); c1.moveToNext()) {
            int productkey = c1.getInt(c1.getColumnIndex(FOREIGNKEY_COL_PRODUCTKEY));
            String mediaTypeTableName = "";
            if(decideChildClass(MOVIE_TABLE_NAME, new String[] {String.valueOf(productkey)})) {
                mediaTypeTableName = MOVIE_TABLE_NAME;
            } else if(decideChildClass(SONG_TABLE_NAME, new String[] {String.valueOf(productkey)})) {
                mediaTypeTableName = SONG_TABLE_NAME;
            } else if(decideChildClass(BOOK_TABLE_NAME, new String[] {String.valueOf(productkey)})) {
                mediaTypeTableName = BOOK_TABLE_NAME;
            } else if(decideChildClass(GAME_TABLE_NAME, new String[] {String.valueOf(productkey)})) {
                mediaTypeTableName = GAME_TABLE_NAME;
            }
            Cursor c2 = dbHelper.getData(
                    "SELECT * FROM "+
                    PRODUCT_TABLE_NAME+", "+mediaTypeTableName+
                    " WHERE "+PRODUCT_TABLE_NAME+"."+PRODUCT_COL_KEY+" = "+mediaTypeTableName+"."+FOREIGNKEY_COL_PRODUCTKEY+
                    " AND "+PRODUCT_TABLE_NAME+"."+PRODUCT_COL_KEY+"=?",
                    new String[] {String.valueOf(productkey)}
            );
            c2.moveToFirst();
            if(mediaTypeTableName.equals(MOVIE_TABLE_NAME)) {
                productList.add(new Movie(
                        c2.getInt(c2.getColumnIndex(PRODUCT_COL_KEY)),
                        c2.getString(c2.getColumnIndex(PRODUCT_COL_TITLE)),
                        c2.getInt(c2.getColumnIndex(PRODUCT_COL_PRICE)),
                        c2.getString(c2.getColumnIndex(PRODUCT_COL_RELEASE)),
                        c2.getString(c2.getColumnIndex(PRODUCT_COL_GENRE)),
                        c2.getString(c2.getColumnIndex(PRODUCT_COL_COMMENT)),
                        c2.getInt(c2.getColumnIndex(MOVIE_COL_LENGTH)),
                        c2.getInt(c2.getColumnIndex(MOVIE_COL_AGE)),
                        c2.getInt(c2.getColumnIndex(MOVIE_COL_RATING)),
                        c2.getString(c2.getColumnIndex(MOVIE_COL_COMPANY))
                ));
            } else if(mediaTypeTableName.equals(SONG_TABLE_NAME)) {
                productList.add(new Song(
                        c2.getInt(c2.getColumnIndex(PRODUCT_COL_KEY)),
                        c2.getString(c2.getColumnIndex(PRODUCT_COL_TITLE)),
                        c2.getInt(c2.getColumnIndex(PRODUCT_COL_PRICE)),
                        c2.getString(c2.getColumnIndex(PRODUCT_COL_RELEASE)),
                        c2.getString(c2.getColumnIndex(PRODUCT_COL_GENRE)),
                        c2.getString(c2.getColumnIndex(PRODUCT_COL_COMMENT)),
                        c2.getString(c2.getColumnIndex(SONG_COL_LABEL)),
                        c2.getString(c2.getColumnIndex(SONG_COL_ARTIST)),
                        c2.getInt(c2.getColumnIndex(SONG_COL_LENGTH))
                ));
            } else if(mediaTypeTableName.equals(BOOK_TABLE_NAME)) {
                productList.add(new Book(
                        c2.getInt(c2.getColumnIndex(PRODUCT_COL_KEY)),
                        c2.getString(c2.getColumnIndex(PRODUCT_COL_TITLE)),
                        c2.getInt(c2.getColumnIndex(PRODUCT_COL_PRICE)),
                        c2.getString(c2.getColumnIndex(PRODUCT_COL_RELEASE)),
                        c2.getString(c2.getColumnIndex(PRODUCT_COL_GENRE)),
                        c2.getString(c2.getColumnIndex(PRODUCT_COL_COMMENT)),
                        c2.getString(c2.getColumnIndex(BOOK_COL_PUBLISHER)),
                        c2.getInt(c2.getColumnIndex(BOOK_COL_PAGES)),
                        c2.getString(c2.getColumnIndex(BOOK_COL_TYPE)),
                        c2.getString(c2.getColumnIndex(BOOK_COL_ISBN))
                ));
            } else if(mediaTypeTableName.equals(GAME_TABLE_NAME)) {
                productList.add(new Game(
                        c2.getInt(c2.getColumnIndex(PRODUCT_COL_KEY)),
                        c2.getString(c2.getColumnIndex(PRODUCT_COL_TITLE)),
                        c2.getInt(c2.getColumnIndex(PRODUCT_COL_PRICE)),
                        c2.getString(c2.getColumnIndex(PRODUCT_COL_RELEASE)),
                        c2.getString(c2.getColumnIndex(PRODUCT_COL_GENRE)),
                        c2.getString(c2.getColumnIndex(PRODUCT_COL_COMMENT)),
                        c2.getString(c2.getColumnIndex(GAME_COL_PLATFORM)),
                        c2.getString(c2.getColumnIndex(GAME_COL_PUBLISHER)),
                        c2.getString(c2.getColumnIndex(GAME_COL_DEVELOPER)),
                        c2.getInt(c2.getColumnIndex(GAME_COL_AGE))
                ));
            }
            c2.close();
        }
        c1.close();
        adapter = new ListContentAdapter(this, R.layout.listview_list_content_item, productList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product p = productList.get(i);

                Intent intent = new Intent(ListContentActivity.this, ProductDetailsActivity.class);
                intent.putExtra(PRODUCT_COL_KEY, p.getProductkey());
                intent.putExtra(PRODUCT_COL_TITLE, p.getTitle());
                intent.putExtra(PRODUCT_COL_PRICE, p.getPrice());
                intent.putExtra(PRODUCT_COL_RELEASE, p.getRelease());
                intent.putExtra(PRODUCT_COL_GENRE, p.getGenre());
                intent.putExtra(PRODUCT_COL_COMMENT, p.getComment());

                if(p instanceof Book) {
                    Book b = (Book) p;
                    intent.putExtra("MEDIATYPE", BOOK_TABLE_NAME);
                    intent.putExtra(BOOK_COL_PAGES, b.getPages());
                    intent.putExtra(BOOK_COL_TYPE, b.getType());
                    intent.putExtra(BOOK_COL_PUBLISHER, b.getPublisher());
                    intent.putExtra(BOOK_COL_ISBN, b.getIsbn());
                } else if(p instanceof Movie) {
                    Movie m = (Movie) p;
                    intent.putExtra("MEDIATYPE", MOVIE_TABLE_NAME);
                    intent.putExtra(MOVIE_COL_LENGTH, m.getLength());
                    intent.putExtra(MOVIE_COL_AGE, m.getAge());
                    intent.putExtra(MOVIE_COL_COMPANY, m.getCompany());
                    intent.putExtra(MOVIE_COL_RATING, m.getRating());
                } else if(p instanceof Song) {
                    Song s = (Song) p;
                    intent.putExtra("MEDIATYPE", SONG_TABLE_NAME);
                    intent.putExtra(SONG_COL_LENGTH, s.getLength());
                    intent.putExtra(SONG_COL_LABEL, s.getLabel());
                    intent.putExtra(SONG_COL_ARTIST, s.getArtist());
                } else if(p instanceof Game) {
                    Game g = (Game) p;
                    intent.putExtra("MEDIATYPE", GAME_TABLE_NAME);
                    intent.putExtra(GAME_COL_PLATFORM, g.getPlatform());
                    intent.putExtra(GAME_COL_AGE, g.getAge());
                    intent.putExtra(GAME_COL_DEVELOPER, g.getDeveloper());
                    intent.putExtra(GAME_COL_PUBLISHER, g.getPublisher());
                }
                startActivity(intent);
            }
        });
    }

    public boolean decideChildClass(String table, String[] selectionArgs) {
        Cursor c = dbHelper.getData(
                "SELECT "+table+"."+FOREIGNKEY_COL_PRODUCTKEY+" FROM "+
                PRODUCT_TABLE_NAME+", "+table+
                " WHERE "+PRODUCT_TABLE_NAME+"."+PRODUCT_COL_KEY+" = "+table+"."+FOREIGNKEY_COL_PRODUCTKEY+
                " AND "+table+"."+FOREIGNKEY_COL_PRODUCTKEY+"=?",
                selectionArgs
        );
        return c.moveToFirst() ? true : false;
    }
}