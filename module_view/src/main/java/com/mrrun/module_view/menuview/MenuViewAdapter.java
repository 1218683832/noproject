package com.mrrun.module_view.menuview;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mrrun.module_view.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个实例Adapter
 *
 * @author lipin
 * @version 1.0
 * @date 2018/07/23
 */
public class MenuViewAdapter extends BaseListMenuViewAdapter {

    private List<String> mTabTexts = new ArrayList<String>();

    public MenuViewAdapter(Context context) {
        super(context);
        mTabTexts.add("城市");
        mTabTexts.add("年龄");
        mTabTexts.add("星座");
    }

    @Override
    public int getMenuCount() {
        return mTabTexts.size() > 0 ? mTabTexts.size() : 0;
    }

    @Override
    public View getMenuView(final int position, ViewGroup parent) {
        TextView view = (TextView) mInflater.inflate(R.layout.menuview_menu, parent, false);
        view.setTextColor(Color.BLACK);
        view.setText(mTabTexts.get(position));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, String.format("点击了第%d个Menu菜单", position), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public View getMenuContentView(int position, ViewGroup parent) {
        View view = null;
        if (position == 0 || position == 2) {
            view = mInflater.inflate(R.layout.menuview_menucontent1, parent, false);
            TextView textView = view.findViewById(R.id.menuContent);
            textView.setText("菜单内容" + position);
            return view;
        }
        if (position == 1){
            view = mInflater.inflate(R.layout.menuview_menucontent2, parent, false);
            TextView textView = view.findViewById(R.id.menuContent);
            textView.setText("菜单内容" + position);
            return view;
        }
        return view;
    }

    @Override
    public void openMenu(View menu) {
        TextView view = (TextView) menu;
        view.setTextColor(Color.RED);
    }

    @Override
    public void closeMenu(View menu) {
        TextView view = (TextView) menu;
        view.setTextColor(Color.BLACK);
    }
}
