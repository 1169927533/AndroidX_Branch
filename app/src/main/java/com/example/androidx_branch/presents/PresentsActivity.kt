package com.example.androidx_branch.presents

import androidx.lifecycle.lifecycleScope
import com.example.androidx_branch.R
import com.uppack.lksmall.baseyu.BaseActivity
import kotlinx.android.synthetic.main.activity_presents.*

/**
 * @Author Yu
 * @Date 2021/6/24 10:32
 * @Description 礼物  本来想用Surfaceview来实现得，但是如果展示得效果是有动画得 那么就没办法在surfaceview上面进行绘制
 */
class PresentsActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_presents
    }

    override fun initView() {
        btnInit.setOnClickListener {
           surfaceViewGift.giftShowController?.initGiftTrack(lifecycleScope)
        }
        btnSend.setOnClickListener {
            surfaceViewGift.giftShowController?.addGift(ed.text.toString())
        }
    }

    override fun initViewData() {
    }

    override fun observeLiveData() {
    }
}