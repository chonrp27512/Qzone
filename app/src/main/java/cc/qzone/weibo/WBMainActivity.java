package cc.qzone.weibo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cc.qzone.BaseActivity;
import cc.qzone.R;
import cc.qzone.weibo.model.WBToken;
import cc.sharesdk.Platform;
import cc.sharesdk.ShareSDK;
import cc.sharesdk.sina.weibo.SinaWeibo;

/**
 * User: 靳世坤(1203596603@qq.com)
 * Date: 2015-09-22
 * Time: 14:25
 * Version 1.0
 */

public class WBMainActivity extends BaseActivity {

    private Button login_btn, weibo_btn, delete_btn;
    private WebView web_view;
    private String loadUrl = "https://api.weibo.com/oauth2/authorize?client_id=284902573&redirect_uri=http://www.koudairiji.com";

    private WBToken wbToken;

    private SinaWeibo pf;

    private String tag = "WBMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wb_activity_main);

        ShareSDK.initSDK(this);

        wbToken = new WBToken(this);

        initViews();

        pf = (SinaWeibo) ShareSDK.getPlatform("SinaWeibo");
        String redirectUrl =  pf.getRedirectUrl();

        Log.i(tag, "----62----"+redirectUrl);

    }

    private void initViews(){
        web_view = (WebView)findViewById(R.id.web_view);
        login_btn = (Button)findViewById(R.id.login_btn);
        weibo_btn = (Button)findViewById(R.id.weibo_btn);
        delete_btn = (Button)findViewById(R.id.delete_btn);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(wbToken.isAuthor()){
                    Snackbar.make(web_view, "已经授权", Snackbar.LENGTH_SHORT).show();
                } else {
                    web_view.loadUrl(loadUrl);
                }
            }
        });

        weibo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(wbToken.isAuthor()){
                    Intent intent = new Intent(WBMainActivity.this, WBListActivity.class);
                    intent.putExtra("wbToken", wbToken);
                    startActivity(intent);
                } else {
                    Snackbar.make(web_view, "请登录授权", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wbToken.deleteToken();
            }
        });

        WebSettings settings = web_view.getSettings();
        settings.setJavaScriptEnabled(true);
        web_view.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.i(tag, "====" + url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Pattern p = Pattern.compile("code=(.*)");
                Matcher m = p.matcher(url);
                String res = null;
                while (m.find()) {
                    res = m.group();
                    if (res.indexOf("code=") != -1) {
                        res = res.replace("code=", "");
                    }
                    Log.i(tag, "--=--" + res);
                }
                Log.i(tag, "----" + url);
                getToken(res);
            }
        });
    }

    private void getToken(String code){
        String url = "https://api.weibo.com/oauth2/access_token";
        AsyncHttpClient httpClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("client_id", "284902573");
        params.put("client_secret", "125352d859d7c7c83184d2957fe2a34f");
        params.put("grant_type", "authorization_code");
        params.put("redirect_uri", "http://www.koudairiji.com");
        params.put("code", code);


        httpClient.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                Log.i(tag, "----" + s);
                try {
                    JSONObject object = new JSONObject(s);
                    String access_token = object.optString("access_token");
                    long expires_in = object.optLong("expires_in");
                    String uid = object.optString("uid");
                    wbToken.saveToken(access_token, expires_in, uid);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }




}
