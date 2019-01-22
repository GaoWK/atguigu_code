package com.example.gaowk.atguigu_code.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gaowk.atguigu_code.R;
import com.example.gaowk.atguigu_code.activity.OKHttpActivity;
import com.example.gaowk.atguigu_code.adapter.CommonFrameFragmentAdapter;
import com.example.gaowk.atguigu_code.base.BaseFragment;

/**
 * Created by GaoWK on 2019/1/5.
 */

public class CommonFrameFragment extends BaseFragment {

    private static final String TAG = CommonFrameFragment.class.getSimpleName();
    private ListView mListView;
    private String mDatas[];
    private CommonFrameFragmentAdapter adapter;


    @Override
    protected View initView() {
        Log.e(TAG,"常用框架页面被初始化了");
        View view = View.inflate(mContext, R.layout.fragment_common_frame,null);
        mListView = view.findViewById(R.id.listview);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String data = mDatas[i];
                if(data.toLowerCase().equals("okhttp")){
                    Intent intent = new Intent(mContext,OKHttpActivity.class);
                    mContext.startActivity(intent);
                }
                Toast.makeText(mContext,"data=="+data,Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        Log.e(TAG, "常用框架数据初始化了...");
        //准备数据
        mDatas = new String[]{"OKHttp", "xUtils3","Retrofit2","Fresco","Glide","greenDao","RxJava","volley","Gson","FastJson","picasso","evenBus","jcvideoplayer","pulltorefresh","Expandablelistview","UniversalVideoView","....."};
        //设置适配器
        adapter = new CommonFrameFragmentAdapter(mContext,mDatas);
        mListView.setAdapter(adapter);
    }
}
