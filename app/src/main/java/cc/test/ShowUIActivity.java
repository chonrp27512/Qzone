package cc.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collection;
import java.util.HashMap;

/**
 * User: (1203596603@qq.com)
 * Date: 2015-09-29
 * Time: 09:37
 * Version 1.0
 */

public class ShowUIActivity extends Activity {

    private static HashMap<String, FakeActivity> executors = new HashMap<>();
    private FakeActivity executor;

    private String tag = "ShowUIActivity";

    public static String registerExecutor(FakeActivity fakeActivity){
        String key = String.valueOf(System.currentTimeMillis());
        executors.put(key, fakeActivity);
        return key;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String key = intent.getStringExtra("launch_time");
        this.executor = executors.remove(key);
        if(this.executor==null){
            super.onCreate(savedInstanceState);
            this.finish();
        }
        this.executor.setActivity(this);
        super.onCreate(savedInstanceState);
        this.executor.onCreate();
    }

    public void setContentView(int resLayout){
        View view = LayoutInflater.from(this).inflate(resLayout, null);
        this.setContentView(view);
    }

    public void setContentView(View view){
        if(view!=null){
            super.setContentView(view);
            if(this.executor!=null){
                this.executor.setContentView(view);
            }
        }
    }

    public void setContentView(View view, ViewGroup.LayoutParams layoutParams){
        if(view!=null){
            if(layoutParams!=null){
                super.setContentView(view, layoutParams);
            } else {
                super.setContentView(view);
            }

            if(this.executor!=null){
                super.setContentView(view);
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if(this.executor == null){
            super.onNewIntent(intent);
        } else {
            this.executor.onNewIntent(intent);
        }
    }

    @Override
    protected void onStart() {
        if(this.executor!=null){
            this.executor.onStart();
        }
        super.onStart();
    }

    @Override
    protected void onResume() {
        if(this.executor!=null){
            this.executor.onResume();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if(this.executor!=null){
            this.executor.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        if(this.executor!=null){
            this.executor.onStop();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if(this.executor!=null){
            this.executor.onDestroy();
        }
        super.onDestroy();
    }
}
