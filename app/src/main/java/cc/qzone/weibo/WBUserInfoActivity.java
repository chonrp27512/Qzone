package cc.qzone.weibo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import cc.qzone.BaseActivity;
import cc.qzone.R;
import cc.qzone.weibo.model.WBToken;

/**
 * User: (1203596603@qq.com)
 * Date: 2015-09-23
 * Time: 09:33
 * Version 1.0
 */

public class WBUserInfoActivity extends BaseActivity {

    private ListView list_view;

    private WBToken wbToken = null;
    private String token = null;
    private String uid;

    private String url = "https://api.weibo.com/2/users/show.json";

    private String tag = "WBUserInfoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        wbToken = new WBToken(this);  //获取已经持久化的token
        token = wbToken.getAccess_token();

        getToken();

        initViews();
        initData();

    }

    private void getToken(){
        try{
            Intent intent = getIntent();
            uid = intent.getStringExtra("uid");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initViews(){
        list_view = (ListView)findViewById(R.id.list_view);
    }

    private void initData(){
        RequestParams params = new RequestParams();
        params.put("access_token", token);
        params.put("uid", uid);
        AsyncHttpClient httpClient = new AsyncHttpClient();
        httpClient.get(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                Log.i(tag, "---==="+s);
            }
        });

    }

}
