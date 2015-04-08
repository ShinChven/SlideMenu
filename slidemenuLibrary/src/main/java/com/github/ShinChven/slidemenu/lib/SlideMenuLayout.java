package com.github.ShinChven.slidemenu.lib;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * Created by ShinChven on 15/4/8.
 */
public class SlideMenuLayout extends RelativeLayout {

    private FrameLayout mMainMenu;
    private FrameLayout mLeftMenu;
    private FrameLayout mRightMenu;

    public SlideMenuLayout(Context context) {
        super(context);
        initLayout(context);
    }

    public SlideMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context);
    }

    public SlideMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context);
    }

    public void initLayout(Context context) {
        mMainMenu = new FrameLayout(context);
        mLeftMenu = new FrameLayout(context);
        mRightMenu = new FrameLayout(context);

        mMainMenu.setBackgroundColor(Color.BLUE);
        mLeftMenu.setBackgroundColor(Color.CYAN);
        mRightMenu.setBackgroundColor(Color.GRAY);

        addView(mMainMenu);
        addView(mLeftMenu);
        addView(mRightMenu);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMainMenu.measure(widthMeasureSpec, heightMeasureSpec);
        int realWidth = MeasureSpec.getSize(widthMeasureSpec);
        int tempWidthMeasure = MeasureSpec.makeMeasureSpec((int) (realWidth * 0.8f), MeasureSpec.EXACTLY);
        mLeftMenu.measure(tempWidthMeasure, heightMeasureSpec);
        mRightMenu.measure(tempWidthMeasure, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mMainMenu.layout(l, t, r, b);
        mLeftMenu.layout(l - mLeftMenu.getMeasuredWidth(), t, r, b);
        mRightMenu.layout(l + mMainMenu.getMeasuredWidth(), t, r + mMainMenu.getMeasuredWidth(), b);

    }

    private boolean isSliding;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!isSliding) {
            getEventType(ev);
        }


        return super.dispatchTouchEvent(ev);
    }

    private Point lastPoint = new Point();

    private void getEventType(MotionEvent ev) {
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                lastPoint.x = (int) ev.getX();
                lastPoint.y = (int) ev.getY();


                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;

        }
    }


}
