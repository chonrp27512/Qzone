package cc.sharesdk.tencent.qzone;

import android.util.Log;

import cc.sharesdk.AuthorizeHelperUtil;
import cc.sharesdk.Platform;

/**
 * User: 靳世坤(1203596603@qq.com)
 * Date: 2015-09-26
 * Time: 15:58
 * Version 1.0
 */

public class QZoneAuthorizeHelperUtil extends AuthorizeHelperUtil {

    private QZone qZone;

    private String tag = "QZoneAuthorizeHelperUtil";

    public QZoneAuthorizeHelperUtil(Platform platform) {
        super(platform);
        qZone = (QZone)platform;
    }

    @Override
    public void getToken(String code) {

    }

    @Override
    public String getAuthorizeUrl() {
        String url = "https://graph.qq.com/oauth2.0/authorize" +
                "?response_type=code" +
                "&client_id=" + qZone.getAppId() +
                "&redirect_uri=" + this.getRedirectUri() +
                "&state="+getState() +
                "&display=mobile";
        Log.i(tag, "===="+url);
        return url;
    }

    @Override
    public void userInfo(String uid) {

    }

    @Override
    public String getRedirectUri() {
        return qZone.getRedirectUri();
    }

    public String getState(){
        return "ShareQzone";
    }
}
