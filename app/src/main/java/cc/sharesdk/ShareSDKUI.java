package cc.sharesdk;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: 靳世坤(1203596603@qq.com)
 * Date: 2015-09-24
 * Time: 14:19
 * Version 1.0
 */

public class ShareSDKUI extends Activity {

    private static HashMap<String, FakeActivity> executors = new HashMap<>();
    private FakeActivity executor;

    public static String registerExecutor(FakeActivity fakeActivity){
        String key = String.valueOf(System.currentTimeMillis());
        return registerExecutor(key, fakeActivity);
    }

    public static String registerExecutor(String key, FakeActivity fakeActivity){
        executors.put(key, fakeActivity);
        return key;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String key = intent.getStringExtra("launch_time");
        String name = intent.getStringExtra("executor_name");
        this.executor = (FakeActivity)executors.remove(key);
        if(this.executor == null){
            String keyScheme = intent.getScheme();
            this.executor = (FakeActivity)executors.remove(keyScheme);
            if(this.executor == null){
                super.onCreate(savedInstanceState);
                this.finish();
                return;
            }
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
        if(view != null){
            super.setContentView(view);
            if(this.executor!=null){
                this.executor.setContentView(view);
            }
        }
    }

    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        if(view != null) {
            if(layoutParams == null) {
                super.setContentView(view);
            } else {
                super.setContentView(view, layoutParams);
            }

            if(this.executor != null) {
                this.executor.setContentView(view);
            }

        }
    }

    protected void onNewIntent(Intent var1) {
        if(this.executor == null) {
            super.onNewIntent(var1);
        } else {
            this.executor.onNewIntent(var1);
        }

    }

    @Override
    protected void onStart() {
        if(this.executor != null) {
            this.executor.onStart();
        }

        super.onStart();
    }

    @Override
    protected void onResume() {
        if(this.executor != null) {
            this.executor.onResume();
        }

        super.onResume();
    }

    @Override
    protected void onPause() {
        if(this.executor != null) {
            this.executor.onPause();
        }

        super.onPause();
    }

    @Override
    protected void onStop() {
        if(this.executor != null) {
            this.executor.onStop();
        }

        super.onStop();
    }

    @Override
    protected void onRestart() {
        if(this.executor != null) {
            this.executor.onRestart();
        }

        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        if(this.executor != null) {
            this.executor.onDestroy();
        }
        super.onDestroy();
    }

    public void finish() {
        if(this.executor == null || !this.executor.onFinish()) {
            super.finish();
        }
    }

    public FakeActivity getExecutor() {
        return this.executor;
    }

}
