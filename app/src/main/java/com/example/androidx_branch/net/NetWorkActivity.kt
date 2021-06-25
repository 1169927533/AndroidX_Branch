package com.example.androidx_branch.net

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.androidx_branch.R
import com.example.lib_network.NetWorkUtil
import com.example.lib_network.retrofitBuild.AppHttpConfig
import com.uppack.lksmall.baseyu.BaseActivity
import dowload
import kotlinx.android.synthetic.main.activity_network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.math.BigInteger
import java.util.concurrent.TimeUnit


/**
 * @Author Yu
 * @Date 2021/5/19 9:57
 * @Description TODO
 */
class NetWorkActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_network
    }

    override fun initView() {
        var retrofit = NetWorkUtil.newRetrofit(AppHttpConfig::class.java)
        btn_getnet.setOnClickListener {
            var service = retrofit.create(ApiInterFace::class.java)
            lifecycleScope.launch(Dispatchers.Main) {
                try {
                    var s = service.officialList()
                    Toast.makeText(this@NetWorkActivity, "${s[0].name}", Toast.LENGTH_SHORT).show()
                } catch (s: Exception) {
                    Toast.makeText(this@NetWorkActivity, "请求超时", Toast.LENGTH_SHORT).show()
                }
            }
        }
        //  .downloadFileWithDynamicUrlAsync("/7923-shanghai-007-shared-04-1256635546/fc01-1400382026/9327-93/27b26d59f7d9374af78ff7ab5881cd3c.m4a")
        //"https://static.veer.com/veer/static/resources/keyword/2020-02-19/b94a42fb11d64052ae5a9baa25f5370c.jpg"
        btn_download.setOnClickListener {
            /* val okHttpClient = OkHttpClient.Builder()
                 .connectTimeout(8, TimeUnit.SECONDS)
                 .build()
             var retrofittt = Retrofit.Builder()
                 .client(okHttpClient)
                 .baseUrl("https://baidu.com/")
                 .build()*/
            //https://blog.csdn.net/jingzz1/article/details/107234712
            lifecycleScope.launch {
                var response = retrofit.create(ApiInterFace::class.java)
                    .downloadFileWithDynamicUrlAsync("https://static.veer.com/veer/static/resources/keyword/2020-02-19/b94a42fb11d64052ae5a9baa25f5370c.jpg")
                Log.i("zjv", "${response.body()?.contentLength()}")
                dowload(
                    response
                ) {
                    success {
                        imgCallback.setImageURI(it)
                    }
                    error {}
                }.startDowload()
            }
        }
        btn_post.setOnClickListener {
            var service = retrofit.create(ApiInterFace::class.java)
            lifecycleScope.launch(Dispatchers.Main) {
                try {
                    service.getFileList("2334")
                } catch (s: Exception) {
                }
            }
        }
        btn_flow.setOnClickListener {
            var f = flow<Int> {
                emit(1)
                emit(2)
            }

            lifecycleScope.launch {
               f.map {
                    " hahha" + it
                }.flowOn(Dispatchers.Default)
                    .map {
                        " yuzhiqiang" + it
                    }
                    .flowOn(Dispatchers.IO).collect {
                    }

            }


            lifecycleScope.launch {
                fibonacci.take(100).collect {
                    Log.i("zjc", "${it}")
                }
            }
        }
    }

    var fibonacci: Flow<BigInteger> = flow {
        var x = BigInteger.ZERO
        var y = BigInteger.ONE
        while (true) {
            emit(x)
            x = y.also {
                y += x
            }
        }
    }

    override fun initViewData() {
    }

    override fun observeLiveData() {
    }
}