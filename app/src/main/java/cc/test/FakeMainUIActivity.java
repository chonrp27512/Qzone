package cc.test;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import cc.qzone.R;

/**
 * User: (1203596603@qq.com)
 * Date: 2015-09-29
 * Time: 10:34
 * Version 1.0
 */

public class FakeMainUIActivity extends FakeActivity {

    private String tag = "FakeMainUIActivity";

    private Button btn;

   @Override
    public void onCreate() {
        super.onCreate();
        if(getContext() instanceof ShowUIActivity){
            ShowUIActivity showUIActivity = (ShowUIActivity)getContext();
            showUIActivity.setContentView(R.layout.activity_main);

            btn = (Button)findViewById(R.id.btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i(tag, "---测试---");
                    goToItem();
                }
            });
        }
    }

    private void goToItem(){
        FakeEditUIActivity fakeEditUIActivity = new FakeEditUIActivity();
        fakeEditUIActivity.show(getContext());
    }
}
