package com.example.androidx_branch.storage

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.androidx_branch.R
import com.uppack.lksmall.baseyu.BaseActivity
import kotlinx.android.synthetic.main.activity_storage.*
import kotlinx.coroutines.*
import java.io.File
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread
import kotlin.math.log


/**
 * @Author Yu
 * @Date 2021/5/10 11:38
 * @Description Android存储学习
 */
class StorageActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_storage
    }

    override fun initView() {
        btn_inFIleDir.setOnClickListener {
            tv_content.text = filesDir?.absolutePath
        }
        btn_incacheDir.setOnClickListener {
            tv_content.text = cacheDir?.absolutePath
        }
        btn_exFIleDir.setOnClickListener {
            tv_content.text = getExternalFilesDir("")?.absolutePath
        }
        btn_exCacheDir.setOnClickListener {
            tv_content.text = externalCacheDir?.absolutePath
        }
        btn_getstorage.setOnClickListener {
            var intent = Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent, 122);
        }
        btn_getwebtime.setOnClickListener {
            getWebSiteDateTime("http://www.baidu.com")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === 122
            && resultCode === Activity.RESULT_OK
        ) {
            val resolver = contentResolver
            var ff = File(getExternalFilesDir("")?.path + "hahah.png")
            if (data != null) {
                data.data?.let {
                    resolver.openInputStream(it).use { stream ->
                        ff?.outputStream()?.let { fff ->
                            stream?.copyTo(fff)

                            fff.close()
                        }
                    }
                }
            }
        }
    }

    override fun initViewData() {
    }

    override fun observeLiveData() {
    }

    //获取网站时间。目的：防止有人更改系统时间
    fun getWebSiteDateTime(webUrl: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            var url = URL(webUrl)
            var uc = url.openConnection()
            uc.connect()
            var ld = uc.date
            val date = Date(ld) // 转换为标准时间对象
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA) // 输出北京时间
            var timw = sdf.format(date)
            withContext(Dispatchers.Main) {
                tv_content.text = timw
            }
        }
    }

}