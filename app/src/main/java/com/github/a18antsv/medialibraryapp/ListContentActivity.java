package com.github.a18antsv.medialibraryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ListContentActivity extends AppCompatActivity {
    private List<Object> objectList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_content);

        Intent intent = getIntent();
        String listName = intent.getStringExtra("LISTNAME");
        setTitle(listName + " content");
    }
}
