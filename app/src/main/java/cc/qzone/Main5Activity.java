package cc.qzone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import cc.sharesdk.Platform;
import cc.sharesdk.PlatformActionListener;
import cc.sharesdk.ShareSDK;
import cc.sharesdk.renren.RRToken;
import cc.sharesdk.sina.weibo.SinaWeiboAuthorizeHelperUtil;
import cc.sharesdk.sina.weibo.WBToken;

/**
 * User: 靳世坤(1203596603@qq.com)
 * Date: 2015-09-24
 * Time: 09:41
 * Version 1.0
 */

public class Main5Activity extends AppCompatActivity {

    private Button weibo_btn, kaixin_btn, douban_btn, renren_btn, qzone_btn;
    private TextView text_view;

    private String tag = "Main5Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        initViews();

        ShareSDK.initSDK(this);
    }

    private void initViews(){
        weibo_btn = (Button)findViewById(R.id.weibo_btn);
        kaixin_btn = (Button)findViewById(R.id.kaixin_btn);
        douban_btn = (Button)findViewById(R.id.douban_btn);
        renren_btn = (Button)findViewById(R.id.renren_btn);
        qzone_btn = (Button)findViewById(R.id.qzone_btn);
        text_view = (TextView)findViewById(R.id.text_view);

        weibo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weibo();
            }
        });

        kaixin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kaixin();
            }
        });

        douban_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                douban();
            }
        });

        renren_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                renren();
            }
        });
        qzone_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qzone();
            }
        });
    }

    private void weibo(){
        WBToken wbToken = new WBToken(this);
        Platform platform = ShareSDK.getPlatform("SinaWeibo");
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int code) {
                Log.i(tag, "---="+code);
            }

            @Override
            public void onError(Platform platform, int code, Throwable var3) {

            }

            @Override
            public void onCancel(Platform platform, int code) {

            }
        });
        if(wbToken.isAuthor()){
            Toast.makeText(this, "已经登录授权", Toast.LENGTH_LONG).show();
            platform.getUserInfo();
        } else {
            platform.doAuthorize();
        }
    }

    private void kaixin(){
        Platform platform = ShareSDK.getPlatform("KaiXin");
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int code) {

            }

            @Override
            public void onError(Platform platform, int code, Throwable var3) {

            }

            @Override
            public void onCancel(Platform platform, int code) {

            }
        });
        platform.doAuthorize();
    }

    private void douban(){
        Platform platform = ShareSDK.getPlatform("Douban");
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int code) {

            }

            @Override
            public void onError(Platform platform, int code, Throwable var3) {

            }

            @Override
            public void onCancel(Platform platform, int code) {

            }
        });
        platform.doAuthorize();

    }

    private void renren(){
        Platform platform = ShareSDK.getPlatform("Renren");
        RRToken rrToken = new RRToken(this);
        if(rrToken.isAuthor()){
            Toast.makeText(this, "已经登录授权", Toast.LENGTH_LONG).show();
            platform.getUserInfo();  //获取登录用户信息
        } else {
            platform.doAuthorize();
        }
    }

    private void qzone(){
        Platform platform = ShareSDK.getPlatform("QZone");

        platform.doAuthorize();
    }

}
