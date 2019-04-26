package com.example.store.Utils;

/**
 * Created by 79124 on 2019/4/17.
 */

public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}
