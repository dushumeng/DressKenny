package com.fcdream.dress.kenny.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fcdream.dress.kenny.App;
import com.fcdream.dress.kenny.BaseActivity;
import com.fcdream.dress.kenny.R;
import com.fcdream.dress.kenny.bo.CommonEntity;
import com.fcdream.dress.kenny.ioc.BindLayout;
import com.fcdream.dress.kenny.ioc.BindView;
import com.fcdream.dress.kenny.recycler.GoodsInfoRecyclerBus;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.squareup.picasso.Picasso;

/**
 * Copyright (c) 2017, 北京视达科科技有限责任公司 All rights reserved.
 * author：shumeng.du
 * date：2017/9/11
 * description：
 */
@BindLayout(layout = R.layout.activity_goods_info)
public class GoodsInfoActivity extends BaseActivity {

    @BindView(id = R.id.back, clickEvent = "dealBack", click = true)
    private ImageView backImage;

    @BindView(id = R.id.title)
    private TextView titleView;

    @BindView(id = R.id.goods_img)
    private ImageView goodsImageView;

    private String goodsId;
    private String goodsName;
    private String goodsImg;

    GoodsInfoRecyclerBus goodsInfoRecyclerBus;

    @Override
    protected void initParam() {
        Intent intent = getIntent();
        goodsId = intent.getStringExtra(CommonEntity.PARAM_ID);
        goodsImg = intent.getStringExtra(CommonEntity.PARAM_IMG);
        goodsName = intent.getStringExtra(CommonEntity.PARAM_NAME);
    }

    @Override
    protected void initView() {
        if (!TextUtils.isEmpty(goodsName)) {
            titleView.setText(goodsName);
        }
        Picasso.with(App.getAppInstance())
                .load(goodsImg)
                .resize((int) App.getAppInstance().getResources().getDimension(R.dimen.goods_info_image_width), (int) App.getAppInstance().getResources().getDimension(R.dimen.goods_info_image_height))
                .centerCrop()
                .placeholder(R.drawable.default_dress_big)
                .into(goodsImageView);

        goodsInfoRecyclerBus = new GoodsInfoRecyclerBus();
        goodsInfoRecyclerBus.init(this, (UltimateRecyclerView) findViewById(R.id.dress_content_list_view), null);
    }

    @Override
    protected void initData() {
        goodsInfoRecyclerBus.userData = goodsName;
        goodsInfoRecyclerBus.loadData();
    }

    public void dealBack(View view) {
        finish();
    }
}
