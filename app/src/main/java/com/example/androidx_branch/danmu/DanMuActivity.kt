package com.example.androidx_branch.danmu

import android.animation.Animator
import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.util.Log
import com.example.androidx_branch.R
import com.uppack.lksmall.baseyu.BaseActivity
import com.uppack.lksmall.baseyu.weight.util.ViewUtil
import kotlinx.android.synthetic.main.activty_danmu.*
import kotlinx.android.synthetic.main.layout_itemdanmu.view.*
import java.util.*

/**
 * @Author Yu
 * @Date 2021/3/25 15:20
 * @Description TODO
 */
class DanMuActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activty_danmu
    }

    override fun initView() {
        var string: String = "余智强再写代码这个东西还需要加以测试"


        btn_adddanmu.setOnClickListener {
            danmuview.queue.add("")
            danmuview.startInRoomAnimal()
        }
    }

    override fun initViewData() {
    }

    override fun observeLiveData() {
    }
}