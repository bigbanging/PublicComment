package com.litte.publiccomment.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by litte on 2018/1/29.
 */

public class MyLetterView extends ImageView {
    String[] letters = new String[]{
            "热门","A","B","C","D","E","F",
            "G","H","I","J","K","L","M",
            "N","O","P","Q","R","S","T","U","V",
            "W","X","Y","Z"
    };
    Paint paint;//画笔
    onTouchLetterListener listener;
    public MyLetterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public void setOnTouchLetterListener(onTouchLetterListener listener){
        this.listener = listener;
    }
    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        float size = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,14,getResources().getDisplayMetrics());
        paint.setTextSize(size);//画出来的文字的大小
    }

    /**
     * 用来设计MyLetterView的尺寸宽高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //改写务必保留此功能
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //针对WRAP_CONTENT的改写
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        if (mode == MeasureSpec.AT_MOST){
            int paddingLeft = getPaddingLeft();
            int paddingRight = getPaddingRight();
            int contentWidth = 0;
            for (int i = 0; i < letters.length; i++) {
                String letter = letters[i];
                Rect bounds = new Rect();
                paint.getTextBounds(letter,0,letter.length(),bounds);
                if (bounds.width()>contentWidth){
                    contentWidth = bounds.width();
                }
            }
            int size = paddingLeft+contentWidth+paddingRight;
            setMeasuredDimension(size,MeasureSpec.getSize(heightMeasureSpec));
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    /**
     * 一定重写
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        for (int i = 0;i<letters.length;i++) {
            String letter = letters[i];
            Rect bounds = new Rect();
            paint.getTextBounds(letter,0,letter.length(),bounds);
            float x = width/2 - bounds.width()/2;
            float y = height/letters.length + bounds.height()/2+i*height/letters.length;
            canvas.drawText(letter, x, y, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                //改变背景
                setBackgroundColor(Color.GRAY);
                if (listener != null){
                    //手指按下或移动MyLetterView顶的距离
                    float y = event.getY();
                    //根据距离（Y）换算文字的下标值
                    int idx = (int) ((y * letters.length) / getHeight());
                    if (idx>=0&&idx<=letters.length){
                        String letter = letters[idx];
                        Log.i("TAG", "onTouchEvent: 触发 的位置"+letter);
                        listener.onTouchLetter(this,letter);
                    }
                }
                break;
            default:
                setBackgroundColor(Color.TRANSPARENT);
                break;
        }
        return true;
    }
    public interface onTouchLetterListener{
        void onTouchLetter(MyLetterView view,String letter);
    }
}
