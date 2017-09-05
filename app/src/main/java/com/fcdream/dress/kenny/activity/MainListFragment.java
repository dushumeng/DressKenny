package com.fcdream.dress.kenny.activity;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
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
import com.fcdream.dress.kenny.log.MyLog;
import com.fcdream.dress.kenny.speech.BaseSpeechSynthesizer;
import com.fcdream.dress.kenny.speech.SpeechFactory;
import com.fcdream.dress.kenny.speech.SpeechSynthesizerError;
import com.fcdream.dress.kenny.utils.SpaceItemDecoration;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by shmdu on 2017/8/31.
 */

@BindLayout(layout = R.layout.fragment_main_page_list)
public class MainListFragment extends BaseMainPageFragment implements BaseSpeechSynthesizer.SpeechSynthesizerListener
        , OnItemClickListener {

    private String currentSearchKey;

    @BindView(id = R.id.dress_content_list_view)
    private UltimateRecyclerView dressRecyclerView;

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

    @BindView(id = R.id.search_edit_text)
    private EditText searchEditText;

    private boolean canSpeak = true;

    LinearLayoutManager dressLayoutManager;

    BaseSpeechSynthesizer speechSynthesizer;

    AnimationDrawable speakAnimation;

    @Override
    protected void initView(Activity activity, View sourceView) {
        speakAnimation = (AnimationDrawable) getActivity().getResources().getDrawable(R.drawable.anim_speak);
        dealChangeSpeakStatus(STATE_SPEAK_NORMAL);
        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    dealSearch(searchEditText.getText().toString());
                }
                return false;
            }
        });
        dressLayoutManager = new LinearLayoutManager(activity);
        dressLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        dressRecyclerView.setLayoutManager(dressLayoutManager);
        dressRecyclerView.setHasFixedSize(true);
        dressRecyclerView.setEmptyView(R.layout.empty_view, UltimateRecyclerView.EMPTY_KEEP_HEADER);
//        dressRecyclerView.addItemDecoration(new SpaceItemDecoration((int) getResources().getDimension(R.dimen.list_page_content_dress_content_item_margin)));
//        dressRecyclerView.setRefreshing(true);
        dressRecyclerView.reenableLoadmore();
        dressRecyclerView.setLoadMoreView(getLayoutInflater().inflate(R.layout.custom_bottom_progressbar, null));
//        dressRecyclerView.setDefaultSwipeToRefreshColorScheme(R.color.google_blue,
//                R.color.google_green,
//                R.color.google_red,
//                R.color.google_yellow);
//        dressRecyclerView.setParallaxHeader(getLayoutInflater().inflate(R.layout.custom_bottom_progressbar, null));
        dressRecyclerView.setDefaultOnRefreshListener(() -> {
            MyLog.i("dsminfo", "refresh!!!");
        });
        dressRecyclerView.setOnLoadMoreListener((itemsCount, maxLastVisiblePosition) -> {
            MyLog.i("dsminfo", "load more!!!");
        });
        dressItemAdapter = new DressItemAdapter(getActivity(), this);
        dressRecyclerView.setAdapter(dressItemAdapter);

        speechSynthesizer = SpeechFactory.createSpeechSynthesizer(SpeechFactory.TYPE_BAIDU);
        speechSynthesizer.setSpeechSynthesizerListener(this);


    }

    @Override
    protected void initData(Activity activity) {

    }

    public void dealSearch(String currentSearchKey) {
        if (TextUtils.isEmpty(currentSearchKey)) {
            return;
        }
        searchEditText.setText(currentSearchKey);
        if (TextUtils.equals(currentSearchKey, this.currentSearchKey)) {

        } else {
            this.currentSearchKey = currentSearchKey;
        }
        TestBus.testSearchDress(new MyCallback<List<DressItem>>() {

            @Override
            public void callback(boolean success, List<DressItem> list) {
                if (list == null || list.size() == 0) {
                    return;
                }
                dressItemAdapter.getDataList().clear();
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

    public void autoScroll() {

//        int firstCompletelyVisibleItemPosition = dressLayoutManager.findFirstCompletelyVisibleItemPosition();
//        MyLog.i("dsminfo", firstCompletelyVisibleItemPosition + "-" + dressRecyclerView.getChildCount());
//        int scrollToPosition = firstCompletelyVisibleItemPosition;
//        if (firstCompletelyVisibleItemPosition != 0) {
//            scrollToPosition = firstCompletelyVisibleItemPosition + 1;
//        }
//        if (scrollToPosition >= dressRecyclerView.getChildCount()) {
//            return;
//        }
//        dressRecyclerView.smoothScrollToPosition(scrollToPosition);
//        DressItemAdapter.ViewHolder childViewHolder = (DressItemAdapter.ViewHolder) dressRecyclerView.getChildViewHolder(dressRecyclerView.getChildAt(2));
//        childViewHolder.bgImage.setVisibility(View.VISIBLE);
//        if (scrollToPosition - 1 >= 0) {
//            childViewHolder = (DressItemAdapter.ViewHolder) dressRecyclerView.getChildViewHolder(dressRecyclerView.getChildAt(scrollToPosition - 1));
//            childViewHolder.bgImage.setVisibility(View.GONE);
//        }
//        App.postDelayToMainLooper(new Runnable() {
//            @Override
//            public void run() {
//                autoScroll();
//            }
//        }, 5000);
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
        dealChangeSpeakStatus(STATE_SPEAKING);
    }

    @Override
    public void onSpeechProgressChanged(String s, int i) {

    }

    @Override
    public void onSpeechFinish(String s) {
        dealChangeSpeakStatus(STATE_SPEAK_NORMAL);
    }

    @Override
    public void onError(String s, SpeechSynthesizerError speechError) {

    }

    @Override
    public void onItemClick(View view, int position, Object object) {
        if (canSpeak && object != null && object instanceof DressItem) {
            dealStartSpeak(((DressItem) object).title);
        }
    }

    public void dealSpeakImageClick(View view) {
        canSpeak = !canSpeak;
        if (!canSpeak && isFragmentIfaceValid()) {
            speechSynthesizer.stop();
        }
        dealChangeSpeakStatus(canSpeak ? STATE_SPEAK_NORMAL : STATE_SPEAK_DISABLE);
    }

    public void dealMicImageClick(View view) {
        if (isFragmentIfaceValid()) {
            dealStopSpeak();
            ifaceReference.get().getSpeech().start();
        }
    }

    private static String STATE_SPEAKING = "state_speaking";
    private static String STATE_SPEAK_NORMAL = "state_speak_normal";
    private static String STATE_SPEAK_DISABLE = "state_speak_disable";

    private void dealChangeSpeakStatus(String state) {
        if (TextUtils.equals(state, STATE_SPEAKING)) {
            speakImage.setImageDrawable(speakAnimation);
            speakAnimation.start();
        } else if (TextUtils.equals(state, STATE_SPEAK_NORMAL)) {
            speakAnimation.stop();
            speakImage.setImageResource(R.drawable.speak4);
        } else if (TextUtils.equals(state, STATE_SPEAK_DISABLE)) {
            speakAnimation.stop();
            speakImage.setImageResource(R.drawable.icon_not_speak);
        }
    }

    @Override
    public void onListenEnd(String info) {
        dealSearch(info);
    }

    private void dealStopSpeak() {
        speechSynthesizer.destroy();
        dealChangeSpeakStatus(canSpeak ? STATE_SPEAK_NORMAL : STATE_SPEAK_DISABLE);
    }

    private void dealStartSpeak(String info) {
        speechSynthesizer.init(getActivity());
        speechSynthesizer.speak(info);
    }
}
