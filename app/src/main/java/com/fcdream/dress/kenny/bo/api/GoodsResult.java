package com.fcdream.dress.kenny.bo.api;

import com.fcdream.dress.kenny.bo.Entity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Copyright (c) 2017, 北京视达科科技有限责任公司 All rights reserved.
 * author：shumeng.du
 * date：2017/9/8
 * description：
 */
public class GoodsResult extends BasePageResult {

    @SerializedName("data")
    public List<GoodsInfo> goodsInfoList;

    public class GoodsInfo extends Entity {
        public String id;

        public String img;

        public String name;
    }
}
