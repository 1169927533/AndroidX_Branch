package com.example.androidx_branch.crop

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import android.widget.SeekBar
import com.example.androidx_branch.R
import com.example.androidx_branch.takepicture.Util
import com.uppack.lksmall.baseyu.BaseActivity
import kotlinx.android.synthetic.main.activity_crop.*
import kotlinx.android.synthetic.main.activity_crop.btn_getpicture
import kotlinx.android.synthetic.main.activity_get.*
import java.io.FileDescriptor

/**
 * @Author Yu
 * @Date 2021/5/21 8:30
 * @Description 图片裁剪 写的真的牛逼
 */
class CropPictureActivity : BaseActivity() {

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
        btn_getpicture.setOnClickListener {
            var intent = Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent, 2);
        }


        sb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            2 -> {
                // 得到图片的全路径
                val uri: Uri? = data?.getData()


                likeView.bitmap =    getBitmapFromUri(this, uri!!)
            }
        }
    }

    fun getBitmapFromUri(
        context: Context,
        uri: Uri?
    ): Bitmap? {
        try {
            val parcelFileDescriptor: ParcelFileDescriptor? =
                context.contentResolver.openFileDescriptor(uri!!, "r")
            val fileDescriptor: FileDescriptor = parcelFileDescriptor!!.getFileDescriptor()
            val image: Bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            parcelFileDescriptor!!.close()
            return image
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

}