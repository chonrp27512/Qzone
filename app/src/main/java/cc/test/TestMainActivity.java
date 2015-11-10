package cc.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cc.qzone.R;

/**
 * User: 靳世坤(1203596603@qq.com)
 * Date: 2015-09-29
 * Time: 10:31
 * Version 1.0
 */

public class TestMainActivity extends Activity {

    private Button test_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main);

        test_btn = (Button)findViewById(R.id.test_btn);
        test_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test();
            }
        });

    }


    private void test(){
        FakeMainUIActivity fakeMainUIActivity = new FakeMainUIActivity();
        fakeMainUIActivity.show(this);
    }


}
