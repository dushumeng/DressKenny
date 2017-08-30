package com.fcdream.dress.kenny;

import android.os.Bundle;
import android.widget.TextView;

import com.fcdream.dress.kenny.speech.baidu.BaiduSpeech;
import com.fcdream.dress.kenny.speech.BaseSpeech;

public class MainActivity extends BaseActivity implements BaseSpeech.SpeechListener {

    private TextView tipView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tipView = findViewById(R.id.tip);
        tipView.requestFocus();

    }

    @Override
    public void onListenStart() {

    }

    @Override
    public void onListening() {

    }

    @Override
    public void onListenError(String errorInfo) {

    }

    @Override
    public void onListenEnd(String info) {

    }
}
