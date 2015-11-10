package cc.qzone.weibo.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cc.qzone.weibo.model.WBStatuses;
import cc.qzone.weibo.model.WBUser;

/**
 * User: (1203596603@qq.com)
 * Date: 2015-09-22
 * Time: 17:36
 * Version 1.0
 */

public class WBJsonUtil {

    public static List<WBStatuses> wbStatusesList(String json){
        List<WBStatuses> list = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.optJSONArray("statuses");
            list = new ArrayList<>();
            int count = jsonArray.length();
            for(int i=0; i<count; i++){
                JSONObject object = jsonArray.optJSONObject(i);
                String created_at = object.optString("created_at");
                String id = object.optString("id");
                String text = object.optString("text");
                String source = object.optString("source");
                boolean favorited = object.optBoolean("favorited");
                boolean truncated = object.optBoolean("truncated");
                int reposts_count = object.optInt("reposts_count");
                int comments_count = object.optInt("comments_count");
                int attitudes_count = object.optInt("attitudes_count");

                JSONObject userObject = object.optJSONObject("user");
                String uid = userObject.optString("id");
                String screen_name = userObject.optString("screen_name");
                String name = userObject.optString("name");
                String location = userObject.optString("location");
                String description = userObject.optString("description");
                String url = userObject.optString("url");
                String profile_image_url = userObject.optString("profile_image_url");
                String avatar_large = userObject.optString("avatar_large");
                WBUser wbUser = new WBUser(uid, screen_name, name, location, description, url,
                        profile_image_url, avatar_large);

                JSONArray pics = object.optJSONArray("pic_urls");
                List<String> pic_urls = new ArrayList<>();
                int pCount = pics.length();
                for(int j=0; j<pCount; j++){
                    JSONObject picsObject = pics.optJSONObject(j);
                    String pUrl = picsObject.optString("thumbnail_pic");
                    pic_urls.add(pUrl);
                }

                WBStatuses wbStatuses = new WBStatuses( created_at,  id,  text,  source,
                 favorited,  truncated,  reposts_count,  comments_count,
                 attitudes_count,  pic_urls,  wbUser);
                list.add(wbStatuses);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
