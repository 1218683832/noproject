package com.mrrun.nbproject;

import android.content.res.XmlResourceParser;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.Button;

import com.mrrun.module_share.wx.TT;
import com.mrrun.module_share.wx.WXScene;
import com.mrrun.module_view.Debug;
import com.mrrun.module_view.searchview.SearchView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uiShare();
        uiSearchView();
        uiDialer();
        uiXmlData();
    }

    private void uiXmlData() {
        Button btn = findViewById(R.id.loadXmlData);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadXmDlata();
            }

            private void loadXmDlata() {
                // 得到XML解析器
                XmlResourceParser xmlResourceParser = MainActivity.this.getResources().getXml(R.xml.dkb_template);
                try {
                    int event = xmlResourceParser.next();
                    while (event != XmlPullParser.END_DOCUMENT){// 文档结束标志
                        switch (event) {
                            case XmlPullParser.START_DOCUMENT:{
                                Debug.D("xml解析开始");
                                break;
                            }
                            case XmlPullParser.START_TAG:{
                                // 一般都是获取标签的属性值，所以在这里获取你需要的数据
                                Debug.D("当前标签是：" + xmlResourceParser.getName() + "开始");
                                break;
                            }
                            case XmlPullParser.END_TAG: {
                                Debug.D("当前标签是：" + xmlResourceParser.getName() + "结束");
                                break;
                            }
                            case XmlPullParser.TEXT:{
                                Debug.D("Text:" + xmlResourceParser.getText());
                                break;
                            }
                            default:
                                break;
                        }
                        event = xmlResourceParser.next();
                    }
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void uiDialer() {
    }

    private void uiSearchView() {
        SearchView searchView = findViewById(R.id.searchview);
    }

    private void uiShare() {
        final TT tt = new TT(this.getApplicationContext());
        Button share = findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tt.share(WXScene.WXSceneSession);
            }
        });
    }
}
