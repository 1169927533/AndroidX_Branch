package com.example.androidx_branch.cache

import com.example.androidx_branch.R
import com.opensource.svgaplayer.SVGADrawable
import com.opensource.svgaplayer.SVGADynamicEntity
import com.opensource.svgaplayer.SVGAParser
import com.opensource.svgaplayer.SVGAVideoEntity
import com.uppack.lksmall.baseyu.BaseActivity
import kotlinx.android.synthetic.main.activity_cache.*


/**
 * @Author Yu
 * @Date 2021/6/16 11:27
 * @Description SVGA学习使用
 */
class CacheActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_cache
    }

    override fun initView() {
        btnStart.setOnClickListener {
            initSvgaView("")
        }
    }

    override fun initViewData() {
    }

    override fun observeLiveData() {
    }

    private fun initSvgaView(svgaFile: String) {
        var parser = SVGAParser(this)
        parser.decodeFromAssets("nanjue.svga", object : SVGAParser.ParseCompletion {
            override fun onComplete(videoItem: SVGAVideoEntity) {
                val dynamicEntity = SVGADynamicEntity()
                dynamicEntity.setClickArea("img_10")
                val drawable = SVGADrawable(videoItem, dynamicEntity)
                svgView.setImageDrawable(drawable)
                svgView.startAnimation()
            }

            override fun onError() {
            }
        })
    }
}