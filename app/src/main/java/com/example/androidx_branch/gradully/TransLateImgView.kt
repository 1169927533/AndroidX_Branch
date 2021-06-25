package com.example.androidx_branch.gradully

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.example.a11699.lib_util.dp
import com.example.androidx_branch.R
import kotlinx.android.synthetic.main.activity_surface.view.*

/**
 * @Author Yu
 * @Date 2021/6/23 15:28
 * @Description TODO
 */
private val BITMAP_SIZE = 200f.dp//原始图片的大小

class TransLateImgView : AppCompatImageView {
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var beforeBitmap: Bitmap? = null
    private var afterBitmap: Bitmap? = null
    var parentWidth = 0
    var parentHeight = 0
    var sourWidth = 0
    var sourHegith = 0
    private var afterHeight = 0f
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        parentWidth = MeasureSpec.getSize(widthMeasureSpec)
        parentHeight = MeasureSpec.getSize(heightMeasureSpec)
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        beforeBitmap = getAvatar(BITMAP_SIZE.toInt(), R.drawable.avatar_rengwuxian)
        sourWidth = beforeBitmap!!.width
        sourHegith = beforeBitmap!!.height
        // 将任意图片设置成和 sourceBitmap一样大小的图
        afterBitmap = setBitmapToTargetSize(getAvatar(BITMAP_SIZE.toInt(), R.drawable.bird))
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.save()
        canvas!!.translate(
            (parentWidth - sourWidth) / 2.toFloat(),
            0f
        )
        canvas!!.drawBitmap(beforeBitmap!!, Matrix(), mPaint)
        canvas!!.translate(
            0f,
            sourHegith.toFloat() - afterHeight
        )
        canvas!!.drawBitmap(afterBitmap!!, Matrix(), mPaint)

        canvas!!.restore()

    }

    // 将图片缩放到指定大小 等比缩放
    private fun getAvatar(width: Int, resource: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, resource, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, resource, options)
    }

    // 将图片的长宽给自缩放到指定大小
    private fun setBitmapToTargetSize(bitmap: Bitmap): Bitmap {
        var matrix = Matrix()
        var scaleWidth = 0f
        var scaleHeight = 0f
        scaleWidth = if (sourWidth > bitmap.width) {
            sourWidth / bitmap.width.toFloat()
        } else {
            bitmap.width / sourWidth.toFloat()
        }
        scaleHeight = if (sourHegith > bitmap.height) {
            sourHegith / bitmap.height.toFloat()
        } else {
            bitmap.height / sourHegith.toFloat()
        }
        matrix.setScale(scaleWidth, scaleHeight)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }


    fun startTranslateAnimal() {
        var transAnimal = ObjectAnimator.ofFloat(this, "trans", 0f, sourHegith.toFloat())
        transAnimal.duration  =1000
        transAnimal.start()
    }

    // 动画更新走这里
    fun setTrans(transx: Float) {
        afterHeight = transx
        invalidate()
    }

}