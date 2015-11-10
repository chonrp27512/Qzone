package cc.qzone.util.http;

import java.util.HashMap;
import java.util.Map;

/**
 * User: 靳世坤(1203596603@qq.com)
 * Date: 2015-09-16
 * Time: 22:15
 * Version 1.0
 */

public class RequestParams {

    private Map<String, String> params = new HashMap<>();

    public RequestParams(){

    }

    /**
     * 添加参数：字符串参数
     * */
    public void addParams(String name, String value){
        params.put(name, value);
    }

    /**
     * 清空参数
     * */
    public void clearAllParams(){
        params.clear();
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
