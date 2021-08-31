package com.example.androidx_branch.spann

import android.graphics.Bitmap
import android.os.Build
import android.text.*
import android.text.style.DynamicDrawableSpan
import android.text.style.ImageSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.androidx_branch.R
import com.uppack.lksmall.baseyu.BaseActivity
import kotlinx.android.synthetic.main.activity_spann.*


/**
 * @Author Yu
 * @Date 2021/4/9 10:46
 * @Description TODO
 */
class SpannActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_spann
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun initView() {
        moveImageview.setOnClickListener {
            Toast.makeText(this,"wobeidianji l ",Toast.LENGTH_SHORT).show()
        }
        var html1 =
            Html.fromHtml(
                "北京市发布霾黄色预警，<font color='#ff0000'><big><big>外出携带好</big></big></font>口罩",
                Html.FROM_HTML_MODE_COMPACT
            )
        var html2 = Html.fromHtml(
            "北京市发布霾黄色预警，<h3><font color='#ff0000'>外出携带好</font></h3>口罩",
            Html.FROM_HTML_MODE_COMPACT
        )

        tv1.text = html1
        tv2.text = html2


        val string = SpannableString("Bottom: span.\nBaseline: span.")
        // using the default alignment: ALIGN_BOTTOM
        // using the default alignment: ALIGN_BOTTOM
        string.setSpan(
            ImageSpan(this, R.mipmap.ic_launcher),
            7,
            8,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        string.setSpan(
            ImageSpan(this, R.mipmap.ic_launcher, DynamicDrawableSpan.ALIGN_BASELINE),
            22, 23, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        var dynamicDrawableSpan =
            ImageSpan(this, R.mipmap.ic_launcher, DynamicDrawableSpan.ALIGN_CENTER)

        string.setSpan(
            dynamicDrawableSpan,
            26, 27, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tv3.text = string
        setImageVIew()


        val titles: ArrayList<String> = ArrayList()
        val list: ArrayList<Fragment> = ArrayList()
        list.add(OneFragment("选项卡1"));
        list.add(OneFragment("选项卡2"));
        titles.add("选项卡1");
        titles.add("选项卡2");
        myViewPager.adapter = MyAdapter(supportFragmentManager, list, titles)
        myViewPager.setTargetView(moveImageview)
    }

    override fun initViewData() {
    }

    override fun observeLiveData() {
    }


    private val URLKEY_LEVEL = "TEM_LEVEL"
    private val URLKEY_LIANG = "TEM_LIANG"
    private val URLKEY_FANGROUP = "TEM_FANGROUP"
    fun setImageVIew() {
        var strHtml: String = ""
        var buffer = StringBuilder()
        var imgCount = 0


        //会员
        buffer.append(" <img src='" + "file:///android_asset/level/flag_icon_vip_stage_small.png'>")
        imgCount++
        buffer.append(" <img src='" + "file:///android_asset/level/")
            .append(URLKEY_LEVEL).append(".png")
            .append("'>") //等级
        imgCount++
        //靓号
        buffer.append(" <img src='" + "file:///android_asset/level/" + URLKEY_LIANG + ".png'>")
        imgCount++
        buffer.append(" <img src='" + "file:///android_asset/level/" + URLKEY_FANGROUP + ".png'>")
        imgCount++

        strHtml = buffer.toString()
        Log.i("zjc", "Html: ${strHtml}")


        var leveImg = getTreasureImg(llcontent)
        var fanGroup = getFanGroupSpan(llcontent)
        val spanned = Html.fromHtml(strHtml, Html.FROM_HTML_MODE_COMPACT)
        val spannableString = SpannableStringBuilder(spanned)
        val imageSpans: Array<ImageSpan> = spannableString.getSpans(
            0, spannableString.length,
            ImageSpan::class.java
        )

        var i = 0
        val size: Int = imageSpans.size
        while (i < size) {
            val imageSpan: ImageSpan = imageSpans.get(i)
            val d = imageSpan.drawable
            var s  = ImageSpan(d, ImageSpan.ALIGN_BASELINE)
            val start: Int = spannableString.getSpanStart(imageSpan)
            val end: Int = spannableString.getSpanEnd(imageSpan)

            if (imageSpan.source != null && imageSpan!!.source!!.contains(URLKEY_LEVEL)) {
                spannableString.setSpan(
                    leveImg,
                    start,
                    end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }   else if (imageSpan.source != null && imageSpan!!.source!!.contains(URLKEY_FANGROUP)) {
                spannableString.setSpan(
                    fanGroup,
                    start,
                    end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            } else {
                spannableString.setSpan(
                    s,
                    start,
                    end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            spannableString.removeSpan(imageSpan)
            i++
        }
        tv4.text = spannableString
    }

    /**
     * 获得等级图标
     *
     * @param level
     * @return
     */
    private fun getTreasureImg(vg: ViewGroup): ImageSpan {

        val view = LayoutInflater.from(this)
            .inflate(R.layout.view_small_level, vg, false) as SmallUserLevelView
        view.measure(
            View.MeasureSpec.makeMeasureSpec((1 shl 30) - 1, View.MeasureSpec.AT_MOST),
            View.MeasureSpec.makeMeasureSpec((1 shl 30) - 1, View.MeasureSpec.AT_MOST)
        )
        var bitmap = convertViewToBitmap(view)
        return bitmap?.let { ImageSpan(this, it, DynamicDrawableSpan.ALIGN_CENTER) }
    }


    /**
     * 获的粉丝团图标
     */
    private fun getFanGroupSpan(
        vg: ViewGroup
    ): ImageSpan {
        val view: FanGroupLevelView = LayoutInflater.from(this)
            .inflate(R.layout.view_fangroup_level, vg, false) as FanGroupLevelView
        view.measure(
            View.MeasureSpec.makeMeasureSpec((1 shl 30) - 1, View.MeasureSpec.AT_MOST),
            View.MeasureSpec.makeMeasureSpec((1 shl 30) - 1, View.MeasureSpec.AT_MOST)
        )
        val bitmap = convertViewToBitmap(view)
        return bitmap?.let { ImageSpan(this, it, DynamicDrawableSpan.ALIGN_CENTER) }
    }


    //view转bitmap
    private fun convertViewToBitmap(view: View): Bitmap {
        view.measure(
            View.MeasureSpec.makeMeasureSpec((1 shl 30) - 1, View.MeasureSpec.AT_MOST),
            View.MeasureSpec.makeMeasureSpec((1 shl 30) - 1, View.MeasureSpec.AT_MOST)
        )
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        view.buildDrawingCache()
        return view.drawingCache
    }
}