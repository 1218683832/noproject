package com.mrrun.module_view.dialog.notification;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.mrrun.module_view.R;

/**
 * 仿微信信息通知视图对话框
 * 比如：通知网络通信问题
 *
 * @author lipin
 * @version 1.0
 * @date 2018/07/04
 */
public class NotificationDialog extends Dialog{

    private String message;

    public NotificationDialog(@NonNull Context context, String message) {
        super(context, R.style.dialog_load_style);
        this.message = message;
    }

    public NotificationDialog(@NonNull Context context, int stringResId) {
        super(context, R.style.dialog_load_style);
        this.message = context.getResources().getString(stringResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_notification);
        // 点击Dialog外部任意区域关闭Dialog
        setCanceledOnTouchOutside(false);
        initView();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
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
        TextView tv_notification = findViewById(R.id.tv_notification);
        tv_notification.setText(message);
        findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
