package com.example.androidx_branch.lianxiren

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidx_branch.R
import com.example.androidx_branch.lianxiren.sortlist.PinyinUtil
import com.example.androidx_branch.lianxiren.sortlist.TelBean
import com.uppack.lksmall.baseyu.BarUtils
import com.uppack.lksmall.baseyu.BaseActivity
import kotlinx.android.synthetic.main.activity_tel.*


/**
 * @Author Yu
 * @Date 2021/2/23 15:05
 * @Description TODO
 */
class TelActivity : BaseActivity() {
    var telList = ArrayList<String>()
    override fun getLayoutId(): Int {
        return R.layout.activity_tel
    }

    override fun initView() {

        BarUtils.addMarginTopEqualStatusBarHeight(recycleview)
        telList.add("于")
        telList.add("吧")
        telList.add("嘛")
        telList.add("嘛")
        telList.add("擦")
        telList.add("嚓")
        telList.add("于")
        telList.add("吧")
        telList.add("嘛")
        telList.add("嘛")
        telList.add("擦")
        telList.add("嚓")
        telList.add("于")
        telList.add("吧")
        telList.add("嘛")
        telList.add("嘛")
        telList.add("擦")
        telList.add("嚓")
    }


    override fun initViewData() {
        var pin = ArrayList<TelBean>()
        for (value in telList) {
            var telBean = TelBean(value, PinyinUtil.getPingYin(value),false)
            pin.add(telBean)
        }

        pin.sortBy {
            it.pin?.first()
        }
        recycleview.adapter = TelAdapter(pin).apply {
            setOnItemChildClickListener { _, view, position ->
                when (view.id) {
                    R.id.image_check -> {
                        view.isSelected = !view.isSelected
                        data[position]. select =  view.isSelected
                    }
                }
            }
        }
        recycleview.addItemDecoration(TelItemDirection(this))
        recycleview.layoutManager = LinearLayoutManager(this)

        btn_back.setOnClickListener {
            var l = ArrayList<TelBean>()
            for(value in pin){
                if(value.select){
                    l.add(value)
                }
            }
            val intent = intent
            var bundle = Bundle()
            bundle.putParcelableArrayList("l",l)
            intent.putExtras(bundle)
            setResult(100, intent)
            finish()
        }
    }


    override fun observeLiveData() {
    }

}