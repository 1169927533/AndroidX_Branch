package com.example.androidx_branch.lianxiren

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.androidx_branch.R
import com.example.androidx_branch.lianxiren.sortlist.TelBean
import kotlinx.android.synthetic.main.item_tel.view.*

/**
 * @Author Yu
 * @Date 2021/2/23 15:36
 * @Description TODO
 */
class TelAdapter(data: ArrayList<TelBean>) :
    BaseQuickAdapter<TelBean, BaseViewHolder>(R.layout.item_tel, data) {
    override fun convert(helper: BaseViewHolder?, item: TelBean?) {
        helper?.let {
            it.itemView.tv_name.text = item?.name
            it.addOnClickListener(R.id.image_check)
        }

    }

    fun isGroupHeader(position: Int): Boolean {
        return if (position == 0) {
            true
        } else {
            val currentGroupName: String = getGroupName(position)
            val preGroupName: String = getGroupName(position - 1)
            preGroupName != currentGroupName
        }
    }

    fun getGroupName(position: Int): String {
        return data[position].pin!!.substring(0, 1)
    }

}