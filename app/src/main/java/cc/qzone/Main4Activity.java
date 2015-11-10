package cc.qzone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cc.qzone.util.http.HttpUtil;
import cc.qzone.util.http.MnCookieStore;
import cc.qzone.util.http.RequestParams;
import cc.qzone.util.http.TextHttpResponseHandler;

/**
 * User: 靳世坤(1203596603@qq.com)
 * Date: 2015-09-14
 * Time: 16:08
 * Version 1.0
 */

public class Main4Activity extends AppCompatActivity {

    private String tag = "Main4Activity";

    private Button btn = null, btn1 = null, btn2 = null;
    private TextView text = null;

    private HttpUtil httpUtil;

    public static String URL = "http://snsapp.xzw.com/index.php?app=public&mod=Xzwpassport&act=doLogin";
    private String url1 = "http://snsapp.xzw.com/index.php?app=public&mod=xzwindex&act=UserInformation";

    private String url2 = "http://snsapp.xzw.com/index.php?app=public&mod=xzwIndex&act=visitorList&p=1";

    private String url3 = "http://snsapp.xzw.com/index.php?app=public&mod=Xzwindex&act=getNewCount";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        MnCookieStore cookieStore = new MnCookieStore(this);
        httpUtil = new HttpUtil();
        httpUtil.setCookieStore(cookieStore);

        btn = (Button)findViewById(R.id.btn);
        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        text = (TextView)findViewById(R.id.text);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
                loadData2();
                loadData3();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData1();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MnCookieStore store = new MnCookieStore(Main4Activity.this);
                store.removeAll();
                Main4Activity.this.finish();
            }
        });
    }

    private void loadData(){
        httpUtil.get(url1, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int code, String info, Throwable throwable) {
                Log.i(tag, "---76----code=" + code);
            }

            @Override
            public void onSuccess(int code, String res) {
                Log.i(tag, "---81----code=" + code);
                text.setText(res);
            }

            @Override
            public void onStart() {
                Log.i(tag, "---87----onStart()");
            }

            @Override
            public void onFinish() {
                Log.i(tag, "---92----onFinish()");
            }
        });
    }

    private void loadData1(){
        RequestParams params = new RequestParams();
        params.addParams("login_email", "1203596603@qq.com");
        params.addParams("login_password", "jin123");
        params.addParams("login_remember", "1");
        httpUtil.post(URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int code, String info, Throwable throwable) {
                Log.i(tag, "---105----code=" + code);
            }

            @Override
            public void onSuccess(int code, String res) {
                Log.i(tag, "---110----code=" + code + "----res=" + res);
                text.setText(res);
            }

            @Override
            public void onStart() {
                Log.i(tag, "---116----onStart()");
            }

            @Override
            public void onFinish() {
                Log.i(tag, "---121----onFinish()");
            }
        });
    }

    private void loadData2(){
        httpUtil.get(url2, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int code, String info, Throwable throwable) {

            }

            @Override
            public void onSuccess(int code, String res) {
                Log.i(tag, "---137----code=" + code + "----res=" + res);
            }

            @Override
            public void onStart() {
                Log.i(tag, "---143----onStart()");
            }

            @Override
            public void onFinish() {
                Log.i(tag, "---148----onFinish()");
            }
        });
    }

    private void loadData3(){
        httpUtil.get(url3, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int code, String info, Throwable throwable) {

            }

            @Override
            public void onSuccess(int code, String res) {
                Log.i(tag, "---164----code=" + code + "----res=" + res);
            }

            @Override
            public void onStart() {
                Log.i(tag, "---169----onStart()");
            }

            @Override
            public void onFinish() {
                Log.i(tag, "---174----onFinish()");
            }
        });
    }

}
