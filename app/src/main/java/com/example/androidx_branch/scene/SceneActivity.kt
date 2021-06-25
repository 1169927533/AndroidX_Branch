package com.example.androidx_branch.scene

import android.os.Build
import android.transition.Fade
import android.transition.Scene
import android.transition.Transition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelLazy
import com.example.androidx_branch.AndroidVm
import com.example.androidx_branch.R
import com.uppack.lksmall.baseyu.BaseActivity
import kotlinx.android.synthetic.main.activity_scene.*

/**
 * @Author Yu
 * @Date 2021/6/10 10:14
 * @Description 场景切换
 */
class SceneActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_scene
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initView() {
        var mAScene = Scene.getSceneForLayout(conRoot, R.layout.layout_block, this)
        val mFadeTransition: Transition = Fade()

        btn_block.setOnClickListener {
            mFadeTransition.duration = 1000
            TransitionManager.go(mAScene, mFadeTransition)
            var s = conRoot.findViewById<TextView>(R.id.tv_report)
            var con1 = conRoot.findViewById<ConstraintLayout>(R.id.conBlock)
            s.setOnClickListener {
                var mAScene = Scene.getSceneForLayout(conRoot, R.layout.activity_scene, this)
                val mFadeTransition: Transition = Fade()
                TransitionManager.go(mAScene, mFadeTransition)
            }
        }

    }

    override fun initViewData() {
    }

    override fun observeLiveData() {
    }
}