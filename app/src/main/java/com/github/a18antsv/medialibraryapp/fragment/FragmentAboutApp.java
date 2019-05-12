package com.github.a18antsv.medialibraryapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.a18antsv.medialibraryapp.R;

public class FragmentAboutApp extends Fragment {
    private TextView textViewClose;

    public FragmentAboutApp() {}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about_app, container, false);

        textViewClose = v.findViewById(R.id.textview_close_fragment);

        textViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().remove(FragmentAboutApp.this).commit();
            }
        });
        return v;
    }
}
