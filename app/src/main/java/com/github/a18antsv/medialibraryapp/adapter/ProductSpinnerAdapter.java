package com.github.a18antsv.medialibraryapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.a18antsv.medialibraryapp.R;
import com.github.a18antsv.medialibraryapp.object.Product;

import java.util.List;

public class ProductSpinnerAdapter extends ArrayAdapter {
    private Context context;
    private List<Product> productList;
    private int resource;
    private LayoutInflater inflater;

    public ProductSpinnerAdapter(Context context, int resource, List<Product> productList) {
        super(context, resource, productList);
        this.context = context;
        this.productList = productList;
        this.resource = resource;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createView(position, convertView, parent);
    }

    @Override
    public boolean isEnabled(int position) {
        return position == 0 ? false : true;
    }

    private View createView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(resource, parent, false);
        }

        Product p = productList.get(position);

        TextView title = convertView.findViewById(R.id.textview_title);
        TextView genre = convertView.findViewById(R.id.textview_genre);
        TextView price = convertView.findViewById(R.id.textview_price);

        title.setText(p.getTitle());

        if(position == 0) {
            genre.setText("");
            price.setText("");
        } else {
            genre.setText(p.getGenre());
            price.setText("$"+Integer.toString(p.getPrice()));
        }
        return convertView;
    }

}
