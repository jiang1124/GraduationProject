package com.example.shoppingsystem.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.shoppingsystem.R;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = getIntent();
        String key = intent.getStringExtra("key");
        String sort = intent.getStringExtra("sort");
        if(key!=null)
            Toast.makeText(this,key,Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,sort,Toast.LENGTH_SHORT).show();
    }
}
