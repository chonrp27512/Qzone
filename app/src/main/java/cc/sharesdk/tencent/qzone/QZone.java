package cc.sharesdk.tencent.qzone;

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
 * User: (1203596603@qq.com)
 * Date: 2015-09-26
 * Time: 15:10
 * Version 1.0
 */

public class QZone extends Platform {

    private static String NAME = QZone.class.getSimpleName();
    private int version = 1;

    private String AppId;
    private String AppKey;
    private String RedirectUri;

    private HashMap<String, String> params;

    private QZoneAuthorizeHelperUtil qZoneAuthorizeHelperUtil;

    private String tag = NAME;

    public QZone(Context context) {
        super(context);
        qZoneAuthorizeHelperUtil = new QZoneAuthorizeHelperUtil(this);
    }

    @Override
    public void getUserInfo() {

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
        return 0;
    }

    @Override
    public void setParams(HashMap<String, String> params) {
        this.params = params;
        if(params!=null){
            this.AppId = params.get("AppId");
            this.AppKey = params.get("AppKey");
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
                    qZoneAuthorizeHelperUtil.getToken(res);
                }
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        fakeChildUIActivity.setAuthorizeHelper(qZoneAuthorizeHelperUtil);
    }

    @Override
    protected boolean checkAuthorize(int var1, Object var2) {
        return false;
    }

    @Override
    protected void userInfo(String uid) {

    }

    public String getAppId() {
        return AppId;
    }

    public void setAppId(String appId) {
        AppId = appId;
    }

    public String getAppKey() {
        return AppKey;
    }

    public void setAppKey(String appKey) {
        AppKey = appKey;
    }

    public String getRedirectUri() {
        return RedirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        RedirectUri = redirectUri;
    }
}
