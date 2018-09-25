package com.mrrun.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mrrun.module_view.R;
import com.mrrun.module_view.menuview.ConditionListMenuView;

/**
 * @author lipin
 * @version 1.0
 * @date 2016/06/25
 */
public class MainViewExampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewexample);
        getSupportActionBar().setTitle(R.string.module_view);
        initView();
    }

    private void initView() {
        uiSearchView();
        uiDialer();
        uiBubbleFloatView();
        uiAffirmButtonView();
        uiTag();
        uiWXloadDialog();
        uiNotificationDialog();
        uiPayPsdInputView();
        uiProgressView();
        uiFlowersLiveLoadingView1();
        uiFlowersLiveLoadingView2();
        uiColorTrackView();
        uiQQStepProgressView();
        uiLineProgressView();
        uiRatingBar();
        uiAlphabeticIndexView();
        uiIndexSidebar();
        uiLoadingView58();
        uiDropDownMenu();
        uiConditionListMenuView();
        uiLockPatternView();
        uiLoLSlidingMenu();
        uiKugouSlidingMenu();
        uiTantanSlidingMenu();
        uiVerticalDragListView();
        uiImmersiveStatusBar();
        uiBethel();
        uiParallaxAnimation();
    }

    /**
     * 视差动画
     */
    private void uiParallaxAnimation() {
        findViewById(R.id.btn_parallax_animation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainViewExampleActivity.this, ParallaxAnimationActivity.class));
            }
        });
    }

    /**
     * 贝塞尔
     */
    private void uiBethel() {
        findViewById(R.id.btn_bethel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainViewExampleActivity.this, BethelActivity.class));
            }
        });
    }

    /**
     * 沉浸式状态栏
     */
    private void uiImmersiveStatusBar() {
        findViewById(R.id.btn_immersivestatusbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainViewExampleActivity.this, ImmersiveStatusBarActivity.class));
            }
        });
    }

    /**
     * 汽车之家折叠效果
     */
    private void uiVerticalDragListView() {
        findViewById(R.id.btn_verticaldraglistview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainViewExampleActivity.this, VerticalDragListViewActivity.class));
            }
        });
    }

    /**
     * 仿探探侧滑菜单栏
     */
    private void uiTantanSlidingMenu() {
        findViewById(R.id.btn_slidingmenutantan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainViewExampleActivity.this, SlidingMenu4TantanActivity.class));
            }
        });
    }

    /**
     * 仿酷狗侧滑菜单栏
     */
    private void uiKugouSlidingMenu() {
        findViewById(R.id.btn_slidingmenukugou).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainViewExampleActivity.this, SlidingMenu4KugouActivity.class));
            }
        });
    }

    /**
     * 仿掌上英雄联盟侧滑菜单栏
     */
    private void uiLoLSlidingMenu() {
        findViewById(R.id.btn_slidingmenulol).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainViewExampleActivity.this, SlidingMenu4LoLActivity.class));
            }
        });
    }

    /**
     * 九宫格解锁
     */
    private void uiLockPatternView() {
        findViewById(R.id.btn_lockpatternview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainViewExampleActivity.this, LockPatternViewActivity.class));
            }
        });
    }

    /**
     * 条件筛选菜单
     */
    private void uiConditionListMenuView() {
        findViewById(R.id.btn_conditionlistmenuview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainViewExampleActivity.this, ConditionListMenuViewActivity.class));
            }
        });
    }

    /**
     * 常用多条件筛选菜单
     */
    private void uiDropDownMenu() {
        findViewById(R.id.btn_dropdownmenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainViewExampleActivity.this, DropDownMenuActivity.class));
            }
        });
    }

    /**
     * 仿58同城LoadingView
     */
    private void uiLoadingView58() {
        findViewById(R.id.btn_loadingview58).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainViewExampleActivity.this, LoadingView58Activity.class));
            }
        });
    }

    /**
     * 索引侧边栏
     */
    private void uiIndexSidebar() {
        findViewById(R.id.btn_indexsidebar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainViewExampleActivity.this, IndexSidebarActivity.class));
            }
        });
    }

    /**
     * 字母索引列表
     */
    private void uiAlphabeticIndexView() {
        findViewById(R.id.btn_alphabeticindexview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainViewExampleActivity.this, AlphabeticIndexViewActivity.class));
            }
        });
    }

    /**
     * 自定义评分控件
     */
    private void uiRatingBar() {
        findViewById(R.id.btn_ratingbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainViewExampleActivity.this, RatingBarActivity.class));
            }
        });
    }

    /**
     * 直线型进度效果View
     */
    private void uiLineProgressView() {
        findViewById(R.id.btn_lineprogressview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainViewExampleActivity.this, LineProgressViewActivity.class));
            }
        });
    }

    /**
     * 仿QQ运动步数进度效果
     */
    private void uiQQStepProgressView() {
        findViewById(R.id.btn_qqstepprogressview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainViewExampleActivity.this, QQStepProgressViewActivity.class));
            }
        });
    }

    private void uiColorTrackView() {
        findViewById(R.id.btn_colortrackview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainViewExampleActivity.this, ColorTrackViewActivity.class));
            }
        });
    }

    /**
     * 玩转文字变色View
     */
    private void uiFlowersLiveLoadingView2() {
        findViewById(R.id.btn_flowersliveloadingview2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainViewExampleActivity.this, FlowersLiveLoadingView2Activity.class));
            }
        });
    }

    /**
     * 仿花束直播加载动画View1
     */
    private void uiFlowersLiveLoadingView1() {
        findViewById(R.id.btn_flowersliveloadingview1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainViewExampleActivity.this, FlowersLiveLoadingView1Activity.class));
            }
        });
    }

    /**
     * 进度View
     */
    private void uiProgressView() {
        findViewById(R.id.btn_progressview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainViewExampleActivity.this, ProgressViewActivity.class));
            }
        });
    }

    /**
     * 仿微信自定义支付密码输入框
     */
    private void uiPayPsdInputView() {
        findViewById(R.id.btn_paypsdinputview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainViewExampleActivity.this, PayPsdInputViewActivity.class));
            }
        });
    }

    /**
     * 仿仿微信信息通知视图对话框
     */
    private void uiNotificationDialog() {
        findViewById(R.id.btn_notificationdialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainViewExampleActivity.this, NotificationDialogActivity.class));
            }
        });
    }

    /**
     * 仿微信支付的加载视图对话框
     */
    private void uiWXloadDialog() {
        findViewById(R.id.btn_wxloaddialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainViewExampleActivity.this, WXLoadDialogActivity.class));
            }
        });
    }

    /**
     * 自定义标签布局
     */
    private void uiTag() {
        findViewById(R.id.btn_taglayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainViewExampleActivity.this, TagActivity.class));
            }
        });
    }

    /**
     * 自定义确认按钮带动画效果
     */
    private void uiAffirmButtonView() {
        findViewById(R.id.btn_affirmbuttonview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainViewExampleActivity.this, AffirmButtonViewActivity.class));
            }
        });
    }

    /**
     * 气泡浮动View及动画
     */
    private void uiBubbleFloatView() {
        findViewById(R.id.btn_bubblefloatview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainViewExampleActivity.this, BubbleFloatViewActivitiy.class));
            }
        });
    }

    /**
     * 拨号盘View
     */
    private void uiDialer() {
        findViewById(R.id.btn_dialer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainViewExampleActivity.this, DialerActivity.class));
            }
        });
    }

    /**
     * 搜索View
     */
    private void uiSearchView() {
        findViewById(R.id.btn_searchview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainViewExampleActivity.this, SearchViewActivity.class));
            }
        });
    }
}
