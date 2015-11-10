package cc.sharesdk.douban;

import android.content.Context;
import android.content.SharedPreferences;

import cc.sharesdk.Token;

/**
 * User: 靳世坤(1203596603@qq.com)
 * Date: 2015-09-28
 * Time: 17:24
 * Version 1.0
 */

public class DBToken extends Token {

    private SharedPreferences dbTokenPreferences;

    private String refresh_token;

    private String douban_user_id;

    public DBToken(Context context) {
        super(context, "DBToken");

        dbTokenPreferences = getTokenPreferences();

        refresh_token = dbTokenPreferences.getString("refresh_token", null);
        douban_user_id = dbTokenPreferences.getString("douban_user_id", null);
    }

    @Override
    public void delete() {
        SharedPreferences.Editor ed = dbTokenPreferences.edit();
        ed.remove("access_token");
        ed.remove("expires_in");
        ed.remove("refresh_token");
        ed.remove("douban_user_id");
        ed.commit();
    }

    @Override
    public void saveToken(String access_token, long expires_in) {
        SharedPreferences.Editor ed = dbTokenPreferences.edit();
        ed.putString("access_token", access_token);
        ed.putLong("expires_in", expires_in);
        ed.commit();
    }

    public void saveTokenItem(String refresh_token, String douban_user_id){
        SharedPreferences.Editor ed = dbTokenPreferences.edit();
        ed.putString("refresh_token", refresh_token);
        ed.putString("douban_user_id", douban_user_id);
        ed.commit();
    }

    public String getDouban_user_id() {
        return douban_user_id;
    }

    public void setDouban_user_id(String douban_user_id) {
        this.douban_user_id = douban_user_id;
    }
}
