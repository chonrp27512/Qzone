package cc.qzone;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cc.qzone.view.QzoneTitleView;
import cc.qzone.view.WebBoTextView;

public class MainActivity extends AppCompatActivity {

    private QzoneTitleView title_view = null;
    private Button btn = null;
    private WebBoTextView text_view = null;

    AlertDialog.Builder builder = null;

    private String text = "#盗墓笔记#新浪微博话题#花千骨##瓶邪#功能测试！！@无家，测试测试@山野村夫，，，，，mmmmm测试，" +
            "@东村野人@荒村故事 http://www.baidu.com";

    private String tag = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(MainActivity.this, ShuoShuoListActivity.class);
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });

        btn.setOnLongClickListener(null);

        title_view = (QzoneTitleView)findViewById(R.id.title_view);
        title_view.setName("靳先生");

        title_view.setOnNameClickListener(new QzoneTitleView.OnNameClickListener() {
            @Override
            public void onClick() {
                Toast.makeText(MainActivity.this, "点击了用户昵称区域", Toast.LENGTH_SHORT).show();
            }
        });

        builder = new AlertDialog.Builder(this);
        builder.setTitle("测试对话框样式");
        builder.setMessage("对话框内容");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        //测试
        text_view = (WebBoTextView)findViewById(R.id.text_view);
        text_view.setTextContent(Html.fromHtml(text));
        text_view.setOnTextViewClickListener(new WebBoTextView.OnTextViewClickListener() {
            @Override
            public void clickTextView(int style, String key, int index) {
                String str = "@";
                switch (style){
                    case 0:
                        str = "话题";
                        break;
                    case 1:
                        str = "@用户";
                        break;
                }
                Toast.makeText(MainActivity.this, str+"---" +index+"--"+key, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
