package com.xk.netutils;


import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 把okhttp的响应体转为string
 *
 * @author xuekai1
 * @date 2019-07-03
 */
public class ConvertCallback<T> implements okhttp3.Callback {
    Handler handler = new Handler(Looper.getMainLooper());
    Callback<T> callback;
    Class<T> clazz;

    public ConvertCallback(Callback<T> callback, Class<T> clazz) {
        this.callback = callback;
        this.clazz = clazz;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        handler.post(() -> callback.onError(e));
    }

    @Override
    public void onResponse(Call call, Response response) {
        Gson gson = new Gson();
        try {
            if (response.isSuccessful() && response.body() != null) {
                if (clazz == String.class) {
                    T t = (T) response.body().string();
                    handler.post(() -> callback.onSuccess(t));
                } else {
                    T t = gson.fromJson(response.body().string(), clazz);
                    handler.post(() -> callback.onSuccess(t));
                }

            } else {
                handler.post(() -> callback.onError(new Exception("error code = " + response.code())));
            }
        } catch (IOException e) {
            e.printStackTrace();
            handler.post(() -> callback.onError(e));
        }
    }

    public interface Callback<T> {
        void onSuccess(T response);

        void onError(Exception e);
    }
}
