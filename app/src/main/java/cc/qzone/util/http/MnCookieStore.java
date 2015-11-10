package cc.qzone.util.http;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: (1203596603@qq.com)
 * Date: 2015-09-14
 * Time: 20:34
 * Version 1.0
 */

public class MnCookieStore implements CookieStore {

    private String tag = "MnCookieStore";

    private final Map<URI, List<HttpCookie>> map;

    private ConcurrentHashMap<String, HttpCookie> cookies;

    private final SharedPreferences cookiePreferences;

    private String cookieStr;

    public MnCookieStore(Context context){
        map = new HashMap<>();
        cookies = new ConcurrentHashMap<>();
        cookiePreferences = context.getSharedPreferences("cookie_info", Context.MODE_ENABLE_WRITE_AHEAD_LOGGING);
        Log.i(tag, "---43---");
        String storedCookieNames = this.cookiePreferences.getString("names", null);
        if(storedCookieNames!=null){
            String[] cookieNames = TextUtils.split(storedCookieNames, ",");
            String[] cookieTemp = cookieNames;
            int length = cookieNames.length;

            StringBuilder sb = new StringBuilder();
            for(int i=0; i<length; i++){
                String name = cookieTemp[i];
                String value = this.cookiePreferences.getString(name, null);
                Log.i(tag, "---54-name"+name+"----value:"+value);
                this.cookies.put(name, new HttpCookie(name, value));
                sb.append(name+"="+value);
                if((i+1)!=length){
                    sb.append(";");
                }
            }
            cookieStr = sb.toString();
        }
        Log.i(tag, "----59----"+cookieStr);
    }

    public String getCookieStr() {
        if(TextUtils.isEmpty(cookieStr)){
            String storedCookieNames = this.cookiePreferences.getString("names", null);
            if(storedCookieNames!=null){
                String[] cookieNames = TextUtils.split(storedCookieNames, ",");
                String[] cookieTemp = cookieNames;
                int length = cookieNames.length;

                StringBuilder sb = new StringBuilder();
                for(int i=0; i<length; ++i){
                    String name = cookieTemp[i];
                    String value = this.cookiePreferences.getString(name, null);
                    Log.i(tag, "---78-name"+name+"----value:"+value);
                    this.cookies.put(name, new HttpCookie(name, value));
                    sb.append(name+"="+value);
                    if((i+1)!=length){
                        sb.append(";");
                    }
                }
                cookieStr = sb.toString();
            }
        }
        Log.i(tag, "-----88-----"+cookieStr);
        return cookieStr;
    }

    @Override
    public void add(URI uri, HttpCookie cookie) {
        List<HttpCookie> cookies = map.get(uri);
        if(cookies == null){
            cookies = new ArrayList<>();
            map.put(uri, cookies);
        }
        cookies.add(cookie);

        String domain = cookie.getDomain();
        String name = cookie.getName();
        this.cookies.put(name, cookie);

        SharedPreferences.Editor prefsWriter = this.cookiePreferences.edit();
        prefsWriter.putString("names", TextUtils.join(",", this.cookies.keySet()));
        prefsWriter.putString("domain", domain);
        prefsWriter.putString(name, cookie.getValue());
        prefsWriter.commit();

        Log.i(tag, "---111---uri="+uri+"----cookie="+cookie);
    }

    @Override
    public List<HttpCookie> get(URI uri) {
        List<HttpCookie> cookies = map.get(uri);
        if(cookies == null){
            cookies = new ArrayList<>();
            map.put(uri, cookies);
        }
        Log.i(tag, "---121---uri="+uri);
        return cookies;
    }

    @Override
    public List<HttpCookie> getCookies() {
        Collection<List<HttpCookie>> values = map.values();
        List<HttpCookie> result = new ArrayList<>();
        for(List<HttpCookie> value:values){
            result.addAll(value);
        }
        Log.i(tag, "---132---getCookies()");
        return result;
    }

    @Override
    public List<URI> getURIs() {
        Set<URI> keys = map.keySet();
        Log.i(tag, "---139---getURIs()");
        return new ArrayList<URI>(keys);
    }

    @Override
    public boolean remove(URI uri, HttpCookie cookie) {
        List<HttpCookie> cookies = map.get(uri);
        Log.i(tag, "---146---remove");
        if(cookies == null){
            return false;
        }
        return cookies.remove(cookie);
    }

    @Override
    public boolean removeAll() {
        Log.i(tag, "---155---removeAll");
        SharedPreferences.Editor prefsWriter = this.cookiePreferences.edit();
        Iterator var2 = this.cookies.keySet().iterator();

        while(var2.hasNext()) {
            String name = (String)var2.next();
            prefsWriter.remove(name);
        }
        prefsWriter.remove("names");
        prefsWriter.remove("domain");
        prefsWriter.commit();
        this.cookies.clear();
        this.map.clear();
        cookieStr = null;
        return true;
    }
}
