package com.fcdream.dress.kenny.activity.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;

import com.fcdream.dress.kenny.R;
import com.fcdream.dress.kenny.activity.GoodsActivity;
import com.fcdream.dress.kenny.adapter.OnItemClickListener;
import com.fcdream.dress.kenny.adapter.StarAdapter;
import com.fcdream.dress.kenny.bo.CommonEntity;
import com.fcdream.dress.kenny.bo.api.StarResult;
import com.fcdream.dress.kenny.helper.MyTime;
import com.fcdream.dress.kenny.ioc.BindLayout;
import com.fcdream.dress.kenny.ioc.BindView;
import com.fcdream.dress.kenny.log.MyLog;
import com.fcdream.dress.kenny.message.XulSubscriber;
import com.fcdream.dress.kenny.recycler.StarRecyclerBus;
import com.fcdream.dress.kenny.speech.BaseSpeechSynthesizer;
import com.fcdream.dress.kenny.speech.SpeechFactory;
import com.fcdream.dress.kenny.speech.SpeechSynthesizerError;
import com.fcdream.dress.kenny.utils.AndroidUtils;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by shmdu on 2017/8/31.
 */

@BindLayout(layout = R.layout.fragment_main_page_list)
public class MainListFragment extends BaseMainPageFragment implements BaseSpeechSynthesizer.SpeechSynthesizerListener
        , OnItemClickListener {

    private static final int AUTO_SCROLL_CHECK_TIME_LEN = 30 * 1000;
    private static final int MSG_AUTO_SCROLL = 111;

//    @BindView(id = R.id.shop_content_list_view)
//    private RecyclerView shopRecyclerView;

    @BindView(id = R.id.back, clickEvent = "dealHandleBack", click = true)
    private ImageView backImage;

    @BindView(id = R.id.goods_title, clickEvent = "goGoodsPage", click = true)
    private Button goodsImage;

    private ImageView robotImage;

    @BindView(id = R.id.speech_mic, clickEvent = "dealMicImageClick", click = true)
    private ImageView micImage;

    @BindView(id = R.id.speech_speak, clickEvent = "dealSpeakImageClick", click = true)
    private ImageView speakImage;

    @BindView(id = R.id.search_edit_text)
    private EditText searchEditText;

    @BindView(id = R.id.search_edit_image, clickEvent = "dealHandleSearch", click = true)
    private ImageView searchImage;

    @BindView(id = R.id.listen_bg, clickEvent = "dealListenBgLayoutClick", click = true)
    private RelativeLayout listenBgLayout;

    @BindView(id = R.id.listen_mic, clickEvent = "dealListenMicImageClick", click = true)
    private ImageView listenMicImage;

    private boolean canSpeak = true;

    private boolean isAutoScroll = false;
    private long lastTouchTime = 0;

    private StarRecyclerBus starRecyclerBus;

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
        listenAnimation = (AnimationDrawable) listenMicImage.getDrawable();

        dealChangeSpeakStatus(STATE_SPEAK_NORMAL);
        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                dealSearch();
                return true;
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
        if (!TextUtils.equals(currentSearchKey, starRecyclerBus.userData)) {
            dealStopSpeak();
            dealStopSpeech();
            starRecyclerBus.userData = currentSearchKey;
            starRecyclerBus.loadData();
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

    private int currentSelectPosition = -1;

    public void autoScroll(int scrollToPosition) {
        if (isHidden()) {
            return;
        }
        if (scrollToPosition == currentSelectPosition) {
            return;
        }
        MyLog.i("dsminfo", "scrollToPosition:" + scrollToPosition + "---" + starRecyclerBus.getDataListSize());
        if (scrollToPosition >= starRecyclerBus.getDataListSize()) {
            isAutoScroll = false;
            return;
        }

        int targetScrollToPosition = scrollToPosition + 1;
        int targetSelectPosition = scrollToPosition;
        if (targetSelectPosition > starRecyclerBus.getDataListSize() / 2) {
            starRecyclerBus.loadNext();
        }
        currentSelectPosition = targetSelectPosition;

        setStarSelectPosition(targetSelectPosition);
        starRecyclerBus.recyclerView.mRecyclerView.smoothScrollToPosition(targetScrollToPosition);
        StarAdapter.ViewHolder viewHolder = getViewHolder(targetSelectPosition);
        if (viewHolder != null) {
            viewHolder.bgImage.setVisibility(View.VISIBLE);
        } else {
            MyLog.i("dsminfo", "current null---" + targetScrollToPosition);
        }
        viewHolder = getViewHolder(targetSelectPosition - 1);
        if (viewHolder != null) {
            viewHolder.bgImage.setVisibility(View.GONE);
        } else {
            MyLog.i("dsminfo", "last null---" + (targetScrollToPosition - 1));
        }
        StarResult.StarInfo data = (StarResult.StarInfo) starRecyclerBus.getData(targetSelectPosition);
        if (canSpeak && data != null) {
            dealStartSpeak(data.title);
        } else {
            scrollNextDelay(3 * 1000);
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
        dealChangeSpeakStatus(STATE_SPEAKING);
    }

    @Override
    public void onSpeechProgressChanged(String s, int i) {

    }

    @Override
    public void onSpeechFinish(String s) {
        dealChangeSpeakStatus(STATE_SPEAK_NORMAL);
        scrollNextDelay(1000);
    }

    @Override
    public void onSpeechError(String s, SpeechSynthesizerError speechError) {
        scrollNextDelay(1000);
    }

    @Override
    public void onItemClick(View view, int position, Object object) {
        if (canSpeak && object != null && object instanceof StarResult.StarInfo) {
            dealStartSpeak(((StarResult.StarInfo) object).title);
        }
    }

    public void goGoodsPage(View view) {
        startActivity(new Intent(getActivity(), GoodsActivity.class));
    }

    public void dealSpeakImageClick(View view) {
        canSpeak = !canSpeak;
        if (!canSpeak && isFragmentIfaceValid()) {
            speechSynthesizer.stop();
        }
        dealChangeSpeakStatus(canSpeak ? STATE_SPEAK_NORMAL : STATE_SPEAK_DISABLE);
    }

    public void dealMicImageClick(View view) {
        dealStartSpeech();
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
    public void onStop() {
        super.onStop();
        starRecyclerBus.onStop();
        dealStopSpeak();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        starRecyclerBus.onDestroy();
    }

    @Override
    public boolean handleTouchEvent(MotionEvent event) {
        stopAutoScroll();
        return super.handleTouchEvent(event);
    }

    @Override
    public String getPageName() {
        return "page_main_star_list";
    }

    @XulSubscriber(tag = CommonEntity.MESSAGE_ONE_SECOND)
    public void dealUpdate(Object obj) {
        if (isHidden() || !isResumed()) {
            return;
        }
        // TODO: 2017/9/6 检查当前是否需要自动滚动
        if (!isAutoScroll && MyTime.currentTimeMillis() - lastTouchTime > AUTO_SCROLL_CHECK_TIME_LEN) {
            startAutoScroll();
        }
    }

    private void startAutoScroll() {
        if (starRecyclerBus == null || starRecyclerBus.getDataListSize() == 0) {
            return;
        }
        isAutoScroll = true;
        int scrollPosition = starRecyclerBus.layoutManager.findFirstCompletelyVisibleItemPosition();
        autoScroll(scrollPosition);
    }

    private void changeListenState(boolean isListening) {
        if (isListening) {
            listenBgLayout.setVisibility(View.VISIBLE);
            listenAnimation.start();
        } else {
            listenBgLayout.setVisibility(View.GONE);
            listenAnimation.stop();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null);
    }

    private StarAdapter.ViewHolder getViewHolder(int position) {
        StarAdapter.ViewHolder viewHolder = (StarAdapter.ViewHolder) starRecyclerBus.recyclerView.mRecyclerView.findViewHolderForAdapterPosition(position);
        return viewHolder;
    }

    private void scrollNextDelay(int delay) {
        if (!isAutoScroll) {
            return;
        }
        Message message = Message.obtain();
        message.what = MSG_AUTO_SCROLL;
        message.obj = currentSelectPosition + 1;
        handler.sendMessageDelayed(message, delay);
    }

    private void setStarSelectPosition(int position) {
        if (starRecyclerBus == null) {
            return;
        }
        ((StarAdapter) starRecyclerBus.adapter).setSelectPosition(position);
    }

    private void stopAutoScroll() {
        currentSelectPosition = -1;
        lastTouchTime = MyTime.currentTimeMillis();
        isAutoScroll = false;
        setStarSelectPosition(-1);
        handler.removeMessages(MSG_AUTO_SCROLL);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void dealListenBgLayoutClick(View view) {
        dealStopSpeech();
        changeListenState(false);
    }

    public void dealListenMicImageClick(View view) {
        dealStopSpeech();
        changeListenState(false);
    }

    protected void dealStopSpeech() {
        changeListenState(false);
        if (isFragmentIfaceValid()) {
            ifaceReference.get().getSpeech().stop();
        }
    }

    protected void dealStartSpeech() {
        dealStopSpeak();
        changeListenState(true);
        if (isFragmentIfaceValid()) {
            ifaceReference.get().getSpeech().start();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            if (speechSynthesizer != null) {
                dealStopSpeak();
                dealStopSpeech();
            }
        } else {
            if (userdata != null && userdata instanceof String) {
                dealSearch((String) userdata);
            }
            starRecyclerBus.onStart();
        }
    }

    public void dealHandleSearch(View view) {
        dealSearch();
    }

    private void dealSearch() {
        String searchText = searchEditText.getText().toString();
        if (TextUtils.isEmpty(searchText)) {
            return;
        }
        AndroidUtils.hideSoftInput(getContext(), searchEditText);
        dealSearch(searchText);
    }
}
