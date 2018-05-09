package com.mrrun.module_okhttp3.simpleexample.interceptors;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 网络拦截器
 * 拦截读取Json格式数据
 */
public class ReadJsonInterceptor implements Interceptor {

    MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        System.out.println("ReadJsonInterceptor 拦截读取Json格式数据");
        Response response =  chain.proceed(chain.request());
        Response newResponse = null;
        MediaType mediaType = response.body().contentType();
        if (mediaType.type().equals(JSON.type())){// Json格式数据
            System.out.println("约定的Json格式数据");
            String content = response.body().string();
            System.out.println("内容是:" + content);
            newResponse = response.newBuilder()
                    .body(ResponseBody.create(mediaType, content))
                    .build();
        } else {
            System.out.println("非约定的Json格式数据");
        }
        return newResponse != null ? newResponse : response;
    }
}
