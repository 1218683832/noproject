package com.mrrun.module_okhttp3.simpleexample.execute;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * get请求
 */
public class GetExample {
    // 客户端
    private OkHttpClient client = new OkHttpClient();

    public GetExample() {
    }

    public String execute(String url) {
        // 请求头数据
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        // 请求回调
        Call call = client.newCall(request);
        // 响应数据
        Response response;
        try {
            response = call.execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error";
    }
}
