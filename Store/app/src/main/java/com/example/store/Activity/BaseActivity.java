package com.example.store.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.store.Utils.LogUtil;

/**
 * Created by 79124 on 2019/4/1.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        LogUtil.d("BaseActivity",getClass().getSimpleName());
    }
}
