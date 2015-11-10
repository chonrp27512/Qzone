package cc.sharesdk.renren;

import android.content.Context;
import android.content.SharedPreferences;

import cc.sharesdk.Token;

/**
 * User: 靳世坤(1203596603@qq.com)
 * Date: 2015-09-28
 * Time: 10:47
 * Version 1.0
 */

public class RRToken extends Token{

    private String token_type;
    private String refresh_token;
    private String uid;

    private SharedPreferences rrTokenPreferences;


    public RRToken(Context context) {
        super(context, "RRToken");
        rrTokenPreferences = getTokenPreferences();

        token_type = rrTokenPreferences.getString("token_type", null);
        refresh_token = rrTokenPreferences.getString("refresh_token", null);
        uid = rrTokenPreferences.getString("uid", null);
    }

    @Override
    public void delete() {
        SharedPreferences.Editor et = rrTokenPreferences.edit();
        et.remove("access_token");
        et.remove("expires_in");
        et.remove("token_type");
        et.remove("refresh_token");
        et.remove("uid");
        et.commit();
    }

    @Override
    public void saveToken(String access_token, long expires_in) {
        SharedPreferences.Editor ed = rrTokenPreferences.edit();
        ed.putString("access_token", access_token);
        ed.putLong("expires_in", expires_in);
        ed.commit();
    }

    public void saveTokenOtherItem(String token_type, String refresh_token, String uid){
        SharedPreferences.Editor ed = rrTokenPreferences.edit();
        ed.putString("token_type", token_type);
        ed.putString("refresh_token", refresh_token);
        ed.putString("uid", uid);
        ed.commit();
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
