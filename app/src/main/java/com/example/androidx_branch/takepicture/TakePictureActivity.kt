package com.example.androidx_branch.takepicture

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Environment.getExternalStoragePublicDirectory
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.androidx_branch.R
import com.uppack.lksmall.baseyu.BaseActivity
import kotlinx.android.synthetic.main.activity_picture.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


/**
 * @Author Yu
 * @Date 2021/5/6 15:19
 * @Description TODO
 */
class TakePictureActivity : BaseActivity() {
    var REQUEST_TAKE_PHOTO_CODE = 1
    var REQUEST_TAKE_PHOTO_CODE2 = 2

    lateinit var imageFile: File

    override fun getLayoutId(): Int {
        return R.layout.activity_picture
    }

    override fun initView() {
        initPermission()
        btn_takepicture.setOnClickListener {
            startTakePhoto()
        }
    }

    override fun initViewData() {
    }

    override fun observeLiveData() {
    }

    fun startTakePhoto() {
        // 启动系统相机
        var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 启动activity并获取返回数据
        startActivityForResult(intent, REQUEST_TAKE_PHOTO_CODE2);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === REQUEST_TAKE_PHOTO_CODE2
            && resultCode === Activity.RESULT_OK
        ) {
            //这里获取到的是缩放后的图片，不是原始图片
            val b: Bundle? = data?.getExtras()
            if (b != null) {
                val bm = b["data"] as Bitmap?
                if (bm != null) {
                    img.setImageBitmap(bm)
//                   Util.saveToSDExternal_No(bm!!)//在Android10以后这个方法失效
                   // Util.saveToSDExternal_Yes(this, bm!!)

                    Util.savePictureInLocal(this, bm!!)
                }
            } else {
                Toast.makeText(this, "没有获取数据", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun initPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "同意了", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1
            )
        }

        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "同意了", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
        }

        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "同意了", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //同意
                } else {
                    Toast.makeText(this, "You denied CALL_PHONE permission", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    }
}