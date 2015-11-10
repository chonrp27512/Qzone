package cc.sharesdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * User: (1203596603@qq.com)
 * Date: 2015-09-28
 * Time: 09:48
 * Version 1.0
 */

public abstract class Token {

    protected final  Context mContext;

    private SharedPreferences tokenPreferences;

    private String access_token;
    private long expires_in;

    private boolean isAuthor; //是否已经授权

    public Token(Context context, String name){
        mContext = context;

        tokenPreferences = mContext.getSharedPreferences(name + "_token", 0);
        access_token = tokenPreferences.getString("access_token", null);
        expires_in = tokenPreferences.getLong("expires_in", -1);

        if(!TextUtils.isEmpty(access_token)){
            isAuthor = true;
        } else {
            isAuthor = false;
        }
    }

    public abstract void delete();

    public abstract void saveToken(String access_token, long expires_in);

    public boolean isAuthor() {
        return isAuthor;
    }

    public String getAccess_token() {
        return access_token;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public SharedPreferences getTokenPreferences() {
        return tokenPreferences;
    }

    public void setTokenPreferences(SharedPreferences tokenPreferences) {
        this.tokenPreferences = tokenPreferences;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }
}
