package com.example.androidx_branch.fulldialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androidx_branch.R

/**
 * @Author Yu
 * @Date 2021/3/3 20:17
 * @Description 全屏展示的DialogFragment
 */
class DemoDialogFragment:BaseDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_fragment_demo, container, false)

    }
}