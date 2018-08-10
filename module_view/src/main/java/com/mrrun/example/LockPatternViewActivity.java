package com.mrrun.example;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.mrrun.module_view.Debug;
import com.mrrun.module_view.R;
import com.mrrun.module_view.widget.LockPatternView;

public class LockPatternViewActivity extends AppCompatActivity {

    private TextView textView;

    private LockPatternView lockPatternView;

    private boolean hasLock = false;

    private String mSavePwd;

    private String mUnLockedPwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lockpatternviewexample);
        getSupportActionBar().setTitle(R.string.module_view);
        initView();
    }

    private void initView() {
        textView = findViewById(R.id.text);
        lockPatternView = findViewById(R.id.lockpatternview);
        lockPatternView.setOnLockListener(new LockPatternView.OnLockListener() {
            @Override
            public void onLockedPassword(String currentPassword, int type) {
                switch (type){
                    case LockPatternView.OnLockListener.FIRST_SET_UNLOCK:
                        mSavePwd = currentPassword;
                        textView.setText(R.string.set_gesture_password_again);
                        break;
                    case LockPatternView.OnLockListener.SECOND_SET_UNLOCK:
                        mUnLockedPwd = currentPassword;
                        textView.setText(R.string.set_gesture_password_again);
                        break;
                    case LockPatternView.OnLockListener.ONLY_UNLOCK:
                        mUnLockedPwd = currentPassword;
                        break;
                }
                Toast.makeText(LockPatternViewActivity.this, String.format("currentPassword=%s", currentPassword), Toast.LENGTH_SHORT).show();
                Debug.D(String.format("currentPassword=%s, type=%d", currentPassword, type));
            }

            @Override
            public boolean onLockedCompared() {
                Debug.D(String.format("mSavePwd=%s, mUnLockedPwd=%s", mSavePwd, mUnLockedPwd));
                return mSavePwd.equals(mUnLockedPwd);
            }

            @Override
            public void onSucceed(int code) {
                switch (code){
                    case LockPatternView.OnLockListener.SET_SUCCEED:
                        textView.setText(R.string.set_gesture_password_secceed);
                        SharedPreferences sp = getSharedPreferences("lock", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putBoolean("haslock", true);
                        editor.putString("savedlockpwd", mUnLockedPwd);
                        editor.commit();
                        break;
                    case LockPatternView.OnLockListener.UNLOCK_SUCCEED:
                        textView.setText(R.string.unlock_gesture_password_secceed);
                        break;
                }
                Debug.D(String.format("code=%d", code));
            }

            @Override
            public void onFail(int code) {
                switch (code){
                    case LockPatternView.OnLockListener.SET_FAIL:
                        textView.setText(R.string.set_gesture_password_fail);
                        break;
                    case LockPatternView.OnLockListener.UNLOCK_FAIL:
                        textView.setText(R.string.unlock_gesture_password_fail);
                        break;
                }
                Debug.D(String.format("code=%d", code));
            }
        });
        lockPatternView.setCanDraw(true);
        SharedPreferences sp = getSharedPreferences("lock", Context.MODE_PRIVATE);
        hasLock = sp.getBoolean("haslock", false);
        mSavePwd = sp.getString("savedlockpwd","");
        if (hasLock){
            lockPatternView.resetPassword(false);
            textView.setText(R.string.draw_unlock);
        } else {
            lockPatternView.resetPassword(true);
            mSavePwd = null;
            mUnLockedPwd = null;
            textView.setText(R.string.set_gesture_password);
        }
    }
}
