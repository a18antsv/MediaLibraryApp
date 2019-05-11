package com.github.a18antsv.medialibraryapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.github.a18antsv.medialibraryapp.R;
import com.github.a18antsv.medialibraryapp.adapter.ListContentAdapter;
import com.github.a18antsv.medialibraryapp.database.DbHelper;
import com.github.a18antsv.medialibraryapp.fragment.FragmentAddProduct;
import com.github.a18antsv.medialibraryapp.object.Book;
import com.github.a18antsv.medialibraryapp.object.Game;
import com.github.a18antsv.medialibraryapp.object.Movie;
import com.github.a18antsv.medialibraryapp.object.Product;
import com.github.a18antsv.medialibraryapp.object.Song;

import java.util.ArrayList;
import java.util.List;

import static com.github.a18antsv.medialibraryapp.database.DataContract.Entry.*;

public class ListContentActivity extends AppCompatActivity implements FragmentAddProduct.onDataPassListener {
    private List<Product> productList;
    private ListView listView;
    private DbHelper dbHelper;
    private ListContentAdapter adapter;
    private String listName;
    private FragmentAddProduct fragmentAddProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_content);

        listView = findViewById(R.id.listview_list_content);
        FloatingActionButton addProductFab = findViewById(R.id.fab_add_product);
        productList = new ArrayList<>();
        dbHelper = new DbHelper(this);
        dbHelper.getWritableDatabase();

        Intent intent = getIntent();
        listName = intent.getStringExtra("LISTNAME");
        setTitle(listName + " content");

        setup();

        addProductFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(findViewById(R.id.fragment_addproduct_container) != null) {
                    fragmentAddProduct = new FragmentAddProduct();
                    Bundle args = new Bundle();
                    args.putString("ACTION", "add");
                    fragmentAddProduct.setArguments(args);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_addproduct_container, fragmentAddProduct).commit();
                }
            }
        });
    }

    public void setup() {
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

            switch (mediaTypeTableName) {
                case MOVIE_TABLE_NAME:
                    productList.add(new Movie(
                            c2.getInt(c2.getColumnIndex(PRODUCT_COL_KEY)),
                            c2.getString(c2.getColumnIndex(PRODUCT_COL_TITLE)),
                            c2.getInt(c2.getColumnIndex(PRODUCT_COL_PRICE)),
                            c2.getString(c2.getColumnIndex(PRODUCT_COL_RELEASE)),
                            c2.getString(c2.getColumnIndex(PRODUCT_COL_GENRE)),
                            c2.getString(c2.getColumnIndex(PRODUCT_COL_COMMENT)),
                            c2.getString(c2.getColumnIndex(PRODUCT_COL_IMGURL)),
                            c2.getInt(c2.getColumnIndex(MOVIE_COL_LENGTH)),
                            c2.getInt(c2.getColumnIndex(MOVIE_COL_AGE)),
                            c2.getInt(c2.getColumnIndex(MOVIE_COL_RATING)),
                            c2.getString(c2.getColumnIndex(MOVIE_COL_COMPANY))
                    ));
                    break;
                case SONG_TABLE_NAME:
                    productList.add(new Song(
                            c2.getInt(c2.getColumnIndex(PRODUCT_COL_KEY)),
                            c2.getString(c2.getColumnIndex(PRODUCT_COL_TITLE)),
                            c2.getInt(c2.getColumnIndex(PRODUCT_COL_PRICE)),
                            c2.getString(c2.getColumnIndex(PRODUCT_COL_RELEASE)),
                            c2.getString(c2.getColumnIndex(PRODUCT_COL_GENRE)),
                            c2.getString(c2.getColumnIndex(PRODUCT_COL_COMMENT)),
                            c2.getString(c2.getColumnIndex(PRODUCT_COL_IMGURL)),
                            c2.getString(c2.getColumnIndex(SONG_COL_LABEL)),
                            c2.getString(c2.getColumnIndex(SONG_COL_ARTIST)),
                            c2.getInt(c2.getColumnIndex(SONG_COL_LENGTH))
                    ));
                    break;
                case BOOK_TABLE_NAME:
                    productList.add(new Book(
                            c2.getInt(c2.getColumnIndex(PRODUCT_COL_KEY)),
                            c2.getString(c2.getColumnIndex(PRODUCT_COL_TITLE)),
                            c2.getInt(c2.getColumnIndex(PRODUCT_COL_PRICE)),
                            c2.getString(c2.getColumnIndex(PRODUCT_COL_RELEASE)),
                            c2.getString(c2.getColumnIndex(PRODUCT_COL_GENRE)),
                            c2.getString(c2.getColumnIndex(PRODUCT_COL_COMMENT)),
                            c2.getString(c2.getColumnIndex(PRODUCT_COL_IMGURL)),
                            c2.getString(c2.getColumnIndex(BOOK_COL_AUTHOR)),
                            c2.getString(c2.getColumnIndex(BOOK_COL_PUBLISHER)),
                            c2.getInt(c2.getColumnIndex(BOOK_COL_PAGES)),
                            c2.getString(c2.getColumnIndex(BOOK_COL_TYPE)),
                            c2.getString(c2.getColumnIndex(BOOK_COL_ISBN))
                    ));
                    break;
                case GAME_TABLE_NAME:
                    productList.add(new Game(
                            c2.getInt(c2.getColumnIndex(PRODUCT_COL_KEY)),
                            c2.getString(c2.getColumnIndex(PRODUCT_COL_TITLE)),
                            c2.getInt(c2.getColumnIndex(PRODUCT_COL_PRICE)),
                            c2.getString(c2.getColumnIndex(PRODUCT_COL_RELEASE)),
                            c2.getString(c2.getColumnIndex(PRODUCT_COL_GENRE)),
                            c2.getString(c2.getColumnIndex(PRODUCT_COL_COMMENT)),
                            c2.getString(c2.getColumnIndex(PRODUCT_COL_IMGURL)),
                            c2.getString(c2.getColumnIndex(GAME_COL_PLATFORM)),
                            c2.getString(c2.getColumnIndex(GAME_COL_PUBLISHER)),
                            c2.getString(c2.getColumnIndex(GAME_COL_DEVELOPER)),
                            c2.getInt(c2.getColumnIndex(GAME_COL_AGE))
                    ));
                    break;
            }
            c2.close();
        }
        c1.close();
        adapter = new ListContentAdapter(this, R.layout.listview_list_content_item, productList);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product p = productList.get(i);

                Intent intent = new Intent(ListContentActivity.this, ProductDetailsActivity.class);
                intent.putExtra("LISTNAME", listName);
                intent.putExtra("ITEMPOS", i);
                intent.putExtra(PRODUCT_COL_KEY, p.getProductkey());
                intent.putExtra(PRODUCT_COL_TITLE, p.getTitle());
                intent.putExtra(PRODUCT_COL_PRICE, p.getPrice());
                intent.putExtra(PRODUCT_COL_RELEASE, p.getRelease());
                intent.putExtra(PRODUCT_COL_GENRE, p.getGenre());
                intent.putExtra(PRODUCT_COL_COMMENT, p.getComment());
                intent.putExtra(PRODUCT_COL_IMGURL, p.getImgUrl());

                if(p instanceof Book) {
                    Book b = (Book) p;
                    intent.putExtra("MEDIATYPE", BOOK_TABLE_NAME);
                    intent.putExtra(BOOK_COL_AUTHOR, b.getAuthor());
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
                startActivityForResult(intent, 1);
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
        return c.moveToFirst();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 1 && data != null) {
            int i = data.getIntExtra("ITEMPOS", -1);
            if(data.hasExtra("DELETED")) {
                productList.remove(i);
                adapter.notifyDataSetChanged();
            } else if(data.hasExtra("UPDATED")) {
                Product p = productList.get(i);
                String mediaType = data.getStringExtra("MEDIATYPE");

                String newTitle = data.getStringExtra(PRODUCT_COL_TITLE);
                int newPrice = data.getIntExtra(PRODUCT_COL_PRICE, -1);
                String newGenre = data.getStringExtra(PRODUCT_COL_GENRE);
                String newRelease = data.getStringExtra(PRODUCT_COL_RELEASE);
                String newComment = data.getStringExtra(PRODUCT_COL_COMMENT);
                String newImgUrl = data.getStringExtra(PRODUCT_COL_IMGURL);

                p.setTitle(newTitle);
                p.setPrice(newPrice);
                p.setGenre(newGenre);
                p.setRelease(newRelease);
                p.setComment(newComment);
                p.setImgUrl(newImgUrl);

                switch(mediaType) {
                    case BOOK_TABLE_NAME:
                        String newBookAuthor = data.getStringExtra(BOOK_COL_AUTHOR);
                        int newBookPages = data.getIntExtra(BOOK_COL_PAGES, -1);
                        String newBookType = data.getStringExtra(BOOK_COL_TYPE);
                        String newBookPublisher = data.getStringExtra(BOOK_COL_PUBLISHER);
                        String newBookIsbn = data.getStringExtra(BOOK_COL_ISBN);

                        Book b = (Book) p;
                        b.setAuthor(newBookAuthor);
                        b.setPages(newBookPages);
                        b.setType(newBookType);
                        b.setPublisher(newBookPublisher);
                        b.setIsbn(newBookIsbn);
                        break;
                    case MOVIE_TABLE_NAME:
                        int newMovieLength = data.getIntExtra(MOVIE_COL_LENGTH, -1);
                        int newMovieAge = data.getIntExtra(MOVIE_COL_AGE, -1);
                        String newMovieCompany = data.getStringExtra(MOVIE_COL_COMPANY);
                        int newMovieRating = data.getIntExtra(MOVIE_COL_RATING, -1);

                        Movie m = (Movie) p;
                        m.setLength(newMovieLength);
                        m.setAge(newMovieAge);
                        m.setCompany(newMovieCompany);
                        m.setRating(newMovieRating);
                        break;
                    case SONG_TABLE_NAME:
                        int newSongLength = data.getIntExtra(SONG_COL_LENGTH, -1);
                        String newSongLabel = data.getStringExtra(SONG_COL_LABEL);
                        String newSongArtist = data.getStringExtra(SONG_COL_ARTIST);

                        Song s = (Song) p;
                        s.setLength(newSongLength);
                        s.setLabel(newSongLabel);
                        s.setArtist(newSongArtist);
                        break;
                    case GAME_TABLE_NAME:
                        String newGamePlatform = data.getStringExtra(GAME_COL_PLATFORM);
                        int newGameAge = data.getIntExtra(GAME_COL_AGE, -1);
                        String newGameDeveloper = data.getStringExtra(GAME_COL_DEVELOPER);
                        String newGamePublisher = data.getStringExtra(GAME_COL_PUBLISHER);

                        Game g = (Game) p;
                        g.setPlatform(newGamePlatform);
                        g.setAge(newGameAge);
                        g.setDeveloper(newGameDeveloper);
                        g.setPublisher(newGamePublisher);
                        break;
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu_delete, menu);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        Product p = (Product) adapter.getItem(info.position);
        menu.setHeaderTitle(p.getTitle());
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Product p = (Product) adapter.getItem(info.position);
        switch(item.getItemId()) {
            case R.id.option_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Are you sure?");
                builder.setMessage(p.getTitle() + " will be deleted from " + listName + " but still be available in the database for future use.");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int deletedRows = dbHelper.deleteProductFromList(p.getProductkey(), listName);
                        if(deletedRows == 1) {
                            Toast.makeText(getApplicationContext(), "Successfully deleted " + p.getTitle() + " from the list named " + listName, Toast.LENGTH_SHORT).show();
                            productList.remove((int)(long)info.id);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.create().show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onDataPass(int productkey) {
        if(dbHelper.insertIntoList(productkey, listName)) {
            productList.clear();
            adapter.clear();
            setup();
        }
    }
}