package com.github.a18antsv.medialibraryapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.a18antsv.medialibraryapp.R;


public class FragmentAddList extends Fragment {
    private TextView textView;
    private EditText editText;
    private Button button;
    private onDataPassListener dataPasser;

    public FragmentAddList() {}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_list, container, false);

        textView = v.findViewById(R.id.textview_close_fragment);
        editText = v.findViewById(R.id.editText_listname);
        button = v.findViewById(R.id.button_addlist);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().remove(FragmentAddList.this).commit();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText.getText().toString().trim();
                if(!text.isEmpty()) {
                    dataPasser.onDataPass(text);
                    getFragmentManager().beginTransaction().remove(FragmentAddList.this).commit();
                } else {
                    Toast.makeText(getContext(), "No input...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }

    public void passData(String data) {
        dataPasser.onDataPass(data);
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
        void onDataPass(String data);
    }
}