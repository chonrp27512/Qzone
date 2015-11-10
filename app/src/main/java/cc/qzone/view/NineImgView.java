package cc.qzone.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

import cc.qzone.R;
import cc.qzone.util.ImageConfigBuilder;

/**
 * User: (1203596603@qq.com)
 * Date: 2015-08-27
 * Time: 21:52
 * Version 1.0
 */

public class NineImgView extends View implements View.OnTouchListener{

    private int textHeight = 5;

    private Paint mPaint = null;

    private int margin = 5; //间隔：默认5

    private int itemWH = 180; //每个item的宽高：默认180

    private int phoneW = 720; //手机分辨率宽度：默认720

    private int phoneH = 1080; //手机分辨率高度：默认1080

    private Rect[] picRects = new Rect[9]; //测试

    private Bitmap testBitmap = null; //测试

    private OnImageItemClickListener onImageItemClickListener = null;
    private int imgIndex = -1;

    private String tag = "NineImgView";

    public NineImgView(Context context) {
        this(context, null);
    }

    public NineImgView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NineImgView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnTouchListener(this); //设置点击

        init();
    }

    private void init(){

        testBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.ic_launcher);

        getPhoneWH();

        itemWH = (phoneW-margin*5-phoneW/4)/3;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(getContext().getResources().getColor(R.color.huise));

        int ii = 0;
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                Log.i(tag, "--"+ii);
                picRects[ii] = new Rect(itemWH * i + 5, itemWH * j + 5, itemWH * (i + 1), itemWH * (j + 1));
                ii++;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for(int i=0; i<picRects.length; i++){
            canvas.drawRect(picRects[i], mPaint);
            canvas.drawBitmap(testBitmap, picRects[i].left, picRects[i].top, mPaint);
        }

        super.onDraw(canvas);
    }

    /**
     * 获取手机分辨率
     * */
    private void getPhoneWH(){
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(metric);
        phoneW = metric.widthPixels;     // 屏幕宽度（像素）
        phoneH = metric.heightPixels;   // 屏幕高度（像素）
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX(); //相对于父控件的坐标
        float y = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //第一行
                if(x<itemWH && y<itemWH+textHeight && y>textHeight){
                    imgIndex = 0;
                } else if((x>itemWH && x<itemWH*2) && (y<itemWH+textHeight && y>textHeight)){
                    imgIndex = 1;
                } else if((x>itemWH*2 && x<itemWH*3) && (y<itemWH+textHeight && y>textHeight)){
                    imgIndex = 2;
                }
                //第二行
                else if(x<itemWH && (y<itemWH*2+textHeight && y>itemWH+textHeight)){
                    imgIndex = 3;
                } else if((x>itemWH && x<itemWH*2) && (y<itemWH*2+textHeight && y>itemWH+textHeight)){
                    imgIndex = 4;
                } else if((x>itemWH*2 && x<itemWH*3) && (y<itemWH*2+textHeight && y>itemWH+textHeight)){
                    imgIndex = 5;
                }
                //第三行
                else if(x<itemWH && (y<itemWH*3+textHeight && y>itemWH*2+textHeight)){
                    imgIndex = 6;
                } else if((x>itemWH && x<itemWH*2) && (y<itemWH*3+textHeight && y>itemWH*2+textHeight)){
                    imgIndex = 7;
                } else if((x>itemWH*2 && x<itemWH*3) && (y<itemWH*3+textHeight && y>itemWH*2+textHeight)){
                    imgIndex = 8;
                }
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                if(imgIndex>-1 && onImageItemClickListener!=null){
                    onImageItemClickListener.onItemClick(imgIndex);
                    imgIndex = -1; //回复默认值
                }
                break;
        }
        return true;
    }

    public void setBitmap(Bitmap bitmap) {
        this.testBitmap = bitmap;
        invalidate();
    }

    public void setUrls(List<String> urls) {
        ImageSize imageSize = new ImageSize(itemWH-5, itemWH-5);
        Log.i(tag, "---190---"+urls.get(2));
        ImageLoader.getInstance().loadImage(urls.get(2), imageSize, ImageConfigBuilder.USER_HEAD_HD_OPTIONS, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap source) {
                int size = Math.min(source.getWidth(), source.getHeight());
                int x = (source.getWidth() - size) / 2;
                int y = (source.getHeight() - size) / 2;
                Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
                Log.i(tag, "-----203-----"+result);
                setBitmap(result);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
    }

    public void setOnImageItemClickListener(OnImageItemClickListener onImageItemClickListener) {
        this.onImageItemClickListener = onImageItemClickListener;
    }

    public interface OnImageItemClickListener{
        void onItemClick(int index);
    }

    /**
     * 获取字符串的宽度
     * */
    private int getStringWidth(String str, Paint paint){
        return (int)paint.measureText(str);
    }

    /**
     * 获取字符串的高度
     * */
    private int getStringHeight(Paint paint){
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int)Math.ceil(fm.descent - fm.top);
    }

}
