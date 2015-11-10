package cc.qzone.weibo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.util.List;

import cc.qzone.BaseActivity;
import cc.qzone.R;
import cc.qzone.weibo.adapter.WBWeiBoAdapter;
import cc.qzone.weibo.model.WBStatuses;
import cc.qzone.weibo.model.WBToken;
import cc.qzone.weibo.util.WBJsonUtil;

/**
 * User: (1203596603@qq.com)
 * Date: 2015-09-22
 * Time: 16:59
 * Version 1.0
 */

public class WBListActivity extends BaseActivity {

    private ListView list_view = null;

    private WBWeiBoAdapter wbListAdapter;
    private List<WBStatuses> list;

    private WBToken wbToken = null;

    private int page = 1;
    private String access_token;
    private String url = "https://api.weibo.com/2/statuses/home_timeline.json";

    private String tag = "WBListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wb_activity_list);
        getWBToken();
        initViews();
        initData();
    }

    private void getWBToken(){
        try{
            Intent intent = getIntent();
            wbToken = intent.getParcelableExtra("wbToken");
            access_token = wbToken.getAccess_token();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initViews(){
        list_view = (ListView)findViewById(R.id.list_view);
    }

    private void initData(){
        AsyncHttpClient httpClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("access_token", access_token);
        params.put("count", "50");
        params.put("trim_user", "0"); //只返回用户ID
        params.put("page", page);
        httpClient.get(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                list =  WBJsonUtil.wbStatusesList(s);
                wbListAdapter = new WBWeiBoAdapter(WBListActivity.this, list);
                list_view.setAdapter(wbListAdapter);
            }
        });

    }

}
