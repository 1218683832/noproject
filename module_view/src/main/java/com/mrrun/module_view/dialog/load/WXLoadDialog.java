package com.mrrun.module_view.dialog.load;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.mrrun.module_view.R;

/**
 * 仿微信支付加载视图对话框
 *
 * @author lipin
 * @version 1.0
 * @date 2018/07/04
 */
public class WXLoadDialog extends Dialog{

    public WXLoadDialog(@NonNull Context context) {
        super(context, R.style.dialog_load_style);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_wxload);
        // 点击Dialog外部任意区域关闭Dialog
        setCanceledOnTouchOutside(false);
        initView();
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        // 显示位置
        /*Window win = this.getWindow();
        LayoutParams params = new LayoutParams();
        params.x = -80;//设置x坐标
        params.y = -60;//设置y坐标
        win.setAttributes(params);*/
    }
}
