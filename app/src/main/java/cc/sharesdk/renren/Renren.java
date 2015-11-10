package cc.sharesdk.renren;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cc.sharesdk.FakeChildUIActivity;
import cc.sharesdk.Platform;

/**
 * User: 靳世坤(1203596603@qq.com)
 * Date: 2015-09-26
 * Time: 09:48
 * Version 1.0
 */

public class Renren extends Platform {

    private HashMap<String, String> params;

    private String ApiKey;
    private String SecretKey;
    private String RedirectUri;

    private static String NAME = Renren.class.getSimpleName();
    private int version = 1;

    private RenrenAuthorizeHelperUtil renrenAuthorizeHelperUtil;

    private String tag = "Renren";

    public Renren(Context context) {
        super(context);
        renrenAuthorizeHelperUtil = new RenrenAuthorizeHelperUtil(this);
    }

    @Override
    public void getUserInfo() {
        renrenAuthorizeHelperUtil.userInfo(null);
    }

    @Override
    protected void initDevInfo() {

    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    protected int getPlatformId() {
        String SortId = this.params.get("SortId");
        return Integer.valueOf(SortId);
    }

    @Override
    public void setParams(HashMap<String, String> params) {
        this.params = params;
        if(params!=null){
            this.ApiKey = params.get("ApiKey");
            this.SecretKey = params.get("SecretKey");
            this.RedirectUri = params.get("RedirectUri");
        }
    }

    @Override
    public void doAuthorize() {
        FakeChildUIActivity fakeChildUIActivity = new FakeChildUIActivity(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if(url.indexOf("code=")!=-1){
                    view.stopLoading();
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
                    renrenAuthorizeHelperUtil.getToken(res);
                }
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        fakeChildUIActivity.setAuthorizeHelper(renrenAuthorizeHelperUtil);
    }

    @Override
    protected boolean checkAuthorize(int var1, Object var2) {
        return false;
    }

    @Override
    protected void userInfo(String uid) {

    }

    public String getApiKey() {
        return ApiKey;
    }

    public void setApiKey(String apiKey) {
        ApiKey = apiKey;
    }

    public String getSecretKey() {
        return SecretKey;
    }

    public void setSecretKey(String secretKey) {
        SecretKey = secretKey;
    }

    public String getRedirectUri() {
        return RedirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        RedirectUri = redirectUri;
    }
}
