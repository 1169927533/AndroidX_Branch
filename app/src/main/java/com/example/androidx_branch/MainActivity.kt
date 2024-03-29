package com.example.androidx_branch

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.ScaleAnimation
import android.widget.TextView
import androidx.core.view.LayoutInflaterCompat
import com.example.androidx_branch.animal.ScaleActivity
import com.example.androidx_branch.cache.CacheActivity
import com.example.androidx_branch.crop.CropPictureActivity
import com.example.androidx_branch.danmu.DanMuActivity
import com.example.androidx_branch.fulldialog.DialogActivity
import com.example.androidx_branch.gradully.PictureGraduallyActivity
import com.example.androidx_branch.lianxiren.ActivityA
import com.example.androidx_branch.lighetoperate.LightOperateActivity
import com.example.androidx_branch.mysurfaceview.MySurfaceViewStudy
import com.example.androidx_branch.nestedscroll.MyNestedScrollActivity
import com.example.androidx_branch.net.NetWorkActivity
import com.example.androidx_branch.presents.PresentsActivity
import com.example.androidx_branch.process.ProcessBarActivity
import com.example.androidx_branch.reflection.ReflectionStudyActivity
import com.example.androidx_branch.reflection.annotion.EnumAnnotation
import com.example.androidx_branch.reflection.annotion.InjectIdToView
import com.example.androidx_branch.reflection.util.InjectUtil
import com.example.androidx_branch.scene.SceneActivity
import com.example.androidx_branch.scrollerpicker.ScrollerPickerActivity
import com.example.androidx_branch.spann.SpannActivity
import com.example.androidx_branch.stock.StockActivity
import com.example.androidx_branch.storage.StorageActivity
import com.example.androidx_branch.takepicture.GetPictureActivity
import com.example.androidx_branch.takepicture.TakePictureActivity
import com.example.androidx_branch.weixing.WeiXingActivity
import com.example.androidx_branch.一个错误展示.FragmentStudy
import com.example.model_windscreen.WindScreenActivity
import com.uppack.lksmall.baseyu.BarUtils
import com.uppack.lksmall.baseyu.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_surface.*
import kotlinx.android.synthetic.main.dialog_fragment_demo.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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
        btnDou.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                while (true) {
                    delay(1000)
                    shakeAnimal()
                }
            }
        }
        btn_nestedscroll.setOnClickListener {
            startActivity<MyNestedScrollActivity>(this)
        }
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
        btn_scene.setOnClickListener { startActivity<SceneActivity>(this) }
        btn_cache.setOnClickListener { startActivity<CacheActivity>(this) }
        btn_picturegradlly.setOnClickListener { startActivity<PictureGraduallyActivity>(this) }
        btn_presents.setOnClickListener { startActivity<PresentsActivity>(this) }
        btn_picker.setOnClickListener { startActivity<ScrollerPickerActivity>(this) }
        btnLightOperateView.setOnClickListener { startActivity<LightOperateActivity>(this) }
        btnWindScreen.setOnClickListener { startActivity<WindScreenActivity>(this) }

        btncontinuousAnimal.setOnClickListener {

        }
        tvMain.setOnClickListener {
            scaleAnimator.start()
        }

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

    var scaleAnimator = ScaleAnimation(
        0f,
        1f,
        0f,
        1f,
        Animation.RELATIVE_TO_SELF,
        0.5f,
        Animation.RELATIVE_TO_SELF,
        0.5f
    ).apply {
        duration = 1000
        repeatCount = 4
    }

    private fun setFactory2() {
        LayoutInflaterCompat.setFactory2(layoutInflater, object : LayoutInflater.Factory2 {
            override fun onCreateView(
                parent: View?,
                name: String,
                context: Context,
                attrs: AttributeSet
            ): View? {
                var startTime = System.currentTimeMillis()
                var view = delegate.createView(parent, name, context, attrs)
                var endTime = System.currentTimeMillis()
                var cost = endTime - startTime
                Log.i("zjc", name + " cost " + cost + " ms")
                return view
            }

            override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
                return null
            }
        })
    }

    private fun setAnimation() {
        btn_danmu.setAnimation(scaleAnimator)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStart() {
        super.onStart()
        setAnimation()
    }

    // 震动动画
    private fun shakeAnimal() {
        var shakeAnimal: ObjectAnimator? = null
        if (shakeAnimal == null) {
            shakeAnimal = ObjectAnimator.ofFloat(btn_spann, "rotation", 0f, 8f, 0f, 8f, 0f)
            btn_spann.pivotX = btn_spann.measuredWidth.toFloat()
            btn_spann.pivotY = btn_spann.measuredHeight.toFloat() / 2f
            shakeAnimal!!.duration = 400
            shakeAnimal!!.interpolator = LinearInterpolator()

        }
        if (!shakeAnimal!!.isRunning) {
            shakeAnimal!!.start()
        }
    }


}