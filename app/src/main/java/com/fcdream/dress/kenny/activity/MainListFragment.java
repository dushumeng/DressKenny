package com.fcdream.dress.kenny.activity;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;

import com.fcdream.dress.kenny.R;
import com.fcdream.dress.kenny.adapter.OnItemClickListener;
import com.fcdream.dress.kenny.bo.CommonEntity;
import com.fcdream.dress.kenny.bo.StarResult;
import com.fcdream.dress.kenny.helper.MyTime;
import com.fcdream.dress.kenny.ioc.BindLayout;
import com.fcdream.dress.kenny.ioc.BindView;
import com.fcdream.dress.kenny.message.XulSubscriber;
import com.fcdream.dress.kenny.recycler.StarRecyclerBus;
import com.fcdream.dress.kenny.speech.BaseSpeechSynthesizer;
import com.fcdream.dress.kenny.speech.SpeechFactory;
import com.fcdream.dress.kenny.speech.SpeechSynthesizerError;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by shmdu on 2017/8/31.
 */

@BindLayout(layout = R.layout.fragment_main_page_list)
public class MainListFragment extends BaseMainPageFragment implements BaseSpeechSynthesizer.SpeechSynthesizerListener
        , OnItemClickListener {

    private static final int AUTO_SCROLL_CHECK_TIME_LEN = 1 * 60 * 1000;
    private static final int MSG_AUTO_SCROLL = 111;

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

    @BindView(id = R.id.listen_bg)
    private ImageView listenBgImage;

    private boolean canSpeak = true;

    private boolean isAutoScroll = true;
    private long lastTouchTime = 0;

    private String currentSearchKey;

    private StarRecyclerBus starRecyclerBus;

    LinearLayoutManager dressLayoutManager;

    BaseSpeechSynthesizer speechSynthesizer;

    AnimationDrawable speakAnimation;
    AnimationDrawable listenAnimation;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_AUTO_SCROLL) {
                autoScroll((int) msg.obj);
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void initView(Activity activity, View sourceView) {
        speakAnimation = (AnimationDrawable) getActivity().getResources().getDrawable(R.drawable.anim_speak);
        listenAnimation = (AnimationDrawable) listenBgImage.getDrawable();

        dealChangeSpeakStatus(STATE_SPEAK_NORMAL);
        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                dealSearch(searchEditText.getText().toString());
            }
            return false;
        });


        starRecyclerBus = new StarRecyclerBus();
        starRecyclerBus.init(getActivity()
                , (UltimateRecyclerView) sourceView.findViewById(R.id.dress_content_list_view), this);

        speechSynthesizer = SpeechFactory.createSpeechSynthesizer(SpeechFactory.TYPE_BAIDU);
        speechSynthesizer.setSpeechSynthesizerListener(this);
    }

    @Override
    protected void initData(Activity activity) {

    }

    private void dealSearch(String currentSearchKey) {
        if (TextUtils.isEmpty(currentSearchKey)) {
            return;
        }
        currentSearchKey = currentSearchKey.trim();
        searchEditText.setText(currentSearchKey);
        if (!TextUtils.equals(currentSearchKey, starRecyclerBus.currentSearchKey)) {
            starRecyclerBus.currentSearchKey = currentSearchKey;
            starRecyclerBus.loadData(currentSearchKey);
        }
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

    public void autoScroll(int scrollToPosition) {
//        if (scrollToPosition >= starRecyclerBus.recyclerView.getChildCount()) {
//            return;
//        }
//        starRecyclerBus.recyclerView.mRecyclerView.smoothScrollToPosition(scrollToPosition);
//        DressItemAdapter.ViewHolder childViewHolder = (DressItemAdapter.ViewHolder) starRecyclerBus.recyclerView.mRecyclerView.getChildViewHolder(starRecyclerBus.recyclerView.getChildAt(2));
//        childViewHolder.bgImage.setVisibility(View.VISIBLE);
//        if (scrollToPosition - 1 >= 0) {
//            childViewHolder = (DressItemAdapter.ViewHolder) starRecyclerBus.recyclerView.mRecyclerView.getChildViewHolder(starRecyclerBus.recyclerView.getChildAt(scrollToPosition - 1));
//            childViewHolder.bgImage.setVisibility(View.GONE);
//        }
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
        if (canSpeak && object != null && object instanceof StarResult.StarInfo) {
            dealStartSpeak(((StarResult.StarInfo) object).title);
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
            changeListenState(true);
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
    public void onListenError(String errorInfo) {
        super.onListenError(errorInfo);
        changeListenState(false);
    }

    @Override
    public void onListenEnd(String info) {
        changeListenState(false);
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

    @Override
    public void onShow(Object object) {
        if (object != null && object instanceof String) {
            dealSearch((String) object);
        }
    }

    @Override
    public boolean handleTouchEvent(MotionEvent event) {
        lastTouchTime = MyTime.currentTimeMillis();
        isAutoScroll = false;
        handler.removeMessages(MSG_AUTO_SCROLL);
        return super.handleTouchEvent(event);
    }

    @XulSubscriber(tag = CommonEntity.MESSAGE_ONE_SECOND)
    public void dealUpdate(Object obj) {
        // TODO: 2017/9/6 检查当前是否需要自动滚动
        if (!isAutoScroll && MyTime.currentTimeMillis() - lastTouchTime > AUTO_SCROLL_CHECK_TIME_LEN) {
            startAutoScroll();
        }
    }

    private void startAutoScroll() {
        isAutoScroll = true;
        int scrollPosition = dressLayoutManager.findFirstCompletelyVisibleItemPosition();
        autoScroll(scrollPosition);
    }

    private void changeListenState(boolean isListening) {
        if (isListening) {
            listenBgImage.setVisibility(View.VISIBLE);
            listenAnimation.start();
        } else {
            listenBgImage.setVisibility(View.GONE);
            listenAnimation.stop();
        }
    }
}
