package com.example.androidx_branch.net

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.androidx_branch.R
import com.example.androidx_branch.net.bean.OfficialListBean
import com.example.lib_network.NetWorkUtil
import com.example.lib_network.retrofitBuild.AppHttpConfig
import com.uppack.lksmall.baseyu.BaseActivity
import kotlinx.android.synthetic.main.activity_network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.net.SocketTimeoutException

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
        btn_getnet.setOnClickListener {
            var retrofit = NetWorkUtil.newRetrofit(AppHttpConfig::class.java)
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
    }

    override fun initViewData() {
    }

    override fun observeLiveData() {
    }
}