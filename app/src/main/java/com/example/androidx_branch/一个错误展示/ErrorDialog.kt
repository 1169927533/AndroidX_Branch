package com.example.androidx_branch.一个错误展示

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androidx_branch.R
import com.example.androidx_branch.fulldialog.BaseDialogFragment
import kotlinx.android.synthetic.main.dialog_fragment_demo.*

/**
 * @Author Yu
 * @Date 2021/4/27 10:44
 * @Description TODO
 */
class ErrorDialog:BaseDialogFragment() {
    companion object{
        var errorDialogFragment = ErrorDialog()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_fragment_demo, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        an.setOnClickListener {
            var error = ErrorDialog.errorDialogFragment
            error.show(childFragmentManager,"err")
        }
    }
}