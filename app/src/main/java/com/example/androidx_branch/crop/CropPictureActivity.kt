package com.example.androidx_branch.crop

import android.content.Intent
import android.widget.SeekBar
import com.example.androidx_branch.R
import com.uppack.lksmall.baseyu.BaseActivity
import kotlinx.android.synthetic.main.activity_crop.*

/**
 * @Author Yu
 * @Date 2021/5/21 8:30
 * @Description TODO
 */
class CropPictureActivity:BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_crop
    }

    override fun initView() {
        //压缩bitmap宽度至1080
//        Bitmap bitmap = LikeQQCropViewUtils.compressBitmapForWidth(this, R.drawable.bird, 1080);
        //如果你通过储存路径从手机相册直接获取图片(未压缩)，在保证bitmap不会oom的情况下，可以直接调用setBitmap方法
        //否则乖乖调用  以下方法(这些方法可以防止OOM)
        //推荐使用setBitmapForHeight()
        //推荐使用setBitmapForWidth()
        //setBitmapForScale()
        //setBitmap(多参)


        likeView.setBitmapForWidth(R.drawable.bird, 1080)
        sb.setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                //动态改变裁剪框圆角
                val cropWidth = likeView.clipWidth / 2
                val newRadius = progress * 1f / sb.max * cropWidth
                likeView.radius = newRadius
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        btHorizontalFlip.setOnClickListener {
            //水平翻转
            likeView.horizontalFlip()
        }
        btVerticalFlip.setOnClickListener {
            //垂直翻转
            likeView.verticalFlip();
        }
        btBoth.setOnClickListener {
            //垂直水平翻转
            likeView.verticalAndHorizontalFlip()
        }
        btReset.setOnClickListener {
            //重置图片位置
            likeView.reset()
        }
        btClip.setOnClickListener {
            //裁剪
            TestBean.bitmap = likeView.clip()
            val intent =
                Intent(this, TestActivity::class.java)
            startActivity(intent)
        }
    }

    override fun initViewData() {
    }

    override fun observeLiveData() {
    }
}