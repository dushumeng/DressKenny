package com.fcdream.dress.kenny.activity;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.fcdream.dress.kenny.App;
import com.fcdream.dress.kenny.R;
import com.fcdream.dress.kenny.adapter.DressItemAdapter;
import com.fcdream.dress.kenny.adapter.OnItemClickListener;
import com.fcdream.dress.kenny.bo.DressItem;
import com.fcdream.dress.kenny.bus.MyCallback;
import com.fcdream.dress.kenny.bus.TestBus;
import com.fcdream.dress.kenny.ioc.BindLayout;
import com.fcdream.dress.kenny.ioc.BindView;
import com.fcdream.dress.kenny.speech.BaseSpeechSynthesizer;
import com.fcdream.dress.kenny.speech.SpeechFactory;
import com.fcdream.dress.kenny.speech.SpeechSynthesizerError;
import com.fcdream.dress.kenny.utils.SpaceItemDecoration;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by shmdu on 2017/8/31.
 */

@BindLayout(layout = R.layout.fragment_main_page_list)
public class MainListFragment extends BaseMainPageFragment implements BaseSpeechSynthesizer.SpeechSynthesizerListener
        , OnItemClickListener {

    private String currentSearchKey;

    @BindView(id = R.id.dress_content_list_view)
    private RecyclerView dressRecyclerView;

    private DressItemAdapter dressItemAdapter;

    @BindView(id = R.id.shop_content_list_view)
    private RecyclerView shopRecyclerView;

    @BindView(id = R.id.back, clickEvent = "dealHandleBack", click = true)
    private ImageView backImage;

    private ImageView robotImage;

    @BindView(id = R.id.speech_mic, clickEvent = "dealMicImageClick", click = true)
    private ImageView micImage;

    @BindView(id = R.id.speech_speak, clickEvent = "dealSpeakImageClick", click = true)
    private ImageView speakImage;

    private boolean canSpeak = true;

    LinearLayoutManager dressLayoutManager;

    BaseSpeechSynthesizer speechSynthesizer;

    @Override
    protected void initView(Activity activity, View sourceView) {
        dressLayoutManager = new LinearLayoutManager(activity);
        dressLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        dressRecyclerView.setLayoutManager(dressLayoutManager);
        dressRecyclerView.setHasFixedSize(true);
//        dressRecyclerView.addItemDecoration(new SpaceItemDecoration((int) getResources().getDimension(R.dimen.list_page_content_dress_content_item_margin)));
        dressItemAdapter = new DressItemAdapter(getActivity(), this);
        dressRecyclerView.setAdapter(dressItemAdapter);
        speechSynthesizer = SpeechFactory.createSpeechSynthesizer(SpeechFactory.TYPE_BAIDU);
    }

    @Override
    protected void initData(Activity activity) {

    }

    public void startSearch(String currentSearchKey) {
        if (TextUtils.equals(currentSearchKey, this.currentSearchKey)) {

        } else {
            this.currentSearchKey = currentSearchKey;
        }
        TestBus.testSearchDress(new MyCallback<List<DressItem>>() {

            @Override
            public void callback(boolean success, List<DressItem> list) {
                dressItemAdapter.getDataList().addAll(list);
                dressItemAdapter.notifyDataSetChanged();

//                App.postDelayToMainLooper(new Runnable() {
//                    @Override
//                    public void run() {
//                        //autoScroll();
//                        speechSynthesizer.init(getActivity());
//                        speechSynthesizer.speak(list.get(0).title);
//                    }
//                }, 2000);

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

    private int currentIndex = 0;

    public void autoScroll() {
        Log.i("dsminfo", dressLayoutManager.findFirstVisibleItemPosition() + "-" + dressLayoutManager.findFirstCompletelyVisibleItemPosition());
        dressRecyclerView.smoothScrollToPosition(10);
        Log.i("dsminfo", dressLayoutManager.findFirstVisibleItemPosition() + "-" + dressLayoutManager.findFirstCompletelyVisibleItemPosition());
//        int firstItem = dressLayoutManager.findFirstVisibleItemPosition();
//        int scrollTo = firstItem + 1;
//        if (scrollTo >= dressItemAdapter.getItemCount()) {
//            return;
//        }
//        moveToPosition(dressLayoutManager, dressRecyclerView, scrollTo);
//
        DressItemAdapter.ViewHolder childViewHolder = (DressItemAdapter.ViewHolder) dressRecyclerView.getChildViewHolder(dressRecyclerView.getChildAt(2));
        childViewHolder.bgImage.setVisibility(View.VISIBLE);
//        if (scrollTo - 1 >= 0) {
//            childViewHolder = (DressItemAdapter.ViewHolder) dressRecyclerView.getChildViewHolder(dressRecyclerView.getChildAt(scrollTo - 1));
//            childViewHolder.bgImage.setVisibility(View.GONE);
//        }
//
        App.postDelayToMainLooper(new Runnable() {
            @Override
            public void run() {
                autoScroll();
            }
        }, 5000);
    }

    public static void moveToPosition(LinearLayoutManager manager, RecyclerView mRecyclerView, int n) {
        int firstItem = manager.findFirstVisibleItemPosition();
        int lastItem = manager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            mRecyclerView.smoothScrollToPosition(n);
        } else if (n <= lastItem) {
            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
            mRecyclerView.scrollBy(0, top);
        } else {
            mRecyclerView.scrollToPosition(n);
        }
    }

    @Override
    public void onSynthesizeStart(String s) {

    }

    @Override
    public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {

    }

    @Override
    public void onSynthesizeFinish(String s) {

    }

    @Override
    public void onSpeechStart(String s) {

    }

    @Override
    public void onSpeechProgressChanged(String s, int i) {

    }

    @Override
    public void onSpeechFinish(String s) {

    }

    @Override
    public void onError(String s, SpeechSynthesizerError speechError) {

    }

    @Override
    public void onItemClick(View view, int position, Object object) {
//        if (object != null && object instanceof DressItem) {
//            speechSynthesizer.init(getActivity());
//            speechSynthesizer.speak(((DressItem) object).title);
//        }
    }

    public void dealSpeakImageClick(View view) {
        canSpeak = !canSpeak;
        if (!canSpeak && isFragmentIfaceValid()) {
            speechSynthesizer.stop();
        }
        if (canSpeak) {
            speakImage.setImageResource(R.drawable.speak4);
        } else {
            speakImage.setImageResource(R.drawable.icon_not_speak);
        }
    }
}
