package com.example.gaowk.atguigu_code.activity;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gaowk.atguigu_code.R;
import com.example.gaowk.atguigu_code.activity.domain.DataBean;
import com.example.gaowk.atguigu_code.adapter.MyAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by GaoWK on 2019/1/21.
 */

public class OKHttpListActivity extends Activity {

    private static final String TAG = OKHttpListActivity.class.getSimpleName();
    private ListView listview;
    private ProgressBar mProgressBar;
    private TextView tv_nodata;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttplist);
        initView();
        getDataFromNet();
    }

    private void initView() {
        listview = findViewById(R.id.listview);
        mProgressBar = findViewById(R.id.mProgressBar);
        tv_nodata = findViewById(R.id.tv_nodata);
    }

    public void getDataFromNet() {
        String url = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";
        OkHttpUtils
                .get()
                .url(url)
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }

    public class MyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {
            setTitle("loading...");
        }

        @Override
        public void onAfter(int id) {
            setTitle("Sample-okHttp");
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
            tv_nodata.setVisibility(View.VISIBLE);
        }

        @Override
        public void onResponse(String response, int id) {
            processData(response);
            Log.e(TAG, "onResponse：complete");
            tv_nodata.setVisibility(View.GONE);
            switch (id) {
                case 100:
                    Toast.makeText(OKHttpListActivity.this, "http", Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                    Toast.makeText(OKHttpListActivity.this, "https", Toast.LENGTH_SHORT).show();
                    break;
                case 108:
                    if (response != null) {
                        //缓存数据
                        //    CacheUtils.putString(OKHttpActivity.this, url, response);
                        //解析和显示数据

                    }
                    break;
            }


        }

        @Override
        public void inProgress(float progress, long total, int id) {
            Log.e(TAG, "inProgress:" + progress);
//            mProgressBar.setProgress((int) (100 * progress));
        }
    }


    private DataBean parsedJson(String response) {
        DataBean dataBean = new DataBean();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.optJSONArray("trailers");
            if (jsonArray != null && jsonArray.length() > 0) {
                List<DataBean.TrailersEntity> trailers = new ArrayList<>();
                dataBean.setTrailers(trailers);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObjectItem = (JSONObject) jsonArray.get(i);

                    if (jsonObjectItem != null) {

                        DataBean.TrailersEntity mediaItem = new DataBean.TrailersEntity();

                        String movieName = jsonObjectItem.optString("movieName");//name
                        mediaItem.setMovieName(movieName);

                        String videoTitle = jsonObjectItem.optString("videoTitle");//desc
                        mediaItem.setVideoTitle(videoTitle);

                        String imageUrl = jsonObjectItem.optString("coverImg");//imageUrl
                        mediaItem.setCoverImg(imageUrl);

                        String hightUrl = jsonObjectItem.optString("hightUrl");//data
                        mediaItem.setHightUrl(hightUrl);

                        //把数据添加到集合
                        trailers.add(mediaItem);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataBean;
    }

    /**
     * json数据解析和设置适配器
     *
     * @param saveJson
     */
    private void processData(String saveJson) {
        DataBean dataBean = parsedJson(saveJson);
        List<DataBean.TrailersEntity> datas = dataBean.getTrailers();
        MyAdapter myAdapter = new MyAdapter(OKHttpListActivity.this, datas);
        listview.setAdapter(myAdapter);
    }


}
