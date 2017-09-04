package com.fcdream.dress.kenny.bus;

import com.alibaba.fastjson.JSON;
import com.fcdream.dress.kenny.App;
import com.fcdream.dress.kenny.bo.DressItem;
import com.fcdream.dress.kenny.log.MyLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by shmdu on 2017/9/2.
 */

public class TestBus {

    public static final void testSearchDress(MyCallback callback, String... params) {
        StringBuffer sb = new StringBuffer("http://47.94.0.142/cgi/dress/star.pl?pn=12");
        if (params != null) {
            for (int i = 0; i < params.length; i += 2) {
                sb.append("&").append(params[i]).append("=").append(params[i + 1]);
            }
        }
        MyLog.i("dsminfo", sb.toString());
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(sb.toString())
                .build();
        //&q=红色大衣&p=2
        //new call
        Call call = okHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray array = jsonObject.getJSONArray("data");
                    String arrayList = array.toString();
                    List<DressItem> dressItems = JSON.parseArray(arrayList, DressItem.class);
                    if (callback != null) {
                        App.postToMainLooper(new Runnable() {
                            @Override
                            public void run() {
                                callback.callback(true, dressItems);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
