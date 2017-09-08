package com.fcdream.dress.kenny.bo.api;

import com.fcdream.dress.kenny.bo.Entity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Copyright (c) 2017, 北京视达科科技有限责任公司 All rights reserved.
 * author：shumeng.du
 * date：2017/9/6
 * description：
 */
public class StarResult extends BasePageResult {

    @SerializedName("data")
    public List<StarInfo> starInfoList;

    public static class StarInfo extends Entity {
        public String id;
        @SerializedName("bigimg")
        public String bigImg;
        public String img;
        public String title;
        public int width;
        public int height;

        @Override
        public String toString() {
            return "StarInfo{" +
                    "id='" + id + '\'' +
                    ", bigImg='" + bigImg + '\'' +
                    ", img='" + img + '\'' +
                    ", title='" + title + '\'' +
                    ", width=" + width +
                    ", height=" + height +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "StarResult{" +
                "result='" + result + '\'' +
                ", msg='" + msg + '\'' +
                ", queryInfo=" + queryInfo +
                ", starInfoList=" + starInfoList +
                '}';
    }
}
