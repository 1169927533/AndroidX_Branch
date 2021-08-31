package com.example.androidx_branch.mysurfaceview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.androidx_branch.R;

/**
 * @Author Yu
 * @Date 2021/4/22 9:38
 * @Description TODO
 */
public class MySurfaceView extends SurfaceView implements Runnable, SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Thread t;
    private volatile boolean flag;
    private Canvas mCanvas;
    private Paint paint;
    float m_cirlce_r = 10;

    public MySurfaceView(Context context) {
        this(context, null);
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHolder = getHolder();
        mHolder.addCallback(this);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        t = new Thread(this);
        flag = true;
        t.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        flag = false;
        mHolder.removeCallback(this);
    }

    @Override
    public void run() {
        while (flag) {
            try {
                synchronized (mHolder) {
                    Thread.sleep(1000);
                    Draw();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (mCanvas != null) {

                }
            }
        }
    }

    private void Draw() {
        mCanvas = mHolder.lockCanvas();
        if(mCanvas!=null){
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(10);
            paint.setStyle(Paint.Style.FILL);
            if(m_cirlce_r>=(getWidth()/10)){
                m_cirlce_r = 0;
            }else{
                m_cirlce_r++;
            }
            Bitmap pic = ((BitmapDrawable)getResources().getDrawable(R.drawable.bb)).getBitmap();
            mCanvas.drawBitmap(pic,0,0,paint);
            for(int i = 0;i<5;i++){
                for(int j=0;j<8;j++){
                    mCanvas.drawCircle((getWidth()/5)*i+(getWidth()/10),
                            (getWidth()/8)*j+(getWidth()/16),m_cirlce_r,paint);
                }
            }
            mHolder.unlockCanvasAndPost(mCanvas);
        }
    }
}
