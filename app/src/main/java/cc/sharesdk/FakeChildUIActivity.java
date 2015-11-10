package cc.sharesdk;

import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

/**
 * User: 靳世坤(1203596603@qq.com)
 * Date: 2015-09-25
 * Time: 16:35
 * Version 1.0
 */

public class FakeChildUIActivity extends FakeChildActivity {

    private WebView webView;
    private WebViewClient webViewClient;

    private String tag = "FakeChildUIActivity";

    public FakeChildUIActivity(WebViewClient webViewClient){
        this.webViewClient = webViewClient;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(getContext() instanceof ShareSDKUI){
            ShareSDKUI activity = (ShareSDKUI)getContext();
            webView = new WebView(activity);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            webView.setLayoutParams(lp);

            activity.setContentView(webView);

            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);
            webView.loadUrl(getAuthorizeHelper().getAuthorizeUrl());

            webView.setWebViewClient(webViewClient);
        }
    }
}
