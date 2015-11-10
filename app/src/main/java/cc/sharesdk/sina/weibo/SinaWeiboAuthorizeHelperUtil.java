package cc.sharesdk.sina.weibo;

import android.text.TextUtils;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import cc.sharesdk.AuthorizeHelperUtil;
import cc.sharesdk.Platform;
import cc.sharesdk.PlatformActionListener;

/**
 * User: 靳世坤(1203596603@qq.com)
 * Date: 2015-09-24
 * Time: 11:40
 * Version 1.0
 */

public class SinaWeiboAuthorizeHelperUtil extends AuthorizeHelperUtil {

    private SinaWeibo sinaWeibo;
    private WBToken wbToken = null;

    private String tag = "SinaWeiboAuthorizeHelperUtil";

    private PlatformActionListener platformActionListener;

    public SinaWeiboAuthorizeHelperUtil(Platform platform) {
        super(platform);
        sinaWeibo = (SinaWeibo)platform;
        wbToken = new WBToken(sinaWeibo.getContext());
        platformActionListener = sinaWeibo.getListener();
        Log.i(tag, tag+"----38----"+platformActionListener);
    }

    @Override
    public String getAuthorizeUrl() {
        String authorizeUrl = "https://api.weibo.com/oauth2/authorize" +
                "?client_id=" +
                sinaWeibo.getAppKey() +
                "&redirect_uri=" +
                sinaWeibo.getRedirectUrl();
        Log.i(tag, "---" + authorizeUrl);
        return authorizeUrl;
    }

    @Override
    public String getRedirectUri() {
        return sinaWeibo.getRedirectUrl();
    }

    @Override
    public void getToken(String code) {
        String url = "https://api.weibo.com/oauth2/access_token";
        AsyncHttpClient httpClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("client_id", sinaWeibo.getAppKey());
        params.put("client_secret", sinaWeibo.getAppSecret());
        params.put("grant_type", "authorization_code");
        params.put("redirect_uri", sinaWeibo.getRedirectUrl());
        params.put("code", code);

        httpClient.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Log.i(tag, tag+"----"+throwable+"---"+i);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                Log.i(tag, "----"+s);
                try {
                    JSONObject object = new JSONObject(s);
                    String access_token = object.optString("access_token");
                    long expires_in = object.optLong("expires_in");
                    String uid = object.optString("uid");
                    wbToken.saveToken(access_token, expires_in);
                    wbToken.saveTokenOtherItem(uid);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void userInfo(String uid) {
        String url = "https://api.weibo.com/2/users/show.json";
        Log.i(tag, tag+"-----94-----"+platformActionListener);
        RequestParams params = new RequestParams();
        params.put("access_token", wbToken.getAccess_token());
        params.put("uid", TextUtils.isEmpty(uid) ? wbToken.getUid() : uid);

        AsyncHttpClient httpClient = new AsyncHttpClient();
        httpClient.get(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Log.i(tag, "---"+throwable);
                if(platformActionListener!=null){
                    platformActionListener.onError(null, i, throwable);
                }
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                Log.i(tag, "-----"+s);
                if(platformActionListener!=null){
                    platformActionListener.onComplete(null, i);
                }
            }

            @Override
            public void onCancel() {
                if(platformActionListener!=null){
                    platformActionListener.onCancel(null, -100);
                }
            }
        });
    }


}
