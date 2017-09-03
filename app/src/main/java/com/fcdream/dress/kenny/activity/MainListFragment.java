package com.fcdream.dress.kenny.activity;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.fcdream.dress.kenny.R;
import com.fcdream.dress.kenny.adapter.DressItemAdapter;
import com.fcdream.dress.kenny.bo.DressItem;
import com.fcdream.dress.kenny.bus.MyCallback;
import com.fcdream.dress.kenny.bus.TestBus;
import com.fcdream.dress.kenny.ioc.BindLayout;
import com.fcdream.dress.kenny.ioc.BindView;
import com.fcdream.dress.kenny.utils.SpaceItemDecoration;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by shmdu on 2017/8/31.
 */

@BindLayout(layout = R.layout.fragment_main_page_list)
public class MainListFragment extends BaseMainPageFragment {

    private String currentSearchKey;

    @BindView(id = R.id.dress_content_list_view)
    private RecyclerView dressRecyclerView;

    private DressItemAdapter dressItemAdapter;

    @BindView(id = R.id.shop_content_list_view)
    private RecyclerView shopRecyclerView;

    @BindView(id = R.id.back, clickEvent = "dealHandleBack", click = true)
    private ImageView backImage;

    @Override
    protected void initView(Activity activity, View sourceView) {
        LinearLayoutManager dressLayoutManager = new LinearLayoutManager(activity);
        dressLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        dressRecyclerView.setLayoutManager(dressLayoutManager);
        dressRecyclerView.addItemDecoration(new SpaceItemDecoration((int) getResources().getDimension(R.dimen.list_page_content_dress_content_item_margin)));
        dressItemAdapter = new DressItemAdapter();
        dressRecyclerView.setAdapter(dressItemAdapter);
    }

    @Override
    protected void initData(Activity activity) {

    }

    public void startSearch(String currentSearchKey) {
        this.currentSearchKey = currentSearchKey;
        TestBus.testSearchDress(new MyCallback<List<DressItem>>() {

            @Override
            public void callback(boolean success, List<DressItem> list) {
                dressItemAdapter.getDataList().addAll(list);
                dressItemAdapter.notifyDataSetChanged();

            }
        }, "q", currentSearchKey);
    }

    public void dealHandleBack(View view) {
        if (isFragmentIfaceValid()) {
            ifaceReference.get().dealFragmentBack(this);
        }
    }

    @Override
    public String getFragmentType() {
        return BaseMainFragmentIface.TYPE_MAIN_LIST;
    }
}
