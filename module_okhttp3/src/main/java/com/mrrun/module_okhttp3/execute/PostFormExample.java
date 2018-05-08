package com.mrrun.module_okhttp3.execute;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * post请求之form表单形式
 */
public class PostFormExample {
    // 客户端
    OkHttpClient client = new OkHttpClient();

    public PostFormExample() {
    }

    public String execute(String url, Map<String, String> formMap){
        FormBody.Builder builder = new FormBody.Builder();
        if (formMap != null && formMap.size() > 0){
            for (String name : formMap.keySet()) {
                builder.add(name, formMap.get(name));
            }
        }
        RequestBody requestBody = builder.build();
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
