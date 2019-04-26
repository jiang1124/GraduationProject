package com.example.store.Application;

import android.app.Application;
import android.content.Context;

/**
 * Created by 79124 on 2019/4/12.
 */

public class BaseApplication extends Application {
    private static Context context;
    @Override
    public void onCreate(){
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
