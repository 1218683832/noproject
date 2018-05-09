package com.mrrun.module_okhttp3;

import com.mrrun.module_okhttp3.simpleexample.interceptors.LoggingInterceptor;
import com.mrrun.module_okhttp3.simpleexample.interceptors.NetInterceptor;
import com.mrrun.module_okhttp3.simpleexample.interceptors.ReadJsonInterceptor;

import org.junit.Test;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class InterceptorUnitTest {

    @Test
    public void logggingInterceptorTest(){
        String url = "https://www.baidu.com/";
        // 客户端
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())// Application拦截器
                .build();
        // 请求头数据
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void netInterceptorTest(){
        String url = "https://www.baidu.com/";
        // 客户端
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new NetInterceptor())// 网络拦截器
                .build();
        // 请求头数据
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readJsonInterceptorTest(){
        String url = "http://baike.baidu.com/api/openapi/BaikeLemmaCardApi?scope=103&format=json&appid=379020&bk_key=%E9%93%B6%E9%AD%82&bk_length=600";
        // 客户端
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new ReadJsonInterceptor())// Application拦截器
                .build();
        // 请求头数据
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
