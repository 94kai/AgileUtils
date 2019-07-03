package com.xk.netutils;

import com.xk.netutils.rx.RxScheduleHelper;

import io.reactivex.Observable;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author xuekai1
 * @date 2019-06-20
 */
public class NetUtil {
    private static NetUtil netUtil;
    private OkHttpClient mOkHttpClient;

    private NetUtil() {
    }

    public static NetUtil getInstance() {
        if (netUtil == null) {
            netUtil = new NetUtil();
        }
        return netUtil;
    }

    /**
     * 可以调用它来自定义okhttpclient
     */
    public void setOkHttpClient(OkHttpClient okHttpClient) {
        mOkHttpClient = okHttpClient;
    }

    private OkHttpClient getOkHttpClient() {
        if (mOkHttpClient != null) {
            return mOkHttpClient;
        }
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
    }


    /**
     * 发起get请求
     *
     * @param url_headers_tag String Headers Object
     */
    public Observable<Response> get(Object... url_headers_tag) {
        Headers headers;
        if (getParams(url_headers_tag, 1) == null) {
            headers = new Headers.Builder().build();
        } else {
            headers = (Headers) getParams(url_headers_tag, 1);
        }
        Request request = new Request.Builder()
                .headers(headers)
                .url((String) getParams(url_headers_tag, 0))
                .tag(getParams(url_headers_tag, 2))
                .build();
        return Observable.<Response>create(emitter -> {
            //异步请求
            Response response = getOkHttpClient().newCall(request).execute();
            emitter.onNext(response);
        }).compose(RxScheduleHelper.io_main());
    }

    /**
     * 发起get请求
     *
     * @param url_headers_tag String Headers Object
     */
    public <T> void getSync(ConvertCallback.Callback<T> callback, Class<T> clazz, Object... url_headers_tag) {
        ConvertCallback<T> convertCallback = new ConvertCallback<>(callback, clazz);
        Headers headers;
        if (getParams(url_headers_tag, 1) == null) {
            headers = new Headers.Builder().build();
        } else {
            headers = (Headers) getParams(url_headers_tag, 1);
        }
        Request request = new Request.Builder()
                .headers(headers)
                .url((String) getParams(url_headers_tag, 0))
                .tag(getParams(url_headers_tag, 2))
                .build();
        new Thread(() -> getOkHttpClient().newCall(request).enqueue(convertCallback)).start();
    }

    /**
     * 发起post请求
     *
     * @param url_headers_formBody String Headers FormBody
     */
    public Observable<Response> post(Object... url_headers_formBody) {
        Headers headers;
        if (getParams(url_headers_formBody, 1) == null) {
            headers = new Headers.Builder().build();
        } else {
            headers = (Headers) getParams(url_headers_formBody, 1);
        }
        Request request = new Request.Builder()
                .headers(headers)
                .method("post", (RequestBody) getParams(url_headers_formBody, 2))
                .url((String) getParams(url_headers_formBody, 0))
                .build();
        return Observable.<Response>create(emitter -> {
            //异步请求
            Response response = getOkHttpClient().newCall(request).execute();
            emitter.onNext(response);
        }).compose(RxScheduleHelper.io_main());
    }

    /**
     * 发起post请求
     *
     * @param url_headers_formBody String Headers FormBody
     */
    public <T> void postSync(ConvertCallback.Callback<T> callback, Class<T> clazz, Object... url_headers_formBody) {
        ConvertCallback<T> convertCallback = new ConvertCallback<>(callback, clazz);

        Headers headers;
        if (getParams(url_headers_formBody, 1) == null) {
            headers = new Headers.Builder().build();
        } else {
            headers = (Headers) getParams(url_headers_formBody, 1);
        }
        Request request = new Request.Builder()
                .headers(headers)
                .method("post", (RequestBody) getParams(url_headers_formBody, 2))
                .url((String) getParams(url_headers_formBody, 0))
                .build();
        new Thread(() -> getOkHttpClient().newCall(request).enqueue(convertCallback)).start();
    }

    /**
     * 获取可变参数的值
     */
    private Object getParams(Object[] params, int index) {
        if (params == null) {
            return null;
        }
        if (index < params.length) {
            return params[index];
        } else {
            return null;
        }
    }
}

