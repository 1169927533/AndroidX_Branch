package com.example.androidx_branch.mysurfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @Author Yu
 * @Date 2021/4/21 17:23
 * @Description TODO
 */
public class MyView extends SurfaceView implements SurfaceHolder.Callback {

    public MyView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    public void draw(){
        //一定是在surface创建完成后在绘制
        //在surface结束之前结束所有操作
        Canvas canvas = getHolder().lockCanvas();

        //绘制结束之后要进行一个解锁操作
        getHolder().unlockCanvasAndPost(canvas);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
