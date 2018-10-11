package com.mrrun.example;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mrrun.module_hybridapp.Debug;
import com.mrrun.module_hybridapp.R;

/**
 * 在WebView中加载网页
 * 参考：https://blog.csdn.net/harvic880925/article/details/51464687
 *
 * @author lipin
 * @date 2018/10/11
 * @version 1.0
 */
public class WebviewLoadUrlActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webviewloadurlexample);
        getSupportActionBar().setTitle(R.string.module_hybridapp);
        initView();
        initWebViewSetting();
        loadUrl();
    }

    private void loadUrl() {
        // 在Android中是通过webView来加载html页面的，根据HTML文件所在的位置不同写法也不同：
        // 例如：加载assets文件夹下的test.html页面mWebView.loadUrl("file:///android_asset/test.html");
        // 例如：加载网页mWebView.loadUrl("http://www.baidu.com");
        /**
         * 如果只是这样调用mWebView.loadUrl()加载的话,那么当你点击页面中的链接时，页面将会在你手机默认的浏览器上打开。
         * 那如果想要页面在App内中打开的话，就得设置setWebViewClient：
         */
        setWebViewClient();
        // mWebView.loadUrl("https://www.baidu.com/");
        mWebView.loadUrl("https://uat.cmpay.com/user/intoFetionAuthorize.xhtml?MBL_NO=13787164117&SIG_VAL=15D8EACF1B6917BE596DCDAD52B36233");
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
}
