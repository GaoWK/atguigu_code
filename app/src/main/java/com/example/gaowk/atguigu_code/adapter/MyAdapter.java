package com.example.gaowk.atguigu_code.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gaowk.atguigu_code.R;
import com.example.gaowk.atguigu_code.activity.domain.DataBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by GaoWK on 2019/1/21.
 */

public class MyAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<DataBean.TrailersEntity> mDatas;

    public MyAdapter(Context context, List<DataBean.TrailersEntity> datas){
        this.mContext = context;
        this.mDatas = datas;
    }
    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHoder viewHoder;
        if(convertView ==null){
            convertView = View.inflate(mContext, R.layout.item,null);
            viewHoder = new ViewHoder();
            viewHoder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHoder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHoder.tv_desc = (TextView) convertView.findViewById(R.id.tv_desc);

            convertView.setTag(viewHoder);
        }else{
            viewHoder = (ViewHoder) convertView.getTag();
        }

        //根据position得到列表中对应位置的数据
        DataBean.TrailersEntity trailersEntity = mDatas.get(position);
        viewHoder.tv_name.setText(trailersEntity.getMovieName());
        viewHoder.tv_desc.setText(trailersEntity.getVideoTitle());
        //1.使用OKHttp请求图片
        OkHttpUtils
                .get()//
                .url(trailersEntity.getCoverImg())//
                .tag(this)//
                .build()//
                .connTimeOut(20000)
                .readTimeOut(20000)
                .writeTimeOut(20000)
                .execute(new BitmapCallback()
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        Log.e("TAG", "onError:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Bitmap bitmap, int id)
                    {
                        Log.e("TAG", "onResponse：complete");
                        viewHoder.iv_icon.setImageBitmap(bitmap);
                    }
                });

        return convertView;
    }


    static class ViewHoder{
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_desc;
    }
}
