package cc.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

/**
 * User: (1203596603@qq.com)
 * Date: 2015-09-29
 * Time: 09:35
 * Version 1.0
 */

public class FakeActivity {

    private Class<? extends ShowUIActivity> showUIActivity;
    private FakeActivity resultReceiver;

    private View contentView;

    private Activity activity;

    private String tag = "FakeActivity";

    public FakeActivity(){

    }

    public View getContentView() {
        return contentView;
    }

    public void setContentView(View view) {
        this.contentView = view;
    }

    public void setActivity(Activity activity){
        this.activity = activity;
    }

    public Context getContext(){
        return this.activity;
    }

    public <T extends View> View findViewById(int resId){
        return this.activity==null ? null : this.activity.findViewById(resId);
    }

    protected void onNewIntent(Intent intent){
        Log.i(tag, "onNewIntent()"+intent.getStringExtra("launch_time"));
    }

    public void onCreate(){
        Log.i(tag, "onCreate()");
    }

    public void onStart(){
        Log.i(tag, "onStart()");
    }

    public void onResume(){
        Log.i(tag, "onResume()");
    }

    public void onPause(){
        Log.i(tag, "onPause()");
    }

    public void onStop(){
        Log.i(tag, "onStop()");
    }

    public void onDestroy(){
        Log.i(tag, "onDestroy()");
    }

    public void show(Context context){
        showForResult(context, null, null);
    }

    public void showForResult(Context context, Intent intent, FakeActivity fakeActivity){
        this.resultReceiver = fakeActivity;

        Intent newIntent = new Intent(context, showUIActivity==null ? ShowUIActivity.class : showUIActivity);
        String key = ShowUIActivity.registerExecutor(this);
        newIntent.putExtra("launch_time", key);
        context.startActivity(newIntent);

    }

}
