package com.mrrun.module_okhttp3.execute;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 文件下载
 */
public class FileDownloadExample {
    // 客户端
    OkHttpClient client = new OkHttpClient();

    public FileDownloadExample() {
    }

    public String execute(String url){
        // 请求数据
        Request request = new Request.Builder()
                .url(url)
                .build();
        // 请求回调
        Call call = client.newCall(request);
        // 响应数据
        Response response;
        try {
            response = call.execute();
            // 文件下载就是从response中得到inputStream，做写文件操作
            downloadFile(response);
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    private void downloadFile(Response response) {
      System.out.print("下载文件");
        InputStream is = null;
        is = response.body().byteStream();
        // TODO
    }
}
