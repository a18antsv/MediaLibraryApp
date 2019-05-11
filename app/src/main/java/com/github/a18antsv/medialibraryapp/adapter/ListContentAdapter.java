package com.github.a18antsv.medialibraryapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.a18antsv.medialibraryapp.R;
import com.github.a18antsv.medialibraryapp.object.Book;
import com.github.a18antsv.medialibraryapp.object.Game;
import com.github.a18antsv.medialibraryapp.object.Movie;
import com.github.a18antsv.medialibraryapp.object.Product;
import com.github.a18antsv.medialibraryapp.object.Song;

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
        return createView(p, convertView);
    }

    private View createView(Product p, View convertView) {
        ImageView icon = convertView.findViewById(R.id.imageview_icon);
        TextView title = convertView.findViewById(R.id.textview_title);
        TextView info1 = convertView.findViewById(R.id.textview_info1);
        TextView info2 = convertView.findViewById(R.id.textview_info2);

        title.setText(p.getTitle());
        if(p instanceof Book) {
            info1.setText(((Book) p).getAuthor());
            info2.setText(p.getGenre());
            icon.setImageResource(R.drawable.baseline_library_books_black_36dp);
        } else if(p instanceof Movie) {
            info1.setText(p.getGenre());
            info2.setText("$"+Integer.toString(p.getPrice()));
            icon.setImageResource(R.drawable.baseline_movie_black_36dp);
        } else if(p instanceof Song) {
            info1.setText(((Song) p).getArtist());
            info2.setText(p.getGenre());
            icon.setImageResource(R.drawable.baseline_music_note_black_36dp);
        } else if(p instanceof Game) {
            info1.setText(p.getGenre());
            info2.setText("$"+Integer.toString(p.getPrice()));
            icon.setImageResource(R.drawable.baseline_videogame_asset_black_36dp);
        }
        return convertView;
    }
}
