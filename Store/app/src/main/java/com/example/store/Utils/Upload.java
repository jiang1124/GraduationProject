package com.example.store.Utils;

import android.icu.util.TimeUnit;

import com.example.store.Application.BaseApplication;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 79124 on 2019/5/22.
 */

public class Upload {
    static public void  uploadFile(String imagePath,String url,String productId) {
        File file = new File(imagePath);
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("image", file.getName(), RequestBody.create(MediaType.parse("image/*"), file))
        .addFormDataPart("uuid",String.valueOf(productId))
        .addFormDataPart("imageType","product");
        MultipartBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10000, java.util.concurrent.TimeUnit.SECONDS)
                .readTimeout(10000, java.util.concurrent.TimeUnit.SECONDS)
                .writeTimeout(10000, java.util.concurrent.TimeUnit.SECONDS)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }
}
