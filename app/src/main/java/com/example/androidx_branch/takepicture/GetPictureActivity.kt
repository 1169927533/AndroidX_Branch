package com.example.androidx_branch.takepicture

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import com.example.androidx_branch.R
import com.uppack.lksmall.baseyu.BaseActivity
import kotlinx.android.synthetic.main.activity_get.*
import java.io.File


/**
 * @Author Yu
 * @Date 2021/5/7 16:02
 * @Description 获取相册图片
 */
class GetPictureActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_get
    }

    override fun initView() {
        btn_getpicture.setOnClickListener {
            var intent = Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent, 2);
        }
        btn_croppicture.setOnClickListener {
            Util.openAlbum(this)
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
                img_content.setImageURI(uri)
            }
            111 -> {
                //从相册选择
                if (data?.data == null) {
                    return
                }

                Util.clipPhoto(data?.data!!, this) //裁剪图片
            }
            112 -> {
                /*  val extrass: Bundle = data?.getExtras()!!
                  if (extrass != null) {
                      // 获取到裁剪后的图像
                      val bm = extrass.getParcelable<Bitmap>("data")
                      img_content.setImageBitmap(bm)
                  }*/
                //
                val uri: Uri? = data?.getData()
                img_content.setImageURI(uri)
            }
        }
    }

    fun getPath(uri: Uri): String {
        //这里开始的第二部分，获取图片的路径：
        val imgPath =
            arrayOf(MediaStore.Images.Media.DATA)
        //好像是android多媒体数据库的封装接口，具体的看Android文档
        val cursor: Cursor = managedQuery(uri, imgPath, null, null, null)
        //按我个人理解 这个是获得用户选择的图片的索引值
        val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        //将光标移至开头 ，这个很重要，不小心很容易引起越界
        cursor.moveToFirst()
        //最后根据索引值获取图片路径
        val path: String = cursor.getString(column_index)
        return path
    }

    fun getDataColumn(context: Context, contentUri: Uri?, selection: String?, selectionArgs: Array<String>?): String? {
        var contentResolver = context.contentResolver
        val mediaIdColumn = MediaStore.MediaColumns._ID
        val mimeTypeColumn = MediaStore.MediaColumns.MIME_TYPE
        val dataColumn = MediaStore.MediaColumns.DATA
        val sizeColumn = MediaStore.MediaColumns.SIZE
        val durationColumn = MediaStore.Video.Media.DURATION
        MediaStore.Files.FileColumns.DATA
        val projection =
            arrayOf(mediaIdColumn, mimeTypeColumn, dataColumn, sizeColumn)
        var cursor: Cursor? = null
        try {
            cursor = contentResolver.query(contentUri!!, projection, selection, selectionArgs, null)
            if (cursor != null && cursor.moveToFirst()) {
                var mediaId = cursor.getLong(cursor.getColumnIndexOrThrow(mediaIdColumn))
                val mimeType = cursor.getString(cursor.getColumnIndexOrThrow(mimeTypeColumn))
                var path = cursor.getString(cursor.getColumnIndexOrThrow(dataColumn))
                var size = cursor.getLong(cursor.getColumnIndexOrThrow(sizeColumn))
                var duration = 0L
                return path
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        } finally {
            cursor?.close()
        }
        return null
    }

}