package cc.sharesdk.kaixin;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import cc.sharesdk.AuthorizeHelperUtil;
import cc.sharesdk.Platform;

/**
 * User: 靳世坤(1203596603@qq.com)
 * Date: 2015-09-24
 * Time: 11:40
 * Version 1.0
 */

public class KaiXinAuthorizeHelperUtil extends AuthorizeHelperUtil {

    private KaiXin kaiXin;

    private String tag = "KaiXinAuthorizeHelperUtil";

    public KaiXinAuthorizeHelperUtil(Platform platform) {
        super(platform);
        kaiXin = (KaiXin)platform;
    }

    @Override
    public String getAuthorizeUrl() {
        String authorizeUrl = null;
        try {
            authorizeUrl = "http://api.kaixin001.com/oauth2/authorize" +
                    "?client_id=" +
                    kaiXin.getAppKey() +
                    "&response_type=code&client=1" +
                    "&redirect_uri=" +
                    URLEncoder.encode(kaiXin.getRedirectUri(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.i(tag, "---"+authorizeUrl);
        return authorizeUrl;
    }

    @Override
    public void getToken(String code) {

    }

    @Override
    public void userInfo(String uid) {

    }

    @Override
    public String getRedirectUri() {
        return null;
    }

}
