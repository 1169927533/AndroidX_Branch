package com.example.androidx_branch.spann

import android.content.Context
import android.graphics.Typeface
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.example.androidx_branch.R
import kotlinx.android.synthetic.main.view_fanin.view.*
import kotlinx.android.synthetic.main.view_user_leve.view.*


/**
 * 用户等级小图标
 */
class FanGroupLevelView : FrameLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initViewView()
    }

    private fun initViewView() {

        LayoutInflater.from(context).inflate(R.layout.view_fanin, this, true)

    }
}