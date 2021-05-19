package com.example.androidx_branch.animal

import android.view.animation.TranslateAnimation
import android.widget.Toast
import com.example.androidx_branch.R
import com.uppack.lksmall.baseyu.BaseActivity
import kotlinx.android.synthetic.main.activity_scale.*

/**
 * @Author Yu
 * @Date 2021/3/18 15:08
 * @Description
 */
class ScaleActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_scale
    }

    override fun initView() {
        ssss.setOnClickListener {
            var transla = TranslateAnimation(0f, 0f, 300f, 300f)
            transla.fillAfter = true
            ddd.startAnimation(transla)
        }

    }

    override fun initViewData() {
    }

    override fun observeLiveData() {
    }
}