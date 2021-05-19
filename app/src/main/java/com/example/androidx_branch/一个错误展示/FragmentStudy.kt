package com.example.androidx_branch.一个错误展示

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.example.androidx_branch.R
import com.uppack.lksmall.baseyu.BaseActivity
import kotlinx.android.synthetic.main.activity_frastudy.*

/**
 * @Author Yu
 * @Date 2021/4/28 11:13
 * @Description TODO
 */
class FragmentStudy : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_frastudy
    }
    override fun initView() {
        var f1 = Fragment1()
        btn_1.setOnClickListener {
            showFragment(f1)
        }
        btn_2.setOnClickListener {
            showFragment(Fragment12())

        }
        btn_3.setOnClickListener {
            showFragment(Fragment3())

        }
        btn_4.setOnClickListener {
            showFragment(Fragment4())

        }
        btn_5.setOnClickListener {
            showFragment(Fragment5())

        }
    }

    override fun initViewData() {
    }

    override fun observeLiveData() {
    }

    lateinit var translation: FragmentTransaction
    fun showFragment(fra: Fragment) {
        translation = supportFragmentManager.beginTransaction()
        if (fra.isVisible) {
            translation.hide(fra)
        } else {
            if(fra.isAdded){
                translation.show(fra)
            }else{
                translation.add(R.id.fra_content, fra, fra.javaClass.name)
            }
        }
        translation.commit()
    }
}