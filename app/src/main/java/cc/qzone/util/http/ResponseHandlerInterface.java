package cc.qzone.util.http;

import java.io.IOException;
import java.net.URI;

/**
 * Created by abc on 15/9/15.
 */
public interface ResponseHandlerInterface {

    void sendStartMessage();

    void sendFinishMessage();

    void sendProgressMessage(long var1, long var2);

    void sendCancelMessage();

    void sendSuccessMessage(int var1, byte[] var2);

    void sendFailureMessage(int var1, byte[] var2, Throwable var3);

    void sendRetryMessage(int var1);

}
