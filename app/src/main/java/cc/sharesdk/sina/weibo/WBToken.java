package cc.sharesdk.sina.weibo;

import android.content.Context;
import android.content.SharedPreferences;

import cc.sharesdk.Token;

/**
 * User: (1203596603@qq.com)
 * Date: 2015-09-28
 * Time: 10:01
 * Version 1.0
 */

public class WBToken extends Token{

    private String uid;

    private SharedPreferences wbTokenPreferences;

    public WBToken(Context context) {
        super(context, "WBToken");
        wbTokenPreferences = getTokenPreferences();
        uid = wbTokenPreferences.getString("uid", null);
    }

    @Override
    public void delete() {
        SharedPreferences.Editor et = wbTokenPreferences.edit();
        et.remove("access_token");
        et.remove("expires_in");
        et.remove("uid");
        et.commit();
    }

    @Override
    public void saveToken(String access_token, long expires_in) {
        this.setAccess_token(access_token);
        this.setExpires_in(expires_in);

        SharedPreferences.Editor et = wbTokenPreferences.edit();
        et.putString("access_token", access_token);
        et.putLong("expires_in", expires_in);
        et.commit();

    }

    public void saveTokenOtherItem(String uid) {
        SharedPreferences.Editor et = wbTokenPreferences.edit();
        et.putString("uid", uid);
        et.commit();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
