package com.mrrun.module_okhttp3.execute;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 文件上传
 */
public class FileUpExample {
    // 客户端
    OkHttpClient client = new OkHttpClient();

    public FileUpExample() {
    }

    public String execute(String url) {
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), new File(""));
        RequestBody requestBody = new MultipartBody.Builder()
                .addFormDataPart("file", "head_img", fileBody)
                .addFormDataPart("name", "xiaoxiao")
                .build();
        // 请求头数据
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
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
