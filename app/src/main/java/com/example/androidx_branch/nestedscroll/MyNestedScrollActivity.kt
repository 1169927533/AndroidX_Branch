package com.example.androidx_branch.nestedscroll

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.androidx_branch.R
import com.example.androidx_branch.nestedscroll.method3.SimpleViewPagerIndicator
import com.example.androidx_branch.nestedscroll.method3.TabFragment
import com.uppack.lksmall.baseyu.BaseActivity
import kotlinx.android.synthetic.main.activity_nestedscroll.*

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
        id_stickynavlayout_viewpager!!.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {

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
    }

    override fun observeLiveData() {
    }
}