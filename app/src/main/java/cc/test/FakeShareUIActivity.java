package cc.test;

import cc.qzone.R;

/**
 * User: 靳世坤(1203596603@qq.com)
 * Date: 2015-09-29
 * Time: 14:17
 * Version 1.0
 */

public class FakeShareUIActivity extends FakeActivity {

    @Override
    public void onCreate() {
        super.onCreate();
        if(getContext() instanceof ShowUIActivity){
            ShowUIActivity showUIActivity = (ShowUIActivity)getContext();
            showUIActivity.setContentView(R.layout.activity_main5);
        }
    }
}
