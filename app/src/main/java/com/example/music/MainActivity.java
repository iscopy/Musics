package com.example.music;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.music.adapter.MyAdapter;
import com.example.music.model.Song;
import com.example.music.utils.MediaPlayerManager;
import com.example.music.utils.MusicUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private List<Song> list;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setPermissions();
        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        mListView = findViewById(R.id.main_listview);
        list = new ArrayList<>();
        //把扫描到的音乐赋值给list
        list = MusicUtils.getMusicData(this);
        adapter = new MyAdapter(this,list);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song info = list.get(position);
//                因为有可能其他音乐正在被播放，所以要先停止在播放

                MediaPlayerManager.stop();
//                播放音乐
                //MediaPlayerManager.play(MainActivity.this, info.path);
                MediaPlayerManager.play(MainActivity.this, "http://192.168.100.188/wemall/upload/test/music.mp3");
            }
        });
    }


    private void setPermissions() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }else{
            Log.i("Jurisdiction","权限申请ok");
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaPlayerManager.stop();
    }

}
