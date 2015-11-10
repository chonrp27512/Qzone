package cc.qzone.util.http;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * User: (1203596603@qq.com)
 * Date: 2015-09-15
 * Time: 10:17
 * Version 1.0
 */

public class HttpUtil {

    private String tag = "HttpUtil";

    private String charset = "UTF-8";

    private int timeoutMillis = 10000;

    private int byteSize = 4096;

    private boolean cookieBool = false; //是否需要保存cookie

    private MnCookieStore cookieStore;

    private ExecutorService threadPool;

    public HttpUtil() {
        this.threadPool = this.getDefaultThreadPool();

    }

    public void post(final String url, final RequestParams params, final ResponseHandlerInterface responseHandler) {
        AsyncHttpRequest request = new AsyncHttpRequest(0, this, url, params, responseHandler);
        this.threadPool.submit(request);
    }

    public void doPost(String urlName, RequestParams params, ResponseHandlerInterface responseHandler) throws IOException {
        URL url = new URL(urlName);
        StringBuffer parameterBuffer = new StringBuffer();
        if(params.getParams()!=null){
            Iterator<String> it = params.getParams().keySet().iterator();
            String key = null;
            String value = null;
            while (it.hasNext()) {
                key = it.next();
                if(params.getParams().get(key) != null){
                    value = params.getParams().get(key);
                } else {
                    value = "";
                }
                parameterBuffer.append(key).append("=").append(encode(value));
                if(it.hasNext()){
                    parameterBuffer.append("&");
                }
            }
        }

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true); //设置容许输出
        conn.setUseCaches(false); //设置不使用缓存
        conn.setConnectTimeout(timeoutMillis);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept-Charset", charset);
        if(cookieBool){
            conn.setRequestProperty("Cookie", cookieStore.getCookieStr());
        }
        conn.setRequestProperty("Content-Length", String.valueOf(parameterBuffer.length()));

        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        InputStream inputStream = null;

        try{
            outputStream = conn.getOutputStream();
            outputStreamWriter = new OutputStreamWriter(outputStream);
            outputStreamWriter.write(parameterBuffer.toString());
            outputStreamWriter.flush();

            int code = conn.getResponseCode();
            if(code >= 300){
                responseHandler.sendFailureMessage(code, null, null);
            }
            if(code == HttpURLConnection.HTTP_OK){
                if(cookieBool){ //持久化cookie
                    CookieManager manager = new CookieManager(cookieStore, null);
                    manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
                    CookieHandler.setDefault(manager);

                    manager.put(new URI(urlName), conn.getHeaderFields());

                }
                inputStream = conn.getInputStream();

                if(responseHandler instanceof TextHttpResponseHandler){
                    ((TextHttpResponseHandler) responseHandler).sendSuccessMessage(code, inputStreamToByte(inputStream));
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (outputStreamWriter != null) {
                outputStreamWriter.close();
            }

            if (outputStream != null) {
                outputStream.close();
            }

            if (inputStream != null) {
                inputStream.close();
            }
            responseHandler.sendFinishMessage();
        }

    }

    public void get(String url, ResponseHandlerInterface responseHandler){
        AsyncHttpRequest request = new AsyncHttpRequest(1, this, url, null, responseHandler);
        this.threadPool.submit(request);
    }

    /**
     * Get方法
     * */
    public void doGet(String urlName, ResponseHandlerInterface responseHandler) throws IOException{
        URL url = new URL(urlName);

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setConnectTimeout(timeoutMillis);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept-Charset", charset);
        if(cookieBool){
            conn.setRequestProperty("Cookie", cookieStore.getCookieStr());
        }

        InputStream inputStream = null;

        try{
            int code = conn.getResponseCode();
            if(code >= 300){
                responseHandler.sendFailureMessage(code, null, null);
            }
            if(code == HttpURLConnection.HTTP_OK){
                inputStream = conn.getInputStream();
                if(responseHandler instanceof TextHttpResponseHandler){
                    ((TextHttpResponseHandler) responseHandler).sendSuccessMessage(code, inputStreamToByte(inputStream));
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            responseHandler.sendFinishMessage();
        }

    }

    protected ExecutorService getDefaultThreadPool() {
        return Executors.newCachedThreadPool();
    }

    public void setCookieStore(MnCookieStore cookieStore) {
        cookieBool = true;
        this.cookieStore = cookieStore;
    }

    /**
     * 对包含中文字符的字符串进行转码
     * */
    private String encode(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, charset);
    }

    private byte[] inputStreamToByte(InputStream is) throws Exception{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] bs = new byte[1024];
        int len = -1;
        while ((len = is.read(bs)) != -1) {
            bos.write(bs, 0, len);
        }
        byte[] b = bos.toByteArray();
        bos.close();

        return b;
    }


}
