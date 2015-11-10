package cc.sharesdk;

import android.content.Context;
import android.util.Log;

import java.io.Serializable;
import java.util.HashMap;

/**
 * User: 靳世坤(1203596603@qq.com)
 * Date: 2015-09-23
 * Time: 11:44
 * Version 1.0
 */

public abstract class Platform{

    protected final Context context;

    protected PlatformActionListener listener;

    public Platform(Context context){
        this.context = context;
    }

    public abstract void getUserInfo();

    protected abstract void initDevInfo();

    public abstract String getName();

    public abstract int getVersion();

    protected abstract int getPlatformId();

    public abstract void setParams(HashMap<String, String> params);

    public abstract void doAuthorize();

    protected abstract boolean checkAuthorize(int var1, Object var2);

    protected abstract void userInfo(String uid);

    public Context getContext() {
        return this.context;
    }

    public void setPlatformActionListener(PlatformActionListener listener) {
        Log.i("Platform", "----49----"+this.listener);
        this.listener = listener;
    }

    public PlatformActionListener getListener() {
        Log.i("Platform", "----54----"+this.listener);
        return this.listener;
    }
}
