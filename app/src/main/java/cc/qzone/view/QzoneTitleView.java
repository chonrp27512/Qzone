package cc.qzone.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import cc.qzone.R;

/**
 * User: 靳世坤(1203596603@qq.com)
 * Date: 2015-08-26
 * Time: 19:56
 * Version 1.0
 */

public class QzoneTitleView extends View implements QzoneTitleViewImp, View.OnTouchListener{

    private String name = "qzone"; //缺省显示

    private String date = "1970年1月1日";

    private Bitmap iconBitmap = null; //用户头像
    private int iconWidth = 150; //用户头像的宽高

    private Paint namePaint = null; //用户昵称画笔

    private Rect nameRect = null; //用于绘制昵称的背景
    private Paint nameRectPaint = null;
    private int nameTextBackground = Color.WHITE;

    private int nameTextColor = Color.BLACK;

    private float nameTextSize = 21;

    private OnNameClickListener onNameClickListener;

    private boolean isClickName = false; //是否点击用户昵称：默认没有

    private Paint timePaint = null; //时间画笔

    private int timeTextColor = Color.BLACK;

    private float timeTextSize = 18;

    private float moveXY = 1; //xy方向移动的距离

    private String tag = "QzoneTitleView";

    public QzoneTitleView(Context context) {
        this(context, null);
    }

    public QzoneTitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QzoneTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.QzoneTitleView);

        nameTextBackground = ta.getColor(R.styleable.QzoneTitleView_nameTextBackground, Color.WHITE);
        nameTextColor = ta.getColor(R.styleable.QzoneTitleView_nameTextColor, Color.BLACK);
        nameTextSize = ta.getDimensionPixelSize(R.styleable.QzoneTitleView_nameTextSize, 21);
        Log.i(tag, "nameTextColor="+nameTextColor+"---nameTextSize="+nameTextSize);

        timeTextColor = ta.getColor(R.styleable.QzoneTitleView_nameTextColor, Color.BLACK);
        timeTextSize = ta.getDimensionPixelSize(R.styleable.QzoneTitleView_timeTextSize, 18);
        Log.i(tag, "timeTextColor="+timeTextColor+"---timeTextSize="+timeTextSize);

        ta.recycle();

        init();
    }

    private void init(){
        nameRectPaint = new Paint();
        nameRectPaint.setColor(Color.WHITE);

        namePaint = new Paint();
        namePaint.setTextSize(nameTextSize);
        namePaint.setColor(nameTextColor);
        namePaint.setAntiAlias(true);

        timePaint = new Paint();
        timePaint.setTextSize(timeTextSize);
        timePaint.setColor(timeTextColor);
        timePaint.setAntiAlias(true);

        this.setOnTouchListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(iconBitmap!=null){
            canvas.drawBitmap(iconBitmap, 0, 0, new Paint());
        }
        canvas.drawRect(iconWidth, 0, getStringWidth(name, namePaint) + iconWidth, getStringHeight(namePaint), nameRectPaint);

        canvas.drawText(name, iconWidth, getStringHeight(namePaint) - 15, namePaint); //绘制用户昵称

        canvas.drawText(date, iconWidth, getStringHeight(namePaint) + getStringHeight(timePaint), timePaint); //发布时间

        super.onDraw(canvas);
    }

    @Override
    public void setName(String name) {
        this.name = name;
        invalidate(iconWidth, 0, iconWidth + getStringWidth(name, namePaint), getStringHeight(namePaint));
    }

    @Override
    public void setNameTextSize(float size) {
        namePaint.setTextSize(size);
        invalidate(iconWidth, 0, iconWidth+getStringWidth(name, namePaint), getStringHeight(namePaint));
    }

    @Override
    public void setNameTextColor(int colorId) {
        namePaint.setColor(getContext().getResources().getColor(colorId));
    }

    @Override
    public void setUserIcon(String url) {

    }

    @Override
    public void setUserIcon(Bitmap bitmap) {
        iconBitmap = bitmap;
        iconWidth = iconBitmap.getWidth();
        invalidate();
    }

    @Override
    public void setUserIcon(int width, int height) {

    }

    @Override
    public void setTime(String date) {
        this.date = date;
    }

    @Override
    public void setTimeTextSize(float size) {
        timePaint.setTextSize(size);
    }

    @Override
    public void setTimeTextColor(int colorId) {
        timePaint.setColor(getContext().getResources().getColor(colorId));
    }

    @Override
    public void setOnNameClickListener(OnNameClickListener onNameClickListener) {
        this.onNameClickListener = onNameClickListener;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(x>iconWidth && iconWidth+getStringWidth(name, namePaint)>x && getStringHeight(namePaint)>y){
                    namePaint.setColor(Color.WHITE);
                    nameRectPaint.setColor(nameTextBackground);
                    isClickName = true;
                    invalidate(iconWidth, 0, iconWidth+getStringWidth(name, namePaint), getStringHeight(namePaint));
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(motionEvent.getX()-x > moveXY || motionEvent.getY()-y > moveXY){
                    Log.i(tag, "---184---");
                    isClickName = false;
                }
                if(motionEvent.getRawX()>0 || motionEvent.getRawY()>0){
                    Log.i(tag, "---188---");
                    isClickName = false;
                    if(nameRectPaint.getColor()!=Color.WHITE){
                        namePaint.setColor(Color.BLACK);
                        nameRectPaint.setColor(Color.WHITE);
                        invalidate(iconWidth, 0, iconWidth+getStringWidth(name, namePaint), getStringHeight(namePaint));
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if(isClickName){
                    isClickName = false;
                    namePaint.setColor(Color.BLACK);
                    nameRectPaint.setColor(Color.WHITE);
                    invalidate(iconWidth, 0, iconWidth+getStringWidth(name, namePaint), getStringHeight(namePaint));
                    if(onNameClickListener!=null){
                        onNameClickListener.onClick();
                    }
                }
                break;
        }

        return true;
    }

    /**
     * 设置点击
     * */
    public interface OnNameClickListener{
        void onClick();
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
