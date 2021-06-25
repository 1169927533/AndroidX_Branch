package com.example.androidx_branch.presents.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import com.example.a11699.lib_util.dp
import com.example.androidx_branch.R
import com.example.androidx_branch.presents.GiftShowController
import kotlinx.android.synthetic.main.activity_surface.view.*

/**
 * @Author Yu
 * @Date 2021/6/24 10:35
 * @Description TODO
 */
class GiftSurfaceView : SurfaceView, SurfaceHolder.Callback {
    val mTextPaint by lazy {
        Paint().apply {
            color = Color.WHITE;
            isAntiAlias = true;
             setStyle(Paint.Style.FILL);
             setAntiAlias(true);
             setStrokeWidth(5f);
        }

    }
    var giftShowController: GiftShowController? = null
    private var mSurfaceHolder: SurfaceHolder? = null

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        mSurfaceHolder = holder
        mSurfaceHolder!!.addCallback(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        giftShowController = GiftShowController(this)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
    }

    fun drawView(content: String) {
        mSurfaceHolder?.let { viewHolder ->
            var canvas = viewHolder.lockCanvas()
            drawTextContent(content, canvas)
            viewHolder.unlockCanvasAndPost(canvas)
        }
    }

    fun setDsa(s: Float) {

    }

    private fun drawTextContent(inTextContent: String, canvas: Canvas) {
        getViewBitmap(R.layout.item_giftview)?.let {
            canvas?.drawBitmap(it,0f,0f,mTextPaint)
        }
    }

    fun getViewBitmap(layoutId: Int): Bitmap? {
        val view: View = LayoutInflater.from(context).inflate(layoutId, null)
        val me = MeasureSpec.makeMeasureSpec(
            0,
            MeasureSpec.UNSPECIFIED
        )
        view.measure(me, me)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        view.buildDrawingCache()
        return view.drawingCache
    }

}