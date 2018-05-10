package com.mrrun.module_retrofit2;

import com.mrrun.module_retrofit2.modelxml.VVM;

import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AddHeaderRequest_Interface {

    // 采用@Post表示Post方法进行请求（传入部分url地址）
    // 采用@FormUrlEncoded注解的原因:API规定采用请求格式x-www-form-urlencoded,即表单形式
    // 需要配合@Field 向服务器提交需要的字段
//    @POST("http://120.198.250.218:8020/fetionlhsyquery")
//    Call<VVM> getCall(@Header("vvm") String vvm);

    @POST("http://120.198.250.218:8020/fetionlhsyquery")
    Observable<VVM> getCall(@Header("vvm") String vvm);

    @POST("http://120.198.250.218:8020/fetionlhsyquery")
    Observable<VVM> setCall(@Header("vvm") String vvm);
}
