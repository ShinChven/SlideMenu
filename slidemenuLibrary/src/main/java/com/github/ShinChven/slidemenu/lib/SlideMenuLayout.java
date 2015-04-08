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

    private static final int MOVE_SIZE = 10;
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
        mLeftMenu.setBackgroundColor(Color.RED);
        mRightMenu.setBackgroundColor(Color.GRAY);


        // add views from left to right
        addView(mLeftMenu);
        addView(mMainMenu);
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
            return true;
        }
        if (isVertical) {
            switch (ev.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    int currentScrollX = getScrollX();
                    int dist = (int) (ev.getX() - lastPoint.x);
                    int expectx = -dist + currentScrollX;
                    int finalX = 0;
                    if (expectx < 0) {
                        finalX = Math.max(expectx, -mLeftMenu.getMeasuredWidth());
                    } else {
                        finalX = Math.min(expectx, mRightMenu.getMeasuredWidth());
                    }
                    scrollTo(finalX, 0);
                    lastPoint.x = (int) ev.getX();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    isVertical = false;
                    isSliding = false;
                    invalidate();
                    break;

            }
        }else {

            switch (ev.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:

                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    isVertical = false;
                    isSliding = false;
                    invalidate();
                    break;

            }
        }

        return super.dispatchTouchEvent(ev);
    }

    private Point lastPoint = new Point();
    private boolean isVertical;

    private void getEventType(MotionEvent ev) {
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                lastPoint.x = (int) ev.getX();
                lastPoint.y = (int) ev.getY();

                break;
            case MotionEvent.ACTION_MOVE:
                int x = (int) Math.abs(lastPoint.x - ev.getX());
                int y = (int) Math.abs(lastPoint.y - ev.getY());
                if (x >= MOVE_SIZE && x > y) {
                    isSliding = true;
                    isVertical = true;
                    lastPoint.x = (int) ev.getX();
                    lastPoint.y = (int) ev.getY();
                } else if (y >= MOVE_SIZE && y > x) {
                    isSliding = true;
                    isVertical = false;
                    lastPoint.x = (int) ev.getX();
                    lastPoint.y = (int) ev.getY();
                }

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;

        }
    }


}
