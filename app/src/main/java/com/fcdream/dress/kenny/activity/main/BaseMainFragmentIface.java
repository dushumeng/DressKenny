package com.fcdream.dress.kenny.activity.main;

import com.fcdream.dress.kenny.player.XulMediaPlayer;
import com.fcdream.dress.kenny.speech.BaseSpeech;

/**
 * Created by shmdu on 2017/9/2.
 */

public interface BaseMainFragmentIface {


    static final String TYPE_ROBOT = "type_robot";
    static final String TYPE_MAIN_LIST = "type_main_list";
    static final String TYPE_SEARCH = "type_search";

    XulMediaPlayer getMediaPlayer();

    BaseSpeech getSpeech();

    void handleSpeech(String info);

    void dealFragmentBack(BaseMainPageFragment fragment);
}
