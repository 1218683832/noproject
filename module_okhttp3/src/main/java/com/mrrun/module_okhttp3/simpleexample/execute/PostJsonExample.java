package com.mrrun.module_okhttp3.simpleexample.execute;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * post请求之json参数形式
 */
public class PostJsonExample {
    // 客户端
    OkHttpClient client = new OkHttpClient();

    MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public PostJsonExample() {
    }

    public String execute(String url, String json){
        RequestBody requestBody = RequestBody.create(JSON, json);
        // 请求数据
        Request request = new Request.Builder()
                .post(requestBody)
                .url(url)
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
