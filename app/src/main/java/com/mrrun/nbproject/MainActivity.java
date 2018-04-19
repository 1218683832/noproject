package com.mrrun.nbproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mrrun.module_share.TT;
import com.mrrun.module_share.WXScene;
import com.mrrun.module_view.SearchView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uiShare();
        uiSearchView();
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
