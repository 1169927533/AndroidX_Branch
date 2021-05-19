package com.example.androidx_branch.mysurfaceview

import android.view.SurfaceView
import com.example.androidx_branch.R
import com.uppack.lksmall.baseyu.BaseActivity
import kotlinx.android.synthetic.main.activity_surface.*

/**
 * @Author Yu
 * @Date 2021/4/21 17:34
 * @Description TODO
 */
class MySurfaceViewStudy:BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_surface
    }

    override fun initView() {
        con.findViewById<SurfaceView>(R.id.id_info)
    }

    override fun initViewData() {
    }

    override fun observeLiveData() {
    }
}