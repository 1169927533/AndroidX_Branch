package com.example.androidx_branch.scrollerpicker

import android.content.Intent
import com.example.androidx_branch.R
import com.example.androidx_branch.scrollerpicker.sample1.SampleActivity1
import com.example.androidx_branch.scrollerpicker.sample2.SampleActivity
import com.uppack.lksmall.baseyu.BaseActivity
import kotlinx.android.synthetic.main.activity_scroller.*

/**
 * @Author Yu
 * @Date 2021/6/25 9:40
 * @Description 自定义选择器
 */
class ScrollerPickerActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_scroller
    }

    override fun initView() {
        default_scroll_picker_tv.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    SampleActivity1::class.java
                )
            )
        }
        custom_scroll_picker_tv.setOnClickListener {
            startActivity(Intent(this, SampleActivity::class.java))
        }
    }

    override fun initViewData() {
    }

    override fun observeLiveData() {
    }
}