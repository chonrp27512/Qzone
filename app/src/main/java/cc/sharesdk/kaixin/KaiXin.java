package cc.sharesdk.kaixin;

import android.content.Context;
import android.content.Intent;

import java.util.HashMap;

import cc.sharesdk.Platform;
import cc.sharesdk.ShareSDKUI;

/**
 * User: 靳世坤(1203596603@qq.com)
 * Date: 2015-09-24
 * Time: 14:40
 * Version 1.0
 */

public class KaiXin extends Platform {

    public static final String NAME = KaiXin.class.getSimpleName();
    private int version = 1;

    private HashMap<String, String> params;

    private String AppKey;
    private String AppSecret;
    private String RedirectUri;

    public KaiXin(Context context) {
        super(context);
    }

    @Override
    public void getUserInfo() {

    }

    @Override
    protected void initDevInfo() {
        if(params!=null){
            this.AppKey = params.get("AppKey");
            this.AppSecret = params.get("AppSecret");
            this.RedirectUri = params.get("RedirectUri");
        }
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
    protected void userInfo(String uid) {

    }

    @Override
    public void setParams(HashMap<String, String> params) {
        this.params = params;
        if(params!=null){
            this.AppKey = params.get("AppKey");
            this.AppSecret = params.get("AppSecret");
            this.RedirectUri = params.get("RedirectUri");
        }
    }

    @Override
    public void doAuthorize() {
        Intent intent = new Intent(getContext(), ShareSDKUI.class);
        KaiXinAuthorizeHelperUtil kaiXinAuthorizeHelperUtil = new KaiXinAuthorizeHelperUtil(this);
        intent.putExtra("url", kaiXinAuthorizeHelperUtil.getAuthorizeUrl());
        getContext().startActivity(intent);
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

    public String getRedirectUri() {
        return RedirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        RedirectUri = redirectUri;
    }
}
