package com.example.shoppingsystem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by 79124 on 2019/4/1.
 */

public class BaseActiity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedIdstanceState){
        super.onCreate(savedIdstanceState);
        Log.d("BaseActivity",getClass().getSimpleName());
    }
}
