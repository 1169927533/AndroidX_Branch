package com.example.androidx_branch.reflection

import android.widget.Toast
import com.example.androidx_branch.R
import com.example.androidx_branch.reflection.annotion.InjectValueToView
import com.example.androidx_branch.reflection.util.InjectUtil
import com.uppack.lksmall.baseyu.BaseActivity

/**
 * @Author Yu
 * @Date 2021/2/20 17:06
 * @Description 通过反射获取intent传的值
 */
class ReflectionStudyActivity:BaseActivity(){
    @InjectValueToView("name")
    lateinit var namew:String
    @InjectValueToView("information")
    lateinit var informationw:String

    @InjectValueToView("list")
    lateinit var list:ArrayList<String>

    @InjectValueToView("intlist")
    lateinit var intlist:IntArray


    override fun getLayoutId(): Int {
        return R.layout.activity_reflection
    }

    override fun initView() {
        InjectUtil.injectIntentValue(this@ReflectionStudyActivity)
        Toast.makeText(this, "$namew   $informationw    ${list.size}   ${intlist.size}", Toast.LENGTH_SHORT).show()

    }

    override fun initViewData() {
    }

    override fun observeLiveData() {
    }
}