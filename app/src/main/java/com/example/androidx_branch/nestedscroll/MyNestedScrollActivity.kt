package com.example.androidx_branch.nestedscroll

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.androidx_branch.R
import com.example.androidx_branch.nestedscroll.method3.SimpleViewPagerIndicator
import com.example.androidx_branch.nestedscroll.method3.TabFragment
import com.uppack.lksmall.baseyu.BaseActivity
import com.uppack.lksmall.baseyu.weight.util.ViewUtil
import kotlinx.android.synthetic.main.activity_a.*
import kotlinx.android.synthetic.main.activity_nestedscroll.*
import kotlinx.android.synthetic.main.dialog_fragment_demo.*

/**
 * @Author Yu
 * @Date 2021/2/22 14:11
 * @Description TODO
 */
class MyNestedScrollActivity : BaseActivity() {
    private val mTitles = arrayOf("简介", "评价", "相关")

    private var mAdapter: FragmentPagerAdapter? = null
    private val mFragments: Array<TabFragment?> = arrayOfNulls<TabFragment>(mTitles.size)
    override fun getLayoutId(): Int {
        return R.layout.activity_nestedscroll
    }

    override fun initView() {

        id_stickynavlayout_indicator!!.setTitles(mTitles)

        for (i in mTitles.indices) {
            mFragments[i] = TabFragment.newInstance(mTitles[i]) as TabFragment
        }

        mAdapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return mFragments[position]!!
            }

            override fun getCount(): Int {
                return mFragments.size
            }
        }

        id_stickynavlayout_viewpager!!.adapter = mAdapter
        id_stickynavlayout_viewpager.currentItem = 0
    }

    override fun initViewData() {
        id_stickynavlayout_viewpager!!.setOnPageChangeListener(object :
            ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                id_stickynavlayout_indicator!!.scroll(position, positionOffset)
            }

            override fun onPageSelected(position: Int) {

            }
        })

        btn_startanimal.setOnClickListener {
            toBig = !toBig
            scaleAnimal(nestedParentLayout, toBig = toBig)
        }


    }

    var toBig = false
    fun scaleAnimal(view: View, toBig: Boolean) {
        var myWidth = view.width
        var myHeight = view.height
        var targetScaleX = 1f
        var targetScaleY = 1f
        var parentWidth = ViewUtil.getScreenWidth(this)
        var parentHeight = ViewUtil.getScreenHeight(this)
        if (toBig) {
            targetScaleX = parentWidth.toFloat() / myWidth
            targetScaleY = parentHeight.toFloat() / myHeight
        } else {
            targetScaleX = 1f
            targetScaleY = 1f
        }
        var animalX = ObjectAnimator.ofFloat(view, "scaleX", view.scaleX, targetScaleX)
        var animalY = ObjectAnimator.ofFloat(view, "scaleY", view.scaleY, targetScaleY)
        var animalSet = AnimatorSet()
        animalSet.playTogether(animalX, animalY)
        animalSet.duration = 1000
        setPivot(view)
        animalSet.start()
    }


    fun setPivot(view: View) {
        var parentWidth = ViewUtil.getScreenWidth(this)
        var parentHeight = ViewUtil.getScreenHeight(this)
        var childWidth = view.width
        var px = (childWidth * view.left).toFloat() / (parentWidth - childWidth).toFloat()
        view.pivotX = px
        var childHelper = view.height
        var py = (childHelper * view.top).toFloat() / (parentHeight - childHelper).toFloat()
        view.pivotY = py
    }

    override fun observeLiveData() {
    }
}