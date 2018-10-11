package com.mrrun.example;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.mrrun.module_hybridapp.Debug;
import com.mrrun.module_hybridapp.R;

/**
 * H5JS与Android代码交互
 * 1、JS调用Java代码主要是用到WebView下面的一个函数：public void addJavascriptInterface(Object obj, String interfaceName)；
 * https://blog.csdn.net/harvic880925/article/details/51464687
 *
 * @author lipin
 * @date 2018/10/11
 * @version 1.0
 */
public class H5AndroidInteractiveActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5androidinteractiveexample);
        getSupportActionBar().setTitle(R.string.module_hybridapp);
        initView();
        initWebViewSetting();
        // 更多时候，网页中需要通过JS代码来调用本地的Android代码，比如H5页面需要判断当前用户是否登录等。
        // 利用JS代码调用JAVA代码，主要是用到WebView下面的一个函数：public void addJavascriptInterface(Object obj, String interfaceName)
        // 这句的意思是把obj对象注入到WebView中，在WebView中的对象别名叫interfaceName；
        mWebView.addJavascriptInterface(new WebJsObject(this.getApplicationContext()), "android");
        loadUrl();
    }

    private void loadUrl() {
        setWebViewClient();
        // 加载assets目录文件
        mWebView.loadUrl("file:///android_asset/localhtml1.html");
    }

    private void setWebViewClient() {
        // 其实仅设置WebViewClient对象，使用它的默认回调就可以实现在WebView中加载在线URL了mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Debug.D("shouldOverrideUrlLoading--->WebResourceRequest:" + request.toString());
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Debug.D("onPageStarted");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Debug.D("onPageFinished");
            }
        });
    }

    private void initView() {
        mWebView = findViewById(R.id.webview);
    }

    /**
     * 在webview中的设置
     */
    private void initWebViewSetting() {
        WebSettings webSettings = mWebView.getSettings();
        // 设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        // 设置可以访问文件
        webSettings.setAllowFileAccess(true);
        // 设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.supportMultipleWindows();
        webSettings.setAllowContentAccess(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadsImagesAutomatically(true);
    }

    /**
     * 如果用webview点链接看了很多页以后，如果不做任何处理，点击系统“Back”键，整个浏览器会调用finish()而结束自身，
     * 如果希望浏览的网页回退而不是退出浏览器，需要在当前Activity中处理并消费掉该Back事件。
     * 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法。
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 改写物理返回键的逻辑
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (mWebView.canGoBack()){
                mWebView.goBack();// 返回上一页面
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public static class WebJsObject {

        private Context applicationContext;

        public WebJsObject(Context context) {
            this.applicationContext = context.getApplicationContext();
        }

        // addJavascriptInterface有注入漏洞
        // 为了解决addJavascriptInterface()函数的安全问题，在android:targetSdkVersion数值为17（Android4.2）及以上的APP中，JS只能访问
        // 带有 @JavascriptInterface注解的Java函数，所以如果你的android:targetSdkVersion是17+，与JS交互的Native函数中，
        // 必须添加JavascriptInterface注解，不然无效。
        @JavascriptInterface
        public void toastMessageFromH5Js(String message){
            Toast.makeText(applicationContext, "H5 JS调native，来自H5的消息:" + message, Toast.LENGTH_LONG).show();
        }
    }
}
