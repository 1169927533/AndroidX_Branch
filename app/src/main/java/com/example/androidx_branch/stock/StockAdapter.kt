package com.example.androidx_branch.stock

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.androidx_branch.R
import com.example.androidx_branch.stock.bean.StockBean
import kotlinx.android.synthetic.main.item_content_detail.view.*

/**
 * @Author Yu
 * @Date 2021/4/19 10:42
 * @Description 股票页面
 */
class StockAdapter : BaseQuickAdapter<StockBean, BaseViewHolder>(
    R.layout.item_content_detail
) {
    override fun convert(helper: BaseViewHolder?, item: StockBean?) {
        helper?.let {
            it.itemView.tabView.setText(item?.stockName)
        }
    }


}