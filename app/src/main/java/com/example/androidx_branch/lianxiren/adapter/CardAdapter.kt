package com.example.androidx_branch.lianxiren.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.androidx_branch.R
import com.example.androidx_branch.lianxiren.sortlist.TelBean
import kotlinx.android.synthetic.main.item_card.view.*

/**
 * @Author Yu
 * @Date 2021/3/16 9:46
 * @Description TODO
 */
class CardAdapter(data: ArrayList<Int>) :
    BaseQuickAdapter<Int, BaseViewHolder>(R.layout.item_card, data){
    override fun convert(helper: BaseViewHolder?, item: Int) {
         helper?.let {
             it.itemView.img_head.setImageResource(item)
         }
    }
}