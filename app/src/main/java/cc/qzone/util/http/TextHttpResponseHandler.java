package cc.qzone.util.http;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;

import java.io.UnsupportedEncodingException;

/**
 * User: (1203596603@qq.com)
 * Date: 2015-09-15
 * Time: 22:08
 * Version 1.0
 */

public abstract class TextHttpResponseHandler extends AsyncHttpResponseHandler {

    private String charset = "UTF-8";

    private String tag = "TextHttpResponseHandler";

    public abstract void onFailure(int code, String info, Throwable throwable);

    public abstract void onSuccess(int code, String res);

    public void onSuccess(int statusCode, byte[] responseBytes) {
        this.onSuccess(statusCode, getResponseString(responseBytes, charset));
    }

    public void onFailure(int statusCode, byte[] responseBytes, Throwable throwable) {
        this.onFailure(statusCode, getResponseString(responseBytes, charset), throwable);
    }

    public String getResponseString(byte[] stringBytes, String charset) {
        try {
            String e = stringBytes == null?null:new String(stringBytes, charset);
            return e != null && e.startsWith("\ufeff")?e.substring(1):e;
        } catch (UnsupportedEncodingException var3) {
            AsyncHttpClient.log.e("TextHttpRH", "Encoding response into string failed", var3);
            return null;
        }
    }
}
