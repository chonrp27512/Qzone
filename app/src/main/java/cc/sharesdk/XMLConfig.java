package cc.sharesdk;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * User: (1203596603@qq.com)
 * Date: 2015-09-23
 * Time: 15:23
 * Version 1.0
 */

public class XMLConfig {

    private Context mContext;

    private HashMap<String, HashMap<String, String>> platfromHashMapStr;

    private ArrayList<Platform> platformArrayList;

    private HashMap<Integer, Platform> platformHashMap;

    private String tag = "XMLConfig";

    public XMLConfig (Context context){
        mContext = context;

        platfromHashMapStr = new HashMap<>();
        platformArrayList = getPlatformArrayList(context);
        Log.i(tag, "----"+platformArrayList);
        platformHashMap = new HashMap<>();

        analyXML();
    }

    public void addPlatfrom(Class<? extends Platform> aClass){
        try {
            Constructor constructor = aClass.getConstructor(new Class[]{Context.class});
            Platform platform = (Platform)constructor.newInstance(new Object[]{this.mContext});
            this.platformHashMap.put(Integer.valueOf(aClass.hashCode()), platform);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Platform getPlatform(String name){
        if(TextUtils.isEmpty(name)){
            return null;
        } else {
            Platform[] platforms = this.platforms();
            if(platforms==null){
                return null;
            } else {
                Platform[] platforms1 = platforms;
                int count = platforms.length;
                for(int i=0; i<count; i++){
                    Platform platform = platforms1[i];
                    String name1 = platform.getName();
                    Log.i(tag, "---79---"+name1);
                    HashMap<String, String> hashMap = platfromHashMapStr.get(name1);
                    if(name.equals(name1)){
                        platform.setParams(hashMap);
                        Log.i(tag, "---83---" + platform);
                        return platform;
                    }
                }
            }
        }

        return null;
    }

    public Platform[] platforms(){
        ArrayList<Platform> list = platformArrayList;
        if(list==null || list.size()<=0){
            return null;
        } else {
            Platform[] platforms = new Platform[list.size()];
            for(int i=0; i<platforms.length; i++){
                platforms[i] = list.get(i);
            }
            return platforms;
        }
    }

    private void analyXML(){
        InputStream inputStream = null;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser pullParser = factory.newPullParser();

            inputStream = mContext.getAssets().open("ShareSDK.xml");

            pullParser.setInput(inputStream, "UTF-8");

            for(int i=pullParser.getEventType(); i!=1; i=pullParser.next()){
                if(i==2){
                    String name = pullParser.getName();
                    HashMap<String, String> attrNameValue = new HashMap();
                    Log.i(tag, "---43---" + name);
                    int attrCount = pullParser.getAttributeCount();
                    for(int j=0; j<attrCount; ++j){
                        String attrName = pullParser.getAttributeName(j);
                        String attrValue = pullParser.getAttributeValue(j).trim();
                        Log.i(tag, "---49---" + attrName + ":" + attrValue);
                        attrNameValue.put(attrName, attrValue);
                    }
                    platfromHashMapStr.put(name, attrNameValue);
                }
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private ArrayList<Platform> getPlatformArrayList(Context context) {
        String[] var2 = new String[]{"cc.sharesdk.sina.weibo.SinaWeibo",
                "cc.sharesdk.kaixin.KaiXin", "cc.sharesdk.douban.Douban", "cc.sharesdk.renren.Renren",
        "cc.sharesdk.tencent.qzone.QZone"};
        ArrayList var3 = new ArrayList();
        String[] var4 = var2;
        int var5 = var2.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            String var7 = var4[var6];

            try {
                Class var8 = Class.forName(var7);
                Constructor var9 = var8.getConstructor(new Class[]{Context.class});
                var9.setAccessible(true);
                Object var10 = var9.newInstance(new Object[]{context});
                var3.add((Platform) var10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return var3;

    }
}
