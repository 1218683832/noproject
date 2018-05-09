package com.mrrun.module_retrofit2;

import org.junit.Test;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void getRequestTest() throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/") // 设置网络请求Url
                .addConverterFactory(GsonConverterFactory.create())// 设置使用Gson解析
                .build();

        // 创建 网络请求接口 的实例
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        // 对 发送请求 进行封装
        Call<Translation1> call = request.getCall();

        Response<Translation1> response = call.execute();
        System.out.println(response.body().toString());
    }

    @Test
    public void postRequestTest() throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // 创建 网络请求接口 的实例
        PostRequest_Interface request = retrofit.create(PostRequest_Interface.class);

        Call<Translation2> call = request.getCall("I love you!");

        Response<Translation2> response = call.execute();
        System.out.println(response.body().toString());
    }
}