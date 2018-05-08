package com.mrrun.module_okhttp3.interceptors;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 网络拦截器
 * Network Interception是在请求发送数据之前
 * 1、可以修改OkHttp框架自动添加的一些属性。
 * 2、可以观察最终完整的请求参数（也就是最终服务器接收到的请求数据和信息）
 */
public class NetInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        System.out.println("This is a Network Interceptor!");
        Request request = chain.request();

        long t1 = System.nanoTime();
        System.out.println("Before request!");
        System.out.println(String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));

        Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        System.out.println("After request!");
        System.out.println(String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        return response;
    }
}
