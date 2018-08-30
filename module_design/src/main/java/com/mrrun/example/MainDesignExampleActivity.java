package com.mrrun.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mrrun.module_design.R;

/**
 * @author lipin
 * @version 1.0
 * @date 2018/08/24
 */
public class MainDesignExampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designexample);
        getSupportActionBar().setTitle(R.string.module_design);
        intView();
    }

    private void intView() {
        uiDrawerLayout();
        uiCDL_ABL();
    }

    /**
     * CoordinatorLayout+AppBarLayout
     */
    private void uiCDL_ABL() {
        findViewById(R.id.btn_cbl_abl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainDesignExampleActivity.this, CDLABLActivity.class));
            }
        });
    }

    /**
     * 滑动菜单---DrawerLayout
     */
    private void uiDrawerLayout() {
        findViewById(R.id.btn_drawerlayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainDesignExampleActivity.this, DrawerLayoutActivity.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
