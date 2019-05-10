package com.github.a18antsv.medialibraryapp.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.a18antsv.medialibraryapp.database.DbHelper;
import com.github.a18antsv.medialibraryapp.adapter.ProductSpinnerAdapter;
import com.github.a18antsv.medialibraryapp.R;
import com.github.a18antsv.medialibraryapp.object.Product;

import java.util.ArrayList;
import java.util.List;

import static com.github.a18antsv.medialibraryapp.database.DataContract.Entry.*;

public class FragmentAddProduct extends Fragment {
    private TextView textViewClose, textViewInfo;
    private onDataPassListener dataPasser;
    private Spinner spinnerBooks, spinnerMovies, spinnerSongs, spinnerGames;
    private Button buttonAddProduct;
    private DbHelper dbHelper;
    private ProductSpinnerAdapter adapter;
    private List<Product> bookList;
    private List<Product> movieList;
    private List<Product> songList;
    private List<Product> gameList;

    public FragmentAddProduct() {}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_product, container, false);

        textViewClose = v.findViewById(R.id.textview_close_fragment);
        textViewInfo = v.findViewById(R.id.textview_info);
        spinnerBooks = v.findViewById(R.id.spinner_books);
        spinnerMovies = v.findViewById(R.id.spinner_movies);
        spinnerSongs = v.findViewById(R.id.spinner_songs);
        spinnerGames = v.findViewById(R.id.spinner_games);
        buttonAddProduct = v.findViewById(R.id.button_addproduct);

        dbHelper = new DbHelper(getContext());
        dbHelper.getWritableDatabase();

        bookList = getProductListByType(BOOK_TABLE_NAME);
        movieList = getProductListByType(MOVIE_TABLE_NAME);
        songList = getProductListByType(SONG_TABLE_NAME);
        gameList = getProductListByType(GAME_TABLE_NAME);

        adapter = new ProductSpinnerAdapter(getContext(), R.layout.spinner_product_row, bookList);
        spinnerBooks.setAdapter(adapter);
        adapter = new ProductSpinnerAdapter(getContext(), R.layout.spinner_product_row, movieList);
        spinnerMovies.setAdapter(adapter);
        adapter = new ProductSpinnerAdapter(getContext(), R.layout.spinner_product_row, songList);
        spinnerSongs.setAdapter(adapter);
        adapter = new ProductSpinnerAdapter(getContext(), R.layout.spinner_product_row, gameList);
        spinnerGames.setAdapter(adapter);

        textViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().remove(FragmentAddProduct.this).commit();
            }
        });

        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product b = (Product) spinnerBooks.getSelectedItem();
                Product m = (Product) spinnerMovies.getSelectedItem();
                Product s = (Product) spinnerSongs.getSelectedItem();
                Product g = (Product) spinnerGames.getSelectedItem();
                int itemsSelected = 0;

                if(b.getProductkey() != -1) {
                    itemsSelected++;
                    dataPasser.onDataPass(b.getProductkey());
                }
                if(m.getProductkey() != -1) {
                    itemsSelected++;
                    dataPasser.onDataPass(m.getProductkey());
                }
                if(s.getProductkey() != -1) {
                    itemsSelected++;
                    dataPasser.onDataPass(s.getProductkey());
                }
                if(g.getProductkey() != -1) {
                    itemsSelected++;
                    dataPasser.onDataPass(g.getProductkey());
                }

                if(itemsSelected > 0 ) {
                    getFragmentManager().beginTransaction().remove(FragmentAddProduct.this).commit();
                } else {
                    Toast.makeText(getContext(), "No items selected... ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }

    private List<Product> getProductListByType(String table) {
        List<Product> productList = new ArrayList<>();
        productList.add(0, new Product(-1, "Select " + table, 0, "", "", "", ""));

        Cursor c = dbHelper.getData(
                "SELECT * FROM " +
                PRODUCT_TABLE_NAME+", "+table +
                " WHERE "+PRODUCT_TABLE_NAME+"."+PRODUCT_COL_KEY+" = "+table+"."+FOREIGNKEY_COL_PRODUCTKEY,
                null
        );

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            productList.add(new Product(
                    c.getInt(c.getColumnIndex(PRODUCT_COL_KEY)),
                    c.getString(c.getColumnIndex(PRODUCT_COL_TITLE)),
                    c.getInt(c.getColumnIndex(PRODUCT_COL_PRICE)),
                    c.getString(c.getColumnIndex(PRODUCT_COL_RELEASE)),
                    c.getString(c.getColumnIndex(PRODUCT_COL_GENRE)),
                    c.getString(c.getColumnIndex(PRODUCT_COL_COMMENT)),
                    c.getString(c.getColumnIndex(PRODUCT_COL_IMGURL))
            ));
        }
        c.close();
        return productList;
    }

    public void passData(int productkey) {
        dataPasser.onDataPass(productkey);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof onDataPassListener) {
            dataPasser = (onDataPassListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement onDataPass");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dataPasser = null;
    }

    public interface onDataPassListener {
        void onDataPass(int productkey);
    }
}