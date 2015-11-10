package cc.qzone.weibo.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * User: (1203596603@qq.com)
 * Date: 2015-09-22
 * Time: 16:28
 * Version 1.0
 */

public class WBToken implements Parcelable{

    private String access_token;
    private long expires_in;
    private String uid;

    private boolean isAuthor; //是否已经授权

    private SharedPreferences tokenPreferences;

    public WBToken(Context context){
        tokenPreferences = context.getSharedPreferences("weibo_token", 0);
        access_token = tokenPreferences.getString("access_token", null);
        expires_in = tokenPreferences.getLong("expires_in", -1);
        uid = tokenPreferences.getString("uid" ,null);

        if(!TextUtils.isEmpty(access_token)){ //先简单判断一下
            isAuthor = true;
        } else {
            isAuthor = false;
        }
    }

    /**
     * 删除Token
     * */
    public void deleteToken(){
        SharedPreferences.Editor ed = this.tokenPreferences.edit();
        ed.remove("access_token");
        ed.remove("expires_in");
        ed.remove("uid");
        ed.commit();
    }

    public void saveToken(String access_token, long expires_in, String uid){
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.uid = uid;
        SharedPreferences.Editor ed = this.tokenPreferences.edit();
        ed.putString("access_token", access_token);
        ed.putLong("expires_in", expires_in);
        ed.putString("uid", uid);
        ed.commit();
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public boolean isAuthor() {
        return isAuthor;
    }

    public void setIsAuthor(boolean isAuthor) {
        this.isAuthor = isAuthor;
    }

    public SharedPreferences getTokenPreferences() {
        return tokenPreferences;
    }

    public void setTokenPreferences(SharedPreferences tokenPreferences) {
        this.tokenPreferences = tokenPreferences;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.access_token);
        dest.writeLong(this.expires_in);
        dest.writeString(this.uid);
        dest.writeByte(isAuthor ? (byte) 1 : (byte) 0);
    }

    protected WBToken(Parcel in) {
        this.access_token = in.readString();
        this.expires_in = in.readLong();
        this.uid = in.readString();
        this.isAuthor = in.readByte() != 0;
    }

    public static final Creator<WBToken> CREATOR = new Creator<WBToken>() {
        public WBToken createFromParcel(Parcel source) {
            return new WBToken(source);
        }

        public WBToken[] newArray(int size) {
            return new WBToken[size];
        }
    };
}
