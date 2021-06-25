package com.example.androidx_branch.gradully

import android.util.Log
import com.example.androidx_branch.R
import com.uppack.lksmall.baseyu.BaseActivity
import kotlinx.android.synthetic.main.activity_gradlly.*
import kotlinx.coroutines.*

/**
 * @Author Yu
 * @Date 2021/6/23 14:03
 * @Description 图片逐渐展示
 */
class PictureGraduallyActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_gradlly
    }

    override fun initView() {
        btnTranslate.setOnClickListener {
            imgContent.startTranslateAnimal()
        }
    }


    override fun initViewData() {
    }

    override fun observeLiveData() {
    }
}