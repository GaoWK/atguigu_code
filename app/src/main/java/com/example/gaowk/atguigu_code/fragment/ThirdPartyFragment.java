package com.example.gaowk.atguigu_code.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.gaowk.atguigu_code.base.BaseFragment;

/**
 * Created by GaoWK on 2019/1/5.
 */

public class ThirdPartyFragment extends BaseFragment {

    private static final String TAG = ThirdPartyFragment.class.getSimpleName();
    private TextView textView;

    @Override
    protected View initView() {

        Log.e(TAG,"第三方页面被初始化了");
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(20);
        textView.setTextColor(Color.BLACK);
        return textView;
    }

    @Override
    protected void initData() {
        super.initData();
        Log.e(TAG, "第三方数据初始化了...");
        textView.setText("我是第三方页面");
    }
}
