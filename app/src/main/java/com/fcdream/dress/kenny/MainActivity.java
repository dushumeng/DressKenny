package com.fcdream.dress.kenny;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fcdream.dress.kenny.speech.baidu.BaiduSpeech;
import com.fcdream.dress.kenny.speech.BaseSpeech;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BaiduSpeech baiduSpeech = new BaiduSpeech(new BaseSpeech.SpeechListener() {
            @Override
            public void onListenStart() {

            }

            @Override
            public void onListening() {

            }

            @Override
            public void onListenEnd(String info) {

            }
        });

        baiduSpeech.init(this);
        baiduSpeech.start();
    }
}
