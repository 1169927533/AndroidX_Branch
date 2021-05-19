package com.example.androidx_branch.mysurfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @Author Yu
 * @Date 2021/4/21 17:23
 * @Description 自定义surfaceview
 */
public class MyView_Two extends SurfaceView implements SurfaceHolder.Callback {
    private Paint mPaint = null;

    public MyView_Two(Context context) {
        this(context,null);

    }

    public MyView_Two(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint  = new Paint();
        mPaint.setColor(Color.RED);
        getHolder().addCallback(this);
    }

    public void draw() {
        //一定是在surface创建完成后在绘制
        //在surface结束之前结束所有操作
        Canvas canvas = getHolder().lockCanvas();
        canvas.drawColor(Color.WHITE);
        canvas.drawRect(0,0,100,100,mPaint);
        //绘制结束之后要进行一个解锁操作
        getHolder().unlockCanvasAndPost(canvas);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        draw();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
