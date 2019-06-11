package com.xk.netutils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 1.0.0-SNAPSHOT
 * @author xuekai1
 * @date 2019-06-10
 */
public class NetWork {
    private static NetWork okSingle;
    private static OkHttpClient mOkHttpClient;
    private static Gson gson;

    private static final int SUCCESS = 1;
    private static final int FAILED = 0;

    private NetWork(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
        } else {
            mOkHttpClient = okHttpClient;
        }
        gson = new Gson();

    }

    private static NetWork initClient(OkHttpClient okHttpClient) {
        if (okSingle == null) {
            synchronized (NetWork.class) {
                if (okSingle == null) {
                    okSingle = new NetWork(okHttpClient);
                }
            }
        }
        return okSingle;

    }

    //获得单例的对象
    public static NetWork getInstance() {
        return initClient(null);

    }

    public <T> void postRequestAsync(String url,
                                     Map<String, String> params,
                                     final GetResponse.ErrorListener errorListener,
                                     final GetResponse.Listener<T> listener,
                                     final Class<T> tClass) {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : params.keySet()) {
            builder.add(key, params.get(key));
        }
        FormBody formBody = builder.build();
        Request request = new Request.Builder().url(url)
                .post(formBody)
                .build();
        request(errorListener, listener, tClass, request);
    }

    private <T> void request(final GetResponse.ErrorListener errorListener, final GetResponse.Listener<T> listener, final Class<T> tClass, Request request) {
        final Handler handler = new Handler(Looper.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case SUCCESS:
                        //成功时候的回调
                        if (listener == null) {
                            return;
                        }
                        listener.onResponse((T) msg.obj);
                        break;
                    case FAILED:
                        if (errorListener == null) {
                            return;
                        }
                        errorListener.onErrorResponse();
                        break;
                }

            }
        };

        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                errorListener.onErrorResponse();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    T t;
                    if (tClass == String.class) {
                        t = (T) result;
                    } else {
                        t = gson.fromJson(result, tClass);
                    }
                    Message message = handler.obtainMessage();//这种写法比较省内存
                    message.what = SUCCESS;
                    message.obj = t;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    //因为是失败,所以返回失败,在主线程中判断
                    handler.sendEmptyMessage(FAILED);
                }
            }


        });
    }


    public <T> void getRequestAsync(String url,
                                    final GetResponse.ErrorListener errorListener,
                                    final GetResponse.Listener<T> listener,
                                    final Class<T> tClass) {
        Request request = new Request.Builder().url(url).build();
        //这种写法,可以让Handler指定属于主线程
        request(errorListener, listener, tClass, request);
    }

    public Bitmap downLoadImage(String url) {
        Request request = new Request.Builder().url(url).build();
        //获取响应体
        ResponseBody body;
        try {
            body = mOkHttpClient.newCall(request).execute().body();
            //获取流
            InputStream in = body.byteStream();
            //转化为bitmap
            return BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    //自定义接口
    public interface GetResponse {

        //成功的接口
        interface Listener<T> {
            void onResponse(T response);
        }

        //失败的接口
        interface ErrorListener {
            void onErrorResponse();
        }
    }

}
