package com.example.androidx_branch

import android.content.Intent
import android.util.Log
import android.view.ViewTreeObserver
import android.widget.TextView
import com.example.androidx_branch.animal.ScaleActivity
import com.example.androidx_branch.crop.CropPictureActivity
import com.example.androidx_branch.danmu.DanMuActivity
import com.example.androidx_branch.fulldialog.DialogActivity
import com.example.androidx_branch.lianxiren.ActivityA
import com.example.androidx_branch.mysurfaceview.MySurfaceViewStudy
import com.example.androidx_branch.nestedscroll.MyNestedScrollActivity
import com.example.androidx_branch.net.NetWorkActivity
import com.example.androidx_branch.process.ProcessBarActivity
import com.example.androidx_branch.reflection.ReflectionStudyActivity
import com.example.androidx_branch.reflection.annotion.EnumAnnotation
import com.example.androidx_branch.reflection.annotion.InjectIdToView
import com.example.androidx_branch.reflection.util.InjectUtil
import com.example.androidx_branch.spann.SpannActivity
import com.example.androidx_branch.stock.StockActivity
import com.example.androidx_branch.storage.StorageActivity
import com.example.androidx_branch.takepicture.GetPictureActivity
import com.example.androidx_branch.takepicture.TakePictureActivity
import com.example.androidx_branch.weixing.WeiXingActivity
import com.example.androidx_branch.一个错误展示.ErrorDialog
import com.example.androidx_branch.一个错误展示.FragmentStudy
import com.uppack.lksmall.baseyu.BarUtils
import com.uppack.lksmall.baseyu.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {
    @EnumAnnotation  //被这个注解注释的字段 只能声明成1 2 4 这三个值 原理就在于@IntDef
    var num = 1

    @InjectIdToView(R.id.tv_main)
    lateinit var tvMain: TextView

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        InjectUtil.injectIdToView(this@MainActivity)
        tvMain.text = "通过反射获取值"
        Log.i("zjc", "MainActivity的WindowManger:${window.windowManager.hashCode()}")
        getToolbarHeight()

        tvMain.setOnClickListener {
            intimacyAnimal()
        }

        btn_reflection.viewTreeObserver.addOnGlobalLayoutListener {
           // btn_reflection.viewTreeObserver.removeOnGlobalLayoutListener(this@)
            //这里就可以获取他的高度了
        }


        btn_reflection.setOnClickListener {
            /* it.requestLayout()
             btn_reflection.text = "dsadasd"*/
            var injtent = Intent(this@MainActivity, ReflectionStudyActivity::class.java)
            injtent.putExtra("name", "余智强")
            injtent.putExtra("information", "通过反射的方式获取activity的传值")
            val checkList = ArrayList<String>()
            checkList.add("数组")
            var intArray = intArrayOf(1, 2, 3)
            injtent.putExtra("list", checkList);
            injtent.putExtra("intlist", intArray)
            startActivity(injtent)
        }

        btn_nestedscroll.setOnClickListener { startActivity<MyNestedScrollActivity>(this) }
        btn_tel.setOnClickListener { startActivity<ActivityA>(this) }
        btn_fulldialog.setOnClickListener { startActivity<DialogActivity>(this) }
        btn_weixing.setOnClickListener { startActivity<WeiXingActivity>(this) }
        btn_processbar.setOnClickListener { startActivity<ProcessBarActivity>(this) }
        btn_viewscale.setOnClickListener { startActivity<ScaleActivity>(this) }
        btn_danmu.setOnClickListener { startActivity<DanMuActivity>(this) }
        btn_spann.setOnClickListener { startActivity<SpannActivity>(this) }
        btn_stock.setOnClickListener { startActivity<StockActivity>(this) }
        btn_surface.setOnClickListener { startActivity<MySurfaceViewStudy>(this) }
        btn_error.setOnClickListener {
            /*var error = ErrorDialog.errorDialogFragment
            error.show(supportFragmentManager, "err")*/
            startActivity<FragmentStudy>(this)
        }
        btn_picture.setOnClickListener { startActivity<TakePictureActivity>(this) }
        btn_getpicture.setOnClickListener { startActivity<GetPictureActivity>(this) }
        btn_storage.setOnClickListener { startActivity<StorageActivity>(this) }
        btn_nettime.setOnClickListener { startActivity<StorageActivity>(this) }
        btn_netstudy.setOnClickListener { startActivity<NetWorkActivity>(this) }
        btn_crop.setOnClickListener { startActivity<CropPictureActivity>(this) }
    }


    override fun initViewData() {
    }

    override fun observeLiveData() {
    }

    /**
     * 设置顶部信息marginTop,适配状态栏高度
     */
    private fun getToolbarHeight() {
        BarUtils.addMarginTopEqualStatusBarHeight(tvMain)
    }

    //亲密度动画
    private fun intimacyAnimal() {
        intimacy.animate().translationY(-100f).alpha(0f).duration = 1000

    }
}