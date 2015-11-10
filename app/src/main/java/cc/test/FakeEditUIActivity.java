package cc.test;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cc.qzone.R;

/**
 * User: 靳世坤(1203596603@qq.com)
 * Date: 2015-09-29
 * Time: 10:34
 * Version 1.0
 */

public class FakeEditUIActivity extends FakeActivity {

    private Button btn;

    private String tag = "FakeEditUIActivity";

   @Override
    public void onCreate() {
        super.onCreate();
        if(getContext() instanceof ShowUIActivity){
            ShowUIActivity showUIActivity = (ShowUIActivity)getContext();
            showUIActivity.setContentView(R.layout.activity_main4);

            btn = (Button)findViewById(R.id.btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "代理UI显示", Toast.LENGTH_SHORT).show();
                    test();
                }
            });
        }
    }

    private void test(){
        FakeShareUIActivity fakeShareUIActivity = new FakeShareUIActivity();
        fakeShareUIActivity.show(getContext());
    }

}
