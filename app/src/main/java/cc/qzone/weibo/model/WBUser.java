package cc.qzone.weibo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * User: (1203596603@qq.com)
 * Date: 2015-09-22
 * Time: 17:21
 * Version 1.0
 */

public class WBUser implements Parcelable {

    private String id;
    private String screen_name;
    private String name;
    private String location;
    private String description;
    private String url;
    private String profile_image_url;


    private String avatar_large;

    public WBUser(String id, String screen_name, String name, String location, String description,
                  String url, String profile_image_url, String avatar_large) {
        this.id = id;
        this.screen_name = screen_name;
        this.name = name;
        this.location = location;
        this.description = description;
        this.url = url;
        this.profile_image_url = profile_image_url;
        this.avatar_large = avatar_large;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public void setProfile_image_url(String profile_image_url) {
        this.profile_image_url = profile_image_url;
    }

    public String getAvatar_large() {
        return avatar_large;
    }

    public void setAvatar_large(String avatar_large) {
        this.avatar_large = avatar_large;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.screen_name);
        dest.writeString(this.name);
        dest.writeString(this.location);
        dest.writeString(this.description);
        dest.writeString(this.url);
        dest.writeString(this.profile_image_url);
        dest.writeString(this.avatar_large);
    }

    public WBUser() {
    }

    protected WBUser(Parcel in) {
        this.id = in.readString();
        this.screen_name = in.readString();
        this.name = in.readString();
        this.location = in.readString();
        this.description = in.readString();
        this.url = in.readString();
        this.profile_image_url = in.readString();
        this.avatar_large = in.readString();
    }

    public static final Parcelable.Creator<WBUser> CREATOR = new Parcelable.Creator<WBUser>() {
        public WBUser createFromParcel(Parcel source) {
            return new WBUser(source);
        }

        public WBUser[] newArray(int size) {
            return new WBUser[size];
        }
    };
}
