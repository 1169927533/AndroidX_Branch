package com.example.androidx_branch.process

import com.example.androidx_branch.R
import com.example.androidx_branch.clickNoRepeat
import com.uppack.lksmall.baseyu.BaseActivity
import com.uppack.lksmall.baseyu.weight.util.ViewUtil
import kotlinx.android.synthetic.main.activity_processbar.*

/**
 * @Author Yu
 * @Date 2021/3/11 15:00
 * @Description 进度条
 */
class ProcessBarActivity:BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_processbar
    }

    override fun initView() {
        tvNormal.post {
            tvItemSelect(0)
        }
        tvNormal.clickNoRepeat {
            tvItemSelect(0)
        }
        tvFollow.clickNoRepeat {
            tvItemSelect(1)
        }
    }

    override fun initViewData() {
    }

    override fun observeLiveData() {
    }
    private fun tvItemSelect(select: Int) {
        tvNormal.pivotX = tvNormal.width / 2f
        tvNormal.pivotY = tvNormal.height.toFloat()
        tvFollow.pivotX = tvNormal.width / 2f
        tvFollow.pivotY = tvNormal.height.toFloat()
        when (select) {
            0 -> {
                tvNormal.animate().scaleX(1.3f).scaleY(1.3f).duration = 100
                tvFollow.animate().scaleX(1f).scaleY(1f).duration = 100

                var centerX = tvNormal.measuredWidth / 2f + ViewUtil.dip2px(20f)
                var distance = centerX - imgIndicator.width / 2f
                imgIndicator.animate().translationX(distance).duration = 100

            }
            1 -> {
                tvFollow.animate().scaleX(1.3f).scaleY(1.3f).duration = 100
                tvNormal.animate().scaleX(1f).scaleY(1f).duration = 100

                var centerX =
                    tvFollow.measuredWidth / 2f + (tvNormal.measuredWidth + ViewUtil.dip2px(35f))
                var distance = centerX - imgIndicator.width / 2f
                imgIndicator.animate().translationX(distance).duration = 100
            }
        }
    }
}