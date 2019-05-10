package com.github.a18antsv.medialibraryapp.fragment;

import android.content.Context;
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

    public FragmentGetDataByExampleList() {}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_get_data_by_example_list, container, false);

        final Spinner spinner = v.findViewById(R.id.spinner_examle_lists);
        TextView textViewClose = v.findViewById(R.id.textview_close_fragment);
        Button buttonAdd = v.findViewById(R.id.button_add_example_lists);
        String[] exampleLists = getResources().getStringArray(R.array.example_lists);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_mediatype_row, R.id.textView_mediaType, exampleLists);
        spinner.setAdapter(adapter);

        textViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().remove(FragmentGetDataByExampleList.this).commit();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String chosenExampleList = spinner.getSelectedItem().toString();
                listExamplePasser.onListExamplePass(chosenExampleList);
                getFragmentManager().beginTransaction().remove(FragmentGetDataByExampleList.this).commit();
            }
        });

        return v;
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
