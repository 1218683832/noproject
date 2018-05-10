package com.mrrun.module_retrofit2;

import com.mrrun.module_retrofit2.aes.AESCrypt;
import com.mrrun.module_retrofit2.modelxml.VVM;

import org.junit.Test;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
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

    @Test
    public void postRequestTest3() throws IOException, NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
//        String value = AESUtil.encrypt("phone=15889939263&&os=25&chn=5100&appid=cmccuc&passid=421272567&token=84840100013402003C31613262314F545530526B56454F4552455244557A4E6A42424E7A673140687474703A2F2F3231312E3133362E31302E3133313A383038302F4030310300040109D853040006303130303031FF0020a35bec8965dbc606c27ecb8a9e0e8d43", "123");
//        String value = AESCipher.aesEncryptString("phone=15889939263&&os=25&chn=5100&appid=cmccuc&passid=421272567&token=84840100013402003C31613262314F545530526B56454F4552455244557A4E6A42424E7A673140687474703A2F2F3231312E3133362E31302E3133313A383038302F4030310300040109D853040006303130303031FF0020a35bec8965dbc606c27ecb8a9e0e8d43", "123");
        String value = AESCrypt.EncrypttoHex("phone=15889939263&&os=25&chn=5100&appid=cmccuc&passid=421272567&token=84840100013402003C31613262314F545530526B56454F4552455244557A4E6A42424E7A673140687474703A2F2F3231312E3133362E31302E3133313A383038302F4030310300040109D9D4040006303130303031FF00200f03f9624b46b74cbc434e86e3062fe8", "123");
        System.out.println("加密后: " + value);
//        RetrofitProvider.getInstence().API().getCall("a9b0c7c55c09fcc6d731fbeebb5625456bd83aa2bca00127840f29440d68cd1a18407041fc0e0b2c5c122eb2574a254a47dfa568a50e6fedff1ab58c0faeb6d920fdc6906e6ebd3dfa1b6acd6766cdf2af02c62c5df695ce56eca78dae994783f7af4d2605a8dce53a7463e90284ee797ff25bf568cadca75a9411726f70072194f2adf85113ec508ba64967e7e9770f8124a9e302127ee7b98d72c33476e11fe485e27f4493203356bf7f2c41f1e3d0aec8e1a06b5a0bef1d7ee61ca342276b83683b73b0e17b9c8d8b9644f60dc8e4f643c78981ac2157315582858e579c49061dc9a7c12d89e23cd511a9a5c3b354305ea86f53286dd2172bbb32dddb6736c51068d6ea7ba65acf5c2748b4e88d7ab38845b42b4b0660b556467d8114b0e9")
        RetrofitProvider.getInstence().API().getCall(value)
                .observeOn(Schedulers.single())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VVM>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("Disposable: " + d);
                    }

                    @Override
                    public void onNext(VVM vvm) {
                        System.out.println("onNext");
                        System.out.println(vvm.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError: " + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
    }

    @Test
    public void postRequestTest4() throws IOException {
        RetrofitProvider.getInstence().API().setCall("phone=15889939263&&os=25&chn=5100&state=0&appid=cmccuc&passid=421272567&" +
                "token=84840100013402003C31613262314F545530526B56454F4552455244557A4E6A42424E7A673140687474703A2F2F3231312E3133362E31302E3133313A383038302F4030310300040109D853040006303130303031FF0020a35bec8965dbc606c27ecb8a9e0e8d43")
                .observeOn(Schedulers.single())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VVM>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("Disposable: " + d);
                    }

                    @Override
                    public void onNext(VVM vvm) {
                        System.out.println("onNext");
                        System.out.println(vvm.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError: " + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
    }

    @Test
    public void Test111() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
                Thread.sleep(5000);
                observableEmitter.onNext("ss");
            }
        })
                .observeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                        ConsoleUtils.println("" + disposable);
                    }

                    @Override
                    public void onNext(String s) {
                        ConsoleUtils.println("" + s);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        ConsoleUtils.println("onError");
                    }

                    @Override
                    public void onComplete() {
                        ConsoleUtils.println("onComplete");
                    }
                });
    }
}