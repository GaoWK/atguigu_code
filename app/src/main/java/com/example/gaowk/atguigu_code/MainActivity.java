package com.example.gaowk.atguigu_code;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.example.gaowk.atguigu_code.base.BaseFragment;
import com.example.gaowk.atguigu_code.fragment.CommonFrameFragment;
import com.example.gaowk.atguigu_code.fragment.CustomFragment;
import com.example.gaowk.atguigu_code.fragment.OtherFragment;
import com.example.gaowk.atguigu_code.fragment.ThirdPartyFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RadioGroup mRg_main;
    private List<BaseFragment> mBaseFragment;

    /**
     * 选中的Fragment的对应的位置
     */
    private int position;

    /**
     * 上次切换的Fragment
     */
    private Fragment mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化View
        initView();
        //初始化Fragment
        initFragment();
        //设置RadioGroup的监听
        setListener();
    }

    private void setListener() {
        mRg_main.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //设置默认选中常用框架
        mRg_main.check(R.id.rb_common_frame);
    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rb_common_frame://常用框架
                    position = 0;
                    break;
                case R.id.rb_thirdparty://第三方
                    position = 1;
                    break;
                case R.id.rb_custom://自定义
                    position = 2;
                    break;
                case R.id.rb_other://其他
                    position = 3;
                    break;
                default:
                    position = 0;
                    break;
            }

            //根据位置得到对应的Fragment
            BaseFragment to = getFragment();
            //替换
            switchFrament(mContent,to);

        }
    }



    private void switchFrament(Fragment from,Fragment to) {
        if(from != to){
            mContent = to;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //才切换
            //判断有没有被添加
            if(!to.isAdded()){
                //to没有被添加
                //from隐藏
                if(from != null){
                    ft.hide(from);
                }
                //添加to
                if(to != null){
                    ft.add(R.id.fl_content,to).commit();
                }
            }else{
                //to已经被添加
                // from隐藏
                if(from != null){
                    ft.hide(from);
                }
                //显示to
                if(to != null){
                    ft.show(to).commit();
                }
            }
        }

    }

//    private void switchFrament(BaseFragment fragment) {
//        //1.得到FragmentManger
//        FragmentManager fm = getSupportFragmentManager();
//        //2.开启事务
//        FragmentTransaction transaction = fm.beginTransaction();
//        //3.替换
//        transaction.replace(R.id.fl_content, fragment);
//        //4.提交事务
//        transaction.commit();
//    }

    /**
     * 根据位置得到对应的Fragment
     * @return
     */
    private BaseFragment getFragment() {
        BaseFragment fragment = mBaseFragment.get(position);
        return fragment;
    }

    private void initFragment() {
        mBaseFragment = new ArrayList<>();
        mBaseFragment.add(new CommonFrameFragment());//常用框架Fragment
        mBaseFragment.add(new ThirdPartyFragment());//第三方Fragment
        mBaseFragment.add(new CustomFragment());//自定义控件Fragment
        mBaseFragment.add(new OtherFragment());//其他Fragment
    }



    private void initView() {
        setContentView(R.layout.activity_main);
        mRg_main = (RadioGroup) findViewById(R.id.rg_bottom_tag);

    }
}
