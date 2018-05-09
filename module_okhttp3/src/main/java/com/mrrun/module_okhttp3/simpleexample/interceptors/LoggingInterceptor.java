package com.mrrun.module_okhttp3.simpleexample.interceptors;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 应用拦截器
 * Application Interceptor是在请求执行刚开始，还没有执行OkHttp的核心代码前进行拦截，Application拦截器的作用：
 * 1、不需要担心是否影响OKHttp的请求策略和请求速度。
 * 2、即使是从缓存中取数据，也会执行Application拦截器。
 * 3、允许重试，即Chain.proceed()可以执行多次。（当然请不要盲目执行多次，需要加入你的逻辑判断）
 */
public class LoggingInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        System.out.println("This is a Application Interceptor!");
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
