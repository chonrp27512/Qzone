package cc.sharesdk.sina.weibo;

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
import cc.sharesdk.PlatformActionListener;

/**
 * User: (1203596603@qq.com)
 * Date: 2015-09-23
 * Time: 14:28
 * Version 1.0
 */

public class SinaWeibo extends Platform {

    public static final String NAME = SinaWeibo.class.getSimpleName();

    private int version = 1;

    private HashMap<String, String> params;

    private String AppKey;
    private String AppSecret;
    private String RedirectUrl;

    private SinaWeiboAuthorizeHelperUtil sinaWeiboAuthorizeHelperUtil;

    private String tag = "SinaWeibo";

    public SinaWeibo(Context context) {
        super(context);
        Log.i(tag, "---&&---"+this.getListener());
        sinaWeiboAuthorizeHelperUtil = new SinaWeiboAuthorizeHelperUtil(this);
    }

    @Override
    public void getUserInfo() {
        sinaWeiboAuthorizeHelperUtil.userInfo(null);
    }

    @Override
    protected void initDevInfo() {

    }

    @Override
    protected void userInfo(String uid) {

    }

    @Override
    public void setPlatformActionListener(PlatformActionListener listener) {
        super.setPlatformActionListener(listener);
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
        String SortId = params.get("SortId");
        return Integer.valueOf(SortId);
    }

    @Override
    public void setParams(HashMap<String, String> params) {
        this.params = params;
        if(params!=null){
            this.AppKey = params.get("AppKey");
            this.AppSecret = params.get("AppSecret");
            this.RedirectUrl = params.get("RedirectUrl");
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
                    sinaWeiboAuthorizeHelperUtil.getToken(res);
                }
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        fakeChildUIActivity.setAuthorizeHelper(sinaWeiboAuthorizeHelperUtil);

    }

    @Override
    protected boolean checkAuthorize(int var1, Object var2) {
        return false;
    }

    public String getAppKey() {
        return AppKey;
    }

    public void setAppKey(String appKey) {
        AppKey = appKey;
    }

    public String getAppSecret() {
        return AppSecret;
    }

    public void setAppSecret(String appSecret) {
        AppSecret = appSecret;
    }

    public String getRedirectUrl() {
        return RedirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        RedirectUrl = redirectUrl;
    }
}
