package com.example.androidx_branch.一个错误展示

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.androidx_branch.R
import com.uppack.lksmall.baseyu.BaseFragment
import kotlinx.android.synthetic.main.fragment_template.*

/**
 * @Author Yu
 * @Date 2021/4/28 11:16
 * @Description TODO
 */
class Fragment1 :BaseFragment(){
    override fun getLayoutId(): Int {
       return R.layout.fragment_template
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("zjc","${javaClass.name} 执行了onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    override fun initView() {

    }

    override fun initViewData() {
    }

    override fun observeLiveData() {
    }
}