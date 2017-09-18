package com.fcdream.dress.kenny.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.fcdream.dress.kenny.BaseActivity;
import com.fcdream.dress.kenny.R;
import com.fcdream.dress.kenny.adapter.OnItemClickListener;
import com.fcdream.dress.kenny.bo.CommonEntity;
import com.fcdream.dress.kenny.bo.api.GoodsResult;
import com.fcdream.dress.kenny.ioc.BindLayout;
import com.fcdream.dress.kenny.ioc.BindView;
import com.fcdream.dress.kenny.recycler.GoodsRecyclerBus;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

/**
 * Created by shmdu on 2017/9/10.
 */
@BindLayout(layout = R.layout.activity_goods)
public class GoodsActivity extends BaseActivity {

    @BindView(id = R.id.back, clickEvent = "dealBack", click = true)
    private ImageView backImage;

    private GoodsRecyclerBus goodsRecyclerBus;

    @Override
    protected void initParam() {

    }

    @Override
    protected void initView() {
        goodsRecyclerBus = new GoodsRecyclerBus();
        goodsRecyclerBus.init(this, (UltimateRecyclerView) findViewById(R.id.content_list_view), new OnItemClickListener<GoodsResult.GoodsInfo>() {

            @Override
            public void onItemClick(View view, int position, GoodsResult.GoodsInfo info) {
                openGoodsInfoPage(info);
            }
        });

    }

    private void openGoodsInfoPage(GoodsResult.GoodsInfo info) {
        Intent intent = new Intent(this, GoodsInfoActivity.class);
        intent.putExtra(CommonEntity.PARAM_ID, info.id);
        intent.putExtra(CommonEntity.PARAM_NAME, info.name);
        intent.putExtra(CommonEntity.PARAM_IMG, info.img);
        startActivity(intent);
    }

    @Override
    protected void initData() {
        goodsRecyclerBus.userData = "1";
        goodsRecyclerBus.loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        goodsRecyclerBus.onDestroy();
    }

    public void dealBack(View view) {
        finish();
    }
}
