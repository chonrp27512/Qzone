package cc.qzone.view;

import android.graphics.Bitmap;

/**
 * Created by abc on 15/8/26.
 */
public interface QzoneTitleViewImp {

    public void setName(String name);  //设置用户昵称

    public void setNameTextSize(float size); //用户昵称字体大小

    public void setNameTextColor(int colorId); //用户昵称字体颜色

    public void setUserIcon(String url);  //用户头像地址
    public void setUserIcon(Bitmap bitmap); //用户头像地址

    public void setUserIcon(int width, int height); //用户的头像的大小

    public void setTime(String date); //发表说说的时间

    public void setTimeTextSize(float size); //发表说说时间字体的大小

    public void setTimeTextColor(int colorId); //发表说说时间字体的颜色

    public void setOnNameClickListener(QzoneTitleView.OnNameClickListener onNameClickListener);

}
