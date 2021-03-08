package com.example.androidx_branch.fulldialog


import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import com.example.androidx_branch.R
import com.example.androidx_branch.fulldialog.util.CozyBarHelper

/**
 * @Author Yu
 * @Date 2021/3/3 20:24
 * @Description TODO
 */
open class BaseDialogFragment : DialogFragment() {
    var isFullScreen: Boolean = true//是否全屏
    var mStatusBarLightMode: Boolean = true //全屏情况，是否是浅色模式，即状态栏字体为深色>6.0
    private val mCanceledOnTouchOutside = true  //点击外部是否可取消
    var mKeyboardEnable: Boolean = true

    private val mSoftInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(AppCompatDialogFragment.STYLE_NORMAL, R.style.NoFloatingDialogFragment)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var window = dialog!!.window
        var layoutParams = window!!.attributes
        layoutParams.dimAmount = 0.5f
        layoutParams.gravity = Gravity.CENTER
        //全屏的话就这样设置
        if (isFullScreen) {
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        }
        window.attributes = layoutParams
        if (isFullScreen) {
            //不隐藏状态栏
            CozyBarHelper.setTransparentStatusBar(window)
            if (mStatusBarLightMode) {
                //设置状态栏字体颜色只对全屏有效，如果不是Match_parent即使设置了也不起作用
                CozyBarHelper.setStatusBarLightMode(window, true)
            }
            //全屏时适配刘海屏
            CozyBarHelper.fitNotch(window)
        }
        if (mKeyboardEnable) {
            window.setSoftInputMode(mSoftInputMode)
        }
        if (isCancelable) {
            dialog!!.setCanceledOnTouchOutside(mCanceledOnTouchOutside)
        }
    }

}