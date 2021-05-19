package com.example.androidx_branch.lianxiren

import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.androidx_branch.R
import com.example.androidx_branch.lianxiren.adapter.CardAdapter
import com.example.androidx_branch.lianxiren.liebiao.HorizalAdapter
import com.example.androidx_branch.lianxiren.liebiao.HorizalLayoutManager
import com.example.androidx_branch.lianxiren.sortlist.LeftFlowLayoutManger
import com.example.androidx_branch.lianxiren.sortlist.SSTwo
import com.example.androidx_branch.lianxiren.sortlist.TelBean
import com.uppack.lksmall.baseyu.BaseActivity
import com.uppack.lksmall.baseyu.weight.util.ViewUtil
import kotlinx.android.synthetic.main.activity_a.*

/**
 * @Author Yu
 * @Date 2021/2/23 17:07
 * @Description TODO
 */
class ActivityA : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_a
    }

    override fun initView() {
        btn.setOnClickListener {
            startActivityForResult(Intent(this@ActivityA, TelActivity::class.java), 10)
        }

    }

    override fun initViewData() {
        initRecycleView()
      //  initRecycleView_Card()
    }

    private fun initRecycleView() {
        re_head.layoutManager = HorizalLayoutManager()
        var d = ArrayList<String>()
        d.add("1")
        d.add("2")
        d.add("3")
        d.add("4")
        d.add("5")
        d.add("6")
        d.add("7")
        d.add("8")
        d.add("9")
        d.add("10")
        d.add("11")
        d.add("12")
        d.add("13")
        d.add("14")
        d.add("15")
        d.add("16")
        d.add("17")
        d.add("18")
        d.add("19")
        d.add("20")
        d.add("21")
        d.add("22")
        d.add("23")
        d.add("24")
        d.add("25")
        d.add("26")

        re_head.adapter = HorizalAdapter(d)


        re_head.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //显示区域的高度
                var extent = re_head.computeHorizontalScrollExtent()
                //整体的高度
                var range = re_head.computeHorizontalScrollRange()
                //滚动的距离
                var offset = re_head.computeHorizontalScrollOffset()
                Log.i("zjc", "extent = ${extent} range = $range offset = $offset")
            }
        })
    }

    override fun observeLiveData() {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            10 -> {
                if (resultCode == 100) {
                    var bundle = data!!.extras
                    var l = bundle!!.getParcelableArrayList<TelBean>("l")
                    for (value in l!!) {
                        Log.i("zjc", value.name)
                    }
                }

            }
        }
    }

    private fun initRecycleView_Card() {
        var la = SSTwo(this)
        btn_stop.setOnClickListener {
            la.method = false
        }
        var d = ArrayList<Int>()
        d.add(R.drawable.three)
        d.add(R.drawable.two)
        d.add(R.drawable.one)
        d.add(R.drawable.four)
        var cardAdapter = CardAdapter(d)
        recycleview_card.adapter = cardAdapter
        recycleview_card.layoutManager = la

        //  PagerSnapHelper().attachToRecyclerView(recycleview_card)
    }
}