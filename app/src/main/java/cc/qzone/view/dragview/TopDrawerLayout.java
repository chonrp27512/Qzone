package cc.qzone.view.dragview;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * User: (1203596603@qq.com)
 * Date: 2015-09-09
 * Time: 21:43
 * Version 1.0
 */

public class TopDrawerLayout extends ViewGroup {

    private View mBottomMenuView;

    private View mContentView;

    private ViewDragHelper mViewDragHelper;

    public TopDrawerLayout(Context context) {
        this(context, null);
    }

    public TopDrawerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopDrawerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mViewDragHelper = ViewDragHelper.create(this, new DragHelperCallback());
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_TOP);
    }

    private class DragHelperCallback extends ViewDragHelper.Callback{
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mBottomMenuView;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            int newTop = Math.max(-child.getHeight(), Math.min(top, 0));
            return newTop;
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            mViewDragHelper.captureChildView(mBottomMenuView, pointerId);
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return child == mBottomMenuView ? child.getHeight():0;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            int childHeight = changedView.getHeight();
            float offset = (float)(childHeight+top)/childHeight;

            changedView.setVisibility(offset == 0 ? View.INVISIBLE:View.VISIBLE);
            invalidate();

        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            int childHeight = releasedChild.getHeight();
            float offset = (childHeight+releasedChild.getTop())*1.0f/childHeight;
            mViewDragHelper.settleCapturedViewAt(releasedChild.getLeft(), yvel > 0 || yvel == 0 && offset > 0.5f ? 0 : -childHeight);
            invalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if(mViewDragHelper.continueSettling(true)){
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSie = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(widthSie, heightSize);

        View bottomView = getChildAt(1);
        MarginLayoutParams lp = (MarginLayoutParams) bottomView.getLayoutParams();
        int drawerWidthSpec = getChildMeasureSpec(widthMeasureSpec, lp.leftMargin+lp.rightMargin, lp.width);
        int drawerHeightSpec = getChildMeasureSpec(heightMeasureSpec, lp.bottomMargin + lp.topMargin, lp.height);

        bottomView.measure(drawerWidthSpec, drawerHeightSpec);

        View contentView = getChildAt(0);
        lp = (MarginLayoutParams)contentView.getLayoutParams();
        int contentWidthSpec = MeasureSpec.makeMeasureSpec(widthSie-lp.leftMargin-lp.rightMargin, MeasureSpec.EXACTLY);
        int contentHeightSpec = MeasureSpec.makeMeasureSpec(heightSize-lp.topMargin-lp.bottomMargin, MeasureSpec.EXACTLY);
        contentView.measure(contentWidthSpec, contentHeightSpec);

        mBottomMenuView = bottomView;
        mContentView = contentView;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View menuView = mBottomMenuView;
        View contentView = mContentView;

        MarginLayoutParams lp = (MarginLayoutParams) contentView.getLayoutParams();
        contentView.layout(lp.leftMargin, lp.topMargin,
                lp.leftMargin+contentView.getMeasuredWidth(), lp.topMargin+contentView.getMeasuredHeight());

        lp = (MarginLayoutParams)menuView.getLayoutParams();
        int menuHeight = menuView.getMeasuredHeight();
        int childTop = - menuHeight;

        menuView.layout(lp.leftMargin, childTop, lp.leftMargin+menuView.getMeasuredWidth(),
                childTop+menuHeight);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new MarginLayoutParams(p);
    }
}
