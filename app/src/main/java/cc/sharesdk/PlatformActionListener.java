package cc.sharesdk;

import java.util.HashMap;

/**
 * Created by abc on 15/9/24.
 */
public interface PlatformActionListener {

    void onComplete(Platform platform, int code);

    void onError(Platform platform, int code, Throwable var3);

    void onCancel(Platform platform, int code);
}
