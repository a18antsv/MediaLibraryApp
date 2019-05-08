package com.github.a18antsv.medialibraryapp.fragment;

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
    private TextView textViewClose;
    private TextView textViewInfo;
    private EditText editText;
    private Button button;
    private onDataPassListener dataPasser;
    private String action;
    private String listName;
    private int itemPos;

    public FragmentAddList() {}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_list, container, false);

        textViewClose = v.findViewById(R.id.textview_close_fragment);
        textViewInfo = v.findViewById(R.id.textview_info);
        editText = v.findViewById(R.id.editText_listname);
        button = v.findViewById(R.id.button_addlist);
        action = getArguments().getString("ACTION");
        listName = getArguments().getString("LISTNAME");
        itemPos = getArguments().getInt("ITEMPOS");

        if(action == "edit") {
            editText.setText(listName);
            textViewInfo.setText("Change name");
            button.setText("Change");
        }

        textViewClose.setOnClickListener(new View.OnClickListener() {
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
                    dataPasser.onDataPass(action, text, listName, itemPos);
                    getFragmentManager().beginTransaction().remove(FragmentAddList.this).commit();
                } else {
                    Toast.makeText(getContext(), "No input...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }

    public void passData(String action, String data, String listName, int itemPos) {
        dataPasser.onDataPass(action, data, listName, itemPos);
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
        void onDataPass(String action, String data, String listName, int itemPos);
    }
}