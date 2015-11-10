package cc.sharesdk;

import android.content.Context;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * User: (1203596603@qq.com)
 * Date: 2015-09-23
 * Time: 11:36
 * Version 1.0
 */

public class ShareSDK {

    private static XMLConfig mXmlConfig;

    private String tag = "ShareSDK";

    public ShareSDK(){

    }

    public static void initSDK(Context context){
        mXmlConfig = new XMLConfig(context);
    }

    public static Platform getPlatform(String platFromName){
        return mXmlConfig.getPlatform(platFromName);
    }

    public static void registerPlatform(Class<? extends Platform> aClass){
        mXmlConfig.addPlatfrom(aClass);
    }


}
