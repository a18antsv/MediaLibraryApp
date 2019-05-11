package com.github.a18antsv.medialibraryapp.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.a18antsv.medialibraryapp.R;

public class FragmentGetDataByExampleList extends Fragment {
    private onListExamplePassListener listExamplePasser;
    private Spinner spinner;

    public FragmentGetDataByExampleList() {}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_get_data_by_example_list, container, false);

        spinner = v.findViewById(R.id.spinner_examle_lists);
        TextView textViewClose = v.findViewById(R.id.textview_close_fragment);
        Button buttonAdd = v.findViewById(R.id.button_add_example_lists);
        String[] exampleLists = getResources().getStringArray(R.array.example_lists);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_mediatype_row, R.id.textView_mediaType, exampleLists);
        spinner.setAdapter(adapter);
        loadPrefs();

        textViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().remove(FragmentGetDataByExampleList.this).commit();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePrefs();
                String chosenExampleList = spinner.getSelectedItem().toString();
                listExamplePasser.onListExamplePass(chosenExampleList);
                getFragmentManager().beginTransaction().remove(FragmentGetDataByExampleList.this).commit();
            }
        });
        return v;
    }

    public void savePrefs() {
        SharedPreferences prefs = this.getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putInt("spinner", spinner.getSelectedItemPosition());
        prefsEditor.apply();
    }
    public void loadPrefs() {
        SharedPreferences prefs = this.getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        spinner.setSelection(prefs.getInt("spinner", 0));
    }

    public void passData(String listName) {
        listExamplePasser.onListExamplePass(listName);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof onListExamplePassListener) {
            listExamplePasser = (onListExamplePassListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement onListExamplePass");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listExamplePasser = null;
    }

    public interface onListExamplePassListener {
        void onListExamplePass(String listName);
    }

}
