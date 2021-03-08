package com.example.androidx_branch.weixing

import android.animation.Animator
import android.annotation.SuppressLint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.example.androidx_branch.R
import com.example.androidx_branch.video.MyVideoView
import com.uppack.lksmall.baseyu.BaseActivity
import com.uppack.lksmall.baseyu.weight.util.ViewUtil
import kotlinx.android.synthetic.main.activity_weixing.*

/**
 * @Author Yu
 * @Date 2021/3/5 16:17
 * @Description 滑动监听
 */
class WeiXingActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_weixing
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun initView() {
        img_head.setOnClickListener {
            Toast.makeText(this@WeiXingActivity,"dsadsad",Toast.LENGTH_SHORT).show()
        }
    }

    override fun initViewData() {
    }

    override fun observeLiveData() {
    }




}