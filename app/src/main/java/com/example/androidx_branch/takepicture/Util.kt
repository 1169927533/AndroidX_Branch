package com.example.androidx_branch.takepicture

import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Environment.DIRECTORY_PICTURES
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


/**
 * @Author Yu
 * @Date 2021/5/6 16:25
 * @Description TODO
 */
class Util {
    companion object {
        /**
         * Android 10 以上手机使用 Environment获取外部存储文件路径的时候 需要在AndroidManifest.xml里面
         * android:requestLegacyExternalStorage="true" 进行声明 否则程序奔溃
         * Environment.getExternalStorageDirectory().absolutePath：  /storage/emulated/我的图片.png
         * 存在外部存储 不受app卸载的限制
         */
        fun saveToSDExternal_No(bitmap: Bitmap) {
            var filePath = Environment.getExternalStorageDirectory().absolutePath + "/我的图片.png"
            var imageFile: File
            Log.i("zjc", "存在外部存储 不受app卸载的限制 文件保存路径： ${filePath}")
            imageFile = File(filePath)
            if (!imageFile.exists()) {
                imageFile.createNewFile()
            }
            val fos = FileOutputStream(imageFile)
            //通过io流的方式来压缩保存图片
            bitmap!!.compress(Bitmap.CompressFormat.JPEG, 60, fos)
            fos.flush()
            fos.close()
        }

        /**
         * context.getExternalTilesDir(): /storage/emulated/0/Android/data/com.example.androidx_branch/files
         * 这个文件是随着app的卸载就没了
         */
        //存在外部存储 不受app卸载的限制
        fun saveToSDExternal_Yes(context: Context, p: Bitmap) {
            var filePath = context.getExternalFilesDir(null)
            var imageFile: File
            imageFile = File(filePath, "我的文件.png")
            if (!imageFile.exists()) {
                imageFile.createNewFile()
            }
            Log.i("zjc", "存在外部存储 不受app卸载的限制 文件保存路径： ${imageFile}")
            val fos = FileOutputStream(imageFile)
            //通过io流的方式来压缩保存图片
            p!!.compress(Bitmap.CompressFormat.JPEG, 60, fos)
            fos.flush()
            fos.close()
        }
        //将拍的照片保存到本地相册
        /**
         * 流程：
         * ContentValues设置保存的文件路径
         * 获取对应的uri 以流的方式读取文件 将bitmap写入里面
         */
        @RequiresApi(Build.VERSION_CODES.Q)
        fun  savePictureInLocal(context: Context, mBitmap:Bitmap){
            var valuess = ContentValues()
            val imageDate = SimpleDateFormat("yyyyMMdd-HHmmSS").format(Date(System.currentTimeMillis()))
            val SCREENSHOT_FILE_NAME_TEMPLATE = "winetalk_%s.png" //图片名称
            val mImageFileName = String.format(SCREENSHOT_FILE_NAME_TEMPLATE, imageDate)
            valuess.put(MediaStore.MediaColumns.RELATIVE_PATH,
                DIRECTORY_PICTURES
                        + File.separator + "winetalk"
            )
            valuess.put(MediaStore.MediaColumns.DISPLAY_NAME, mImageFileName)
            valuess.put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
            valuess.put(MediaStore.MediaColumns.DATE_ADDED, System.currentTimeMillis() / 1000)
            valuess.put(MediaStore.MediaColumns.DATE_MODIFIED, System.currentTimeMillis() / 1000)
            valuess.put(MediaStore.MediaColumns.IS_PENDING, 1)

            val resolver: ContentResolver = context.contentResolver
            val uri =
                resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, valuess)
            try {
                // First, write the actual data for our screenshot
                resolver.openOutputStream(uri!!).use { out ->
                    if (!mBitmap.compress(Bitmap.CompressFormat.PNG, 100, out) || !mBitmap.compress(
                            Bitmap.CompressFormat.JPEG,
                            100,
                            out
                        )
                    ) {
                        throw IOException("Failed to compress")
                    }
                }
                valuess.clear()
                valuess.put(MediaStore.MediaColumns.IS_PENDING, 0)
                resolver.update(uri!!, valuess, null, null)
            } catch (e: IOException) {
                resolver.delete(uri!!, null, arrayOf())
            }
        }


        //打开相册
        open fun openAlbum(activity: Activity) {
            val intent_album = Intent(Intent.ACTION_PICK)//ACTION_GET_CONTENT
            intent_album.type = "image/*"
            activity.startActivityForResult(intent_album, 111)
        }


        //裁剪图片
        open fun clipPhoto(uri: Uri, context: Activity) {
            var imageCropFile = createImageFile(context, true)
            if (imageCropFile != null) {
                val intent = Intent("com.android.camera.action.CROP")
                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                intent.putExtra("crop", "true")
                intent.putExtra("aspectX", 1) //X方向上的比例
                intent.putExtra("aspectY", 1) //Y方向上的比例
                intent.putExtra("outputX", 256) //裁剪区的宽
                intent.putExtra("outputY", 256) //裁剪区的高
                intent.putExtra("scale ", true) //是否保留比例
                intent.putExtra("return-data", true)
                intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
                intent.setDataAndType(uri, "image/*") //设置数据源
                if (Build.VERSION.SDK_INT >= 30) {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                } else {
                    val imgCropUri = Uri.fromFile(imageCropFile)
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imgCropUri)
                }
                context.startActivityForResult(intent, 112)
            }
        }

        lateinit var uri: Uri


        fun createImageFile(
            context: Context,
            isCrop: Boolean
        ): File? {
            return try {
                val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                var fileName = ""
                fileName =  "IMG_" + timeStamp + "_CROP.jpg"
                val rootFile =
                    File(
                        context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.getAbsoluteFile()
                            .toString() + File.separator + "capture"
                    )
                if (!rootFile.exists()) {
                    rootFile.mkdirs()
                }
                val imgFile: File
                if (Build.VERSION.SDK_INT >= 30) {
                    imgFile = File(
                        Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES)
                            .toString() + File.separator + fileName
                    )
                    // 通过 MediaStore API 插入file 为了拿到系统裁剪要保存到的uri（因为App没有权限不能访问公共存储空间，需要通过 MediaStore API来操作）
                    val values = ContentValues()
                    values.put(MediaStore.Images.Media.DATA, imgFile.absolutePath)
                    values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                    uri = context.contentResolver
                        .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)!!
                } else {
                    imgFile =
                        File(rootFile.absolutePath + File.separator + fileName)
                }
                imgFile
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }


}