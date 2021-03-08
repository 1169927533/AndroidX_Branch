package com.example.androidx_branch.fulldialog

import com.example.androidx_branch.R
import com.uppack.lksmall.baseyu.BaseActivity
import kotlinx.android.synthetic.main.activity_dialog.*

/**
 * @Author Yu
 * @Date 2021/3/3 20:14
 * @Description 各种样式的dialog
 */
class DialogActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_dialog
    }

    override fun initView() {
        btn_demo_dialog.setOnClickListener {
            var demoDialogFragment = DemoDialogFragment()
            demoDialogFragment.show(supportFragmentManager,"demodialog")
        }
    }

    override fun initViewData() {
    }

    override fun observeLiveData() {
    }
}