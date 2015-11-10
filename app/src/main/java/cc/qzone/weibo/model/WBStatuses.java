package cc.qzone.weibo.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * User: 靳世坤(1203596603@qq.com)
 * Date: 2015-09-22
 * Time: 17:25
 * Version 1.0
 */

public class WBStatuses implements Parcelable {

    private String created_at;
    private String id;
    private String text;
    private String source;
    private boolean favorited;
    private boolean truncated;

    private int reposts_count;
    private int comments_count;
    private int attitudes_count;

    private List<String> pic_urls; //多图连接

    private WBUser wbUser;

    public WBStatuses(String created_at, String id, String text, String source,
                      boolean favorited, boolean truncated, int reposts_count, int comments_count,
                      int attitudes_count, List<String> pic_urls, WBUser wbUser) {
        this.created_at = created_at;
        this.id = id;
        this.text = text;
        this.source = source;
        this.favorited = favorited;
        this.truncated = truncated;
        this.reposts_count = reposts_count;
        this.comments_count = comments_count;
        this.attitudes_count = attitudes_count;
        this.pic_urls = pic_urls;
        this.wbUser = wbUser;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.created_at);
        dest.writeString(this.id);
        dest.writeString(this.text);
        dest.writeString(this.source);
        dest.writeByte(favorited ? (byte) 1 : (byte) 0);
        dest.writeByte(truncated ? (byte) 1 : (byte) 0);
        dest.writeInt(this.reposts_count);
        dest.writeInt(this.comments_count);
        dest.writeInt(this.attitudes_count);
        dest.writeStringList(this.pic_urls);
        dest.writeParcelable(this.wbUser, 0);
    }

    public WBStatuses() {
    }

    protected WBStatuses(Parcel in) {
        this.created_at = in.readString();
        this.id = in.readString();
        this.text = in.readString();
        this.source = in.readString();
        this.favorited = in.readByte() != 0;
        this.truncated = in.readByte() != 0;
        this.reposts_count = in.readInt();
        this.comments_count = in.readInt();
        this.attitudes_count = in.readInt();
        this.pic_urls = in.createStringArrayList();
        this.wbUser = in.readParcelable(WBUser.class.getClassLoader());
    }

    public static final Parcelable.Creator<WBStatuses> CREATOR = new Parcelable.Creator<WBStatuses>() {
        public WBStatuses createFromParcel(Parcel source) {
            return new WBStatuses(source);
        }

        public WBStatuses[] newArray(int size) {
            return new WBStatuses[size];
        }
    };

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public boolean isTruncated() {
        return truncated;
    }

    public void setTruncated(boolean truncated) {
        this.truncated = truncated;
    }

    public int getReposts_count() {
        return reposts_count;
    }

    public void setReposts_count(int reposts_count) {
        this.reposts_count = reposts_count;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getAttitudes_count() {
        return attitudes_count;
    }

    public void setAttitudes_count(int attitudes_count) {
        this.attitudes_count = attitudes_count;
    }

    public List<String> getPic_urls() {
        return pic_urls;
    }

    public void setPic_urls(List<String> pic_urls) {
        this.pic_urls = pic_urls;
    }

    public WBUser getWbUser() {
        return wbUser;
    }

    public void setWbUser(WBUser wbUser) {
        this.wbUser = wbUser;
    }
}
