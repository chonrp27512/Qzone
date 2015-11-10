package cc.qzone.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * User: 靳世坤(1203596603@qq.com)
 * Date: 2015-08-27
 * Time: 09:58
 * Version 1.0
 */

public class User implements Parcelable {

    private String name;
    private String date;
    private String iconUrl;

    public User(String name, String date, String iconUrl) {
        this.name = name;
        this.date = date;
        this.iconUrl = iconUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.date);
        dest.writeString(this.iconUrl);
    }

    protected User(Parcel in) {
        this.name = in.readString();
        this.date = in.readString();
        this.iconUrl = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
