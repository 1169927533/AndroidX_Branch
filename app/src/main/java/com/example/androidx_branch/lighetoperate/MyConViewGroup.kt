package com.example.androidx_branch.lighetoperate

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_lightoperate.view.*

/**
 * @Author Yu
 * @Date 2021/6/25 15:21
 * @Description TODO
 */
class MyConViewGroup : ConstraintLayout {
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    public override fun detachViewFromParent(child: View?) {
        super.detachViewFromParent(child)
    }

    public override fun detachViewFromParent(index: Int) {
        super.detachViewFromParent(index)
    }

    public override fun attachViewToParent(
        child: View?,
        index: Int,
        params: ViewGroup.LayoutParams?
    ) {
        super.attachViewToParent(child, index, params)
    }
}