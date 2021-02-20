package com.example.androidx_branch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.androidx_branch.reflection.ReflectionStudyActivity
import com.example.androidx_branch.reflection.annotion.EnumAnnotation
import com.example.androidx_branch.reflection.annotion.InjectIdToView
import com.example.androidx_branch.reflection.util.InjectUtil
import com.uppack.lksmall.baseyu.BarUtils
import com.uppack.lksmall.baseyu.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    @EnumAnnotation  //被这个注解注释的字段 只能声明成1 2 4 这三个值 原理就在于@IntDef
    var num = 1

    @InjectIdToView(R.id.tv_main)
    lateinit var tvMain: TextView

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        InjectUtil.injectIdToView(this@MainActivity)
        tvMain.text = "通过反射获取值"

        getToolbarHeight()

        btn_reflection.setOnClickListener {
            var injtent = Intent(this@MainActivity, ReflectionStudyActivity::class.java)
            injtent.putExtra("name", "余智强")
            injtent.putExtra("information", "通过反射的方式获取activity的传值")
            val checkList = ArrayList<String>()
            checkList.add("数组")
            var intArray = intArrayOf(1,2,3)
            injtent.putExtra("list", checkList);
            injtent.putExtra("intlist",intArray)
            startActivity(injtent)
        }
    }

    override fun initViewData() {
    }

    override fun observeLiveData() {
    }

    /**
     * 设置顶部信息marginTop,适配状态栏高度
     */
    private fun getToolbarHeight() {
        BarUtils.addMarginTopEqualStatusBarHeight(tvMain)
    }

}