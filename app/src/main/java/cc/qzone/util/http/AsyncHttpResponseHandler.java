package cc.qzone.util.http;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.net.URI;

/**
 * User: 靳世坤(1203596603@qq.com)
 * Date: 2015-09-15
 * Time: 18:00
 * Version 1.0
 */

public abstract class AsyncHttpResponseHandler implements ResponseHandlerInterface{

    private String tag = "AsyncHttpResponseHandler";

    private Handler handler;

    public AsyncHttpResponseHandler(){
        if(this.handler == null) {
            this.handler = new AsyncHttpResponseHandler.ResponderHandler(this);
        }
    }

    @Override
    public void sendStartMessage() {
        this.sendMessage(this.obtainMessage(2, (Object)null));
    }

    public abstract void onSuccess(int var1, byte[] var3);

    public abstract void onFailure(int var1, byte[] var3, Throwable var4);

    public void onStart() {

    }

    public void onFinish() {

    }

    @Override
    public void sendFinishMessage() {
        this.sendMessage(this.obtainMessage(3, (Object)null));
    }

    @Override
    public void sendProgressMessage(long var1, long var2) {

    }

    public final void sendSuccessMessage(int statusCode, byte[] responseBytes) {
        this.sendMessage(this.obtainMessage(0, new Object[]{Integer.valueOf(statusCode), responseBytes}));
    }

    public final void sendFailureMessage(int statusCode, byte[] responseBody, Throwable throwable) {
        this.sendMessage(this.obtainMessage(1, new Object[]{Integer.valueOf(statusCode), responseBody, throwable}));
    }

    @Override
    public void sendCancelMessage() { //取消

    }

    @Override
    public void sendRetryMessage(int var1) { //重试

    }

    protected void handleMessage(Message message) {
        try {
            Object[] response;
            switch (message.what){
                case 0: //成功
                    response = (Object[])((Object[])message.obj);
                    this.onSuccess((Integer) response[0], (byte[]) response[1]);
                    break;
                case 1: //失败
                    response = (Object[])((Object[])message.obj);
                    this.onFailure((Integer) response[0], (byte[]) response[1], (Throwable)response[2]);
                    break;
                case 2: //开始
                    this.onStart();
                    break;
                case 3: //完成
                    this.onFinish();
                    break;

            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    protected Message obtainMessage(int responseMessageId, Object responseMessageData) {
        return Message.obtain(this.handler, responseMessageId, responseMessageData);
    }

    protected void sendMessage(Message msg) {
        if(this.handler != null) {
            if(!Thread.currentThread().isInterrupted()) {
                this.handler.sendMessage(msg);
            }
        } else {
            this.handleMessage(msg);
        }

    }

    private static class ResponderHandler extends Handler {
        private final AsyncHttpResponseHandler mResponder;

        ResponderHandler(AsyncHttpResponseHandler mResponder) {
            this.mResponder = mResponder;
        }

        public void handleMessage(Message msg) {
            this.mResponder.handleMessage(msg);
        }
    }
}
