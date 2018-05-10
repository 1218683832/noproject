package com.mrrun.module_retrofit2;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RetrofitProvider {

    private static RetrofitProvider mRetrofitProvider;

    private static AddHeaderRequest_Interface mAPIFunction;

    private RetrofitProvider(){
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://120.198.250.218:8020/fetionlhsyquery/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        mAPIFunction = retrofit.create(AddHeaderRequest_Interface.class);
    }

    public static RetrofitProvider getInstence() {
        if (mRetrofitProvider == null) {
            synchronized (RetrofitProvider.class) {
                if (mRetrofitProvider == null)
                    mRetrofitProvider = new RetrofitProvider();
            }
        }
        return mRetrofitProvider;
    }

    public AddHeaderRequest_Interface API() {
        return mAPIFunction;
    }
}
