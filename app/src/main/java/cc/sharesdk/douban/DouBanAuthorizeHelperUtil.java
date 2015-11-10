package cc.sharesdk.douban;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import cc.sharesdk.AuthorizeHelperUtil;
import cc.sharesdk.Platform;
import cc.sharesdk.kaixin.KaiXin;

/**
 * User: 靳世坤(1203596603@qq.com)
 * Date: 2015-09-24
 * Time: 11:40
 * Version 1.0
 */

public class DouBanAuthorizeHelperUtil extends AuthorizeHelperUtil {

    private Douban douban;

    private String tag = "DouBanAuthorizeHelperUtil";

    public DouBanAuthorizeHelperUtil(Platform platform) {
        super(platform);
        douban = (Douban)platform;
    }

    @Override
    public String getAuthorizeUrl() {
        String authorizeUrl = "https://www.douban.com/service/auth2/auth" +
                "?client_id=" +
                douban.getApiKey() +
                "&response_type=code" +
                "&redirect_uri=" +
                douban.getRedirectUri();
        Log.i(tag, "---"+authorizeUrl);
        return authorizeUrl;
    }

    @Override
    public String getRedirectUri() {
        return douban.getRedirectUri();
    }

    @Override
    public void getToken(String code){
        String url = "https://www.douban.com/service/auth2/token";
        RequestParams params = new RequestParams();
        params.put("client_id", douban.getApiKey());
        params.put("client_secret", douban.getSecret());
        params.put("redirect_uri", douban.getRedirectUri());
        params.put("grant_type", "authorization_code");
        params.put("code", code);

        AsyncHttpClient httpClient = new AsyncHttpClient();
        httpClient.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Log.i(tag, tag+"---"+throwable+"---"+i);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                Log.i(tag, tag+"---70---"+s);
            }
        });
    }

    @Override
    public void userInfo(String uid) {

    }
}
