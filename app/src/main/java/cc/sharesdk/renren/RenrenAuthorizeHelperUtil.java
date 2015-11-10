package cc.sharesdk.renren;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import cc.sharesdk.AuthorizeHelperUtil;
import cc.sharesdk.Platform;

/**
 * User: (1203596603@qq.com)
 * Date: 2015-09-26
 * Time: 10:25
 * Version 1.0
 */

public class RenrenAuthorizeHelperUtil extends AuthorizeHelperUtil {

    private Renren renren;
    private RRToken rrToken;

    private String tag = "RenrenAuthorizeHelperUtil";

    public RenrenAuthorizeHelperUtil(Platform platform) {
        super(platform);
        renren = (Renren)platform;
        rrToken = new RRToken(renren.getContext());
    }

    @Override
    public void getToken(String code) {
        String url = "https://graph.renren.com/oauth/token";
        RequestParams params = new RequestParams();
        params.put("grant_type", "authorization_code");
        params.put("client_id", renren.getApiKey());
        params.put("client_secret", renren.getSecretKey());
        params.put("redirect_uri", renren.getRedirectUri());
        params.put("code", code);
        AsyncHttpClient httpClient = new AsyncHttpClient();
        httpClient.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Log.i(tag, i+"----"+throwable);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                Log.i(tag, i+"-----"+s);
                try {
                    JSONObject object = new JSONObject(s);
                    String token_type = object.optString("token_type");
                    long expires_in = object.optLong("expires_in");
                    String refresh_token = object.optString("refresh_token");
                    String access_token = object.optString("access_token");

                    JSONObject userObject = object.optJSONObject("user");
                    String uid = userObject.optString("id");

                    rrToken.saveToken(access_token, expires_in);
                    rrToken.saveTokenOtherItem(token_type, refresh_token, uid);

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void userInfo(String uid) {
        Log.i(tag, "---"+rrToken.getAccess_token());
        String url = " https://api.renren.com/v2/user/get";
        RequestParams params = new RequestParams();
        try{
            params.put("access_token", rrToken.getAccess_token());
        } catch (Exception e){
            e.printStackTrace();
        }

        AsyncHttpClient httpClient = new AsyncHttpClient();
        httpClient.get(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                Log.i(tag, "====="+s);
            }
        });

    }

    @Override
    public String getAuthorizeUrl() {
        String url = "https://graph.renren.com/oauth/authorize" +
                "?client_id="+renren.getApiKey() +
                "&redirect_uri="+renren.getRedirectUri() +
                "&response_type=code" +
                "&display=mobile";
        return url;
    }

    @Override
    public String getRedirectUri() {
        return renren.getRedirectUri();
    }
}
