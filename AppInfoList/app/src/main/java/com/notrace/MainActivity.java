package com.notrace;

import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<AppInfo> list;
    private Handler mainHandler;
    private ProgressBar pb;
    private static final int INIT = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }
    private void initView(){
        listView= (ListView) findViewById(R.id.lv_main);
        pb= (ProgressBar) findViewById(R.id.pb_main);
    }
    private void initData(){
        mainHandler=new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what==INIT){
                    pb.setVisibility(View.VISIBLE);
                    listView.setAdapter(new AppInfoAdapter(MainActivity.this,list));
                    pb.setVisibility(View.GONE);

                }
            }
        };

        new Thread() {
            @Override
            public void run() {
                list = getPackgeInfo(MainActivity.this.getPackageManager().getInstalledPackages(0));
                Collections.sort(list);
                mainHandler.sendEmptyMessage(INIT);
            }
        }.start();
    }


    private List<AppInfo> getPackgeInfo(List<PackageInfo> packageInfoList) {
        if (list == null) {
            list = new ArrayList<>();
        } else {
            list.clear();
        }
        for (int i = 0; i < packageInfoList.size(); i++) {
            list.add(new AppInfo(packageInfoList.get(i)));
        }
        return list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainHandler.sendEmptyMessage(INIT);
    }

    @Override
    protected void onStop() {
        mainHandler.removeCallbacksAndMessages(null);
        super.onStop();
    }
}
