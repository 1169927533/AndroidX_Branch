package com.example.androidx_branch.lianxiren.liebiao

import android.util.Log
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.androidx_branch.R
import kotlinx.android.synthetic.main.item_head.view.*

/**
 * @Author Yu
 * @Date 2021/2/24 10:36
 * @Description TODO
 */
class HorizalAdapter(var data: ArrayList<String>) :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_head, data) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        Log.i("Zkc","onCreateViewHolder被调用了")
        return super.onCreateViewHolder(parent, viewType)
    }
    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.let {
            it.itemView.btn_head.text = item
        }
    }
}