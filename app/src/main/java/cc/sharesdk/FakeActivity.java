package cc.sharesdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import java.util.HashMap;

/**
 * User: 靳世坤(1203596603@qq.com)
 * Date: 2015-09-25
 * Time: 15:25
 * Version 1.0
 */

public class FakeActivity {

    private static Class<? extends ShareSDKUI> shell;
    protected Activity activity;

    private FakeActivity resultReceiver;
    private HashMap<String, Object> result;

    private View contentView;

    public FakeActivity(){

    }

    public static void setShell(Class<? extends ShareSDKUI> she){
        shell = she;
    }

    public final void setContentView(View view){
        this.contentView = view;
    }

    public View getContentView(){
        return contentView;
    }

    public void setActivity(Activity activity){
        this.activity = activity;
    }

    public void onCreate() {
    }

    protected void onNewIntent(Intent intent) {

    }

    public void onStart() {

    }

    public void onPause() {

    }

    public void onResume() {

    }

    public void onStop() {

    }

    public void onRestart() {

    }

    public boolean onFinish() {
        return false;
    }

    public void onDestroy() {

    }

    public final void finish() {
        if(this.activity != null) {
            this.activity.finish();
        }
    }

    public Context getContext(){
        return this.activity;
    }

    public void show(Context context, Intent intent){
        showForResult(context, intent, null);
    }

    public void showForResult(Context context, Intent intent, FakeActivity fakeActivity){
        this.resultReceiver = fakeActivity;

        Intent newIntent = new Intent(context, shell==null?ShareSDKUI.class:shell);
        String key = ShareSDKUI.registerExecutor(this);
        newIntent.putExtra("launch_time", key);
        newIntent.putExtra("executor_name", this.getClass().getName());
        if(intent != null) {
            newIntent.putExtras(intent);
        }
        //暂定
        context.startActivity(newIntent);

    }

}
