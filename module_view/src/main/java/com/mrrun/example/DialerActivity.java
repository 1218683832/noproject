package com.mrrun.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mrrun.module_view.Debug;
import com.mrrun.module_view.R;
import com.mrrun.module_view.dialer.DialerKeyBoard;
import com.mrrun.module_view.dialer.XmlDialerKeyBoardLoader;

/**
 * @author lipin
 * @version 1.0
 * @date 2016/06/29
 */
public class DialerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialerexample);
        getSupportActionBar().setTitle(R.string.module_view);
        initView();
    }

    private void initView() {
        test();
    }

    private void test() {
        DialerKeyBoard dialerKeyBoard = XmlDialerKeyBoardLoader.getInstance(this).loadDbkKeyboard(R.xml.dkb_phone);
        Debug.D(dialerKeyBoard.toString());
    }
}
