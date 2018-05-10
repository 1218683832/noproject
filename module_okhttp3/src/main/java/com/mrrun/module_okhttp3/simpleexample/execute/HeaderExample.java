package com.mrrun.module_okhttp3.simpleexample.execute;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 加头部参数请求
 */
public class HeaderExample {
    // 客户端
    OkHttpClient client = new OkHttpClient();

    public HeaderExample() {
    }

    public String execute(String url, String name, String value){
        // 请求数据
        Request request = new Request.Builder()
                .addHeader(name, value)
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
