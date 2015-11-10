package cc.qzone.util.http;

import java.io.IOException;

/**
 * User: 靳世坤(1203596603@qq.com)
 * Date: 2015-09-17
 * Time: 11:05
 * Version 1.0
 */

public class AsyncHttpRequest implements Runnable {

    private final ResponseHandlerInterface responseHandler;
    private final HttpUtil httpUtil;
    private final String url;
    private RequestParams params;
    private int method;

    public AsyncHttpRequest(int method, HttpUtil httpUtil, String url, RequestParams params, ResponseHandlerInterface responseHandler){
        this.method = method;
        this.params = params;
        this.httpUtil = httpUtil;
        this.url = url;
        this.responseHandler = responseHandler;
    }

    @Override
    public void run() {
        responseHandler.sendStartMessage();
        try {
            switch (method){
                case 0:
                    this.httpUtil.doPost(url, params, responseHandler);
                    break;
                case 1:
                    this.httpUtil.doGet(url, responseHandler);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
