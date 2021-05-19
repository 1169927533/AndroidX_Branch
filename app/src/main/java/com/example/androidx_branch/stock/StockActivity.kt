package com.example.androidx_branch.stock

import android.util.Log
import android.view.animation.AccelerateInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidx_branch.R
import com.example.androidx_branch.stock.bean.StockBean
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.uppack.lksmall.baseyu.BaseActivity
import kotlinx.android.synthetic.main.activity_stock.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @Author Yu
 * @Date 2021/4/19 10:45
 * @Description 仿照股票页面
 */
class StockActivity : BaseActivity() {
    var stockList = ArrayList<StockBean>()
    private val stackHeadAdapter by lazy {
        StockAdapter()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_stock
    }

    override fun initView() {
        stock_head_recycleview.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.HORIZONTAL, false
        )
        stock_head_recycleview.adapter = stackHeadAdapter
        initSockData()
        stock_head_recycleview.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.i("zjc","滑动：${dx}")
            }
        })
        btn_start.setOnClickListener {
            stock_head_recycleview.smoothScrollBy(10,0,AccelerateInterpolator())
        }
    }

    override fun initViewData() {
    }

    override fun observeLiveData() {
    }

    private fun initSockData() {
        var json = AssetsUtils.getJson("stock.json", applicationContext)
        stockList = Gson().fromJson(json, object : TypeToken<ArrayList<StockBean>>() {}.type)
        stackHeadAdapter.setNewData(stockList)
    }
}