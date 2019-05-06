package com.github.a18antsv.medialibraryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.a18antsv.medialibraryapp.objects.Book;
import com.github.a18antsv.medialibraryapp.objects.Game;
import com.github.a18antsv.medialibraryapp.objects.Movie;
import com.github.a18antsv.medialibraryapp.objects.Product;
import com.github.a18antsv.medialibraryapp.objects.Song;

import java.util.List;

public class ListContentAdapter extends ArrayAdapter {
    private Context context;
    private List<Product> productList;
    private int resource;
    private LayoutInflater inflater;

    public ListContentAdapter(Context context, int resource, List<Product> productList) {
        super(context, resource, productList);
        this.context = context;
        this.productList = productList;
        this.resource = resource;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(resource, parent, false);
        }

        Product p = productList.get(position);
        /*if(p instanceof Book) {
            createBookView((Book) p, convertView);
        } else if(p instanceof Movie) {
            createMovieView((Movie) p, convertView);
        } else if(p instanceof Song) {
            createSongView((Song) p, convertView);
        } else if(p instanceof Game) {
            createGameView((Game) p, convertView);
        }*/
        return createView(p, convertView);
    }

    private View createView(Product p, View convertView) {
        ImageView icon = convertView.findViewById(R.id.imageview_icon);
        TextView objectType = convertView.findViewById(R.id.textview_type);

        icon.setImageResource(R.drawable.baseline_library_books_black_36dp);
        objectType.setText(p.getClass().getSimpleName());
        return convertView;
    }
}
