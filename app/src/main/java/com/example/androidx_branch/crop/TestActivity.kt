package com.example.androidx_branch.crop

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.androidx_branch.R
import kotlinx.android.synthetic.main.activity_test.*
import java.io.File
import java.io.FileOutputStream

/**
 * @Author Yu
 * @Date 2021/5/21 10:07
 * @Description TODO
 */
class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        ivv.setImageBitmap(TestBean.bitmap)
        saveBitMap(this,TestBean.bitmap)
    }

    fun saveBitMap(context: Context, bitmap: Bitmap) {
        var filePath = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.absolutePath
        var file = File(filePath, "${System.currentTimeMillis()}.png")
        var fileOutPutStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutPutStream)
        fileOutPutStream.flush()
        fileOutPutStream.close()
    }
}