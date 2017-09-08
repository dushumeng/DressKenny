package com.fcdream.dress.kenny.bo.api;

import com.fcdream.dress.kenny.bo.Entity;
import com.google.gson.annotations.SerializedName;

/**
 * Copyright (c) 2017, 北京视达科科技有限责任公司 All rights reserved.
 * author：shumeng.du
 * date：2017/9/8
 * description：
 */
public abstract class BasePageResult extends Entity {

    public String result;
    public String msg;

    @SerializedName("query")
    public StarResult.QueryInfo queryInfo;

    public static class QueryInfo extends Entity {
        @SerializedName("p")
        public int pageIndex;

        @SerializedName("cnt")
        public int count;

        @SerializedName("pn")
        public int pageNum;

        @Override
        public String toString() {
            return "QueryInfo{" +
                    "pageIndex=" + pageIndex +
                    ", count=" + count +
                    '}';
        }
    }
}
