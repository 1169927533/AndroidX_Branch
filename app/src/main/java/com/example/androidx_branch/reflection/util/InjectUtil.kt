package com.example.androidx_branch.reflection.util

import android.app.Activity
import android.os.Parcelable
import android.text.TextUtils
import android.view.View
import com.example.androidx_branch.reflection.annotion.InjectIdToView
import com.example.androidx_branch.reflection.annotion.InjectValueToView
import com.uppack.lksmall.baseyu.BaseActivity
import java.util.*

/**
 * @Author Yu
 * @Date 2021/2/20 14:58
 * @Description 反射工具类
 */
object InjectUtil {
    fun injectIdToView(activity: BaseActivity){
        val javaClass = activity.javaClass
        //获取activity声明的所有字段
        var declaredFields = javaClass.declaredFields
        for(value in declaredFields){
            //判断是否被注解 InjectIdToView进行标记
            if(value.isAnnotationPresent(InjectIdToView::class.java)){
                //获取注解的值
                var injectIdToView = value.getAnnotation(InjectIdToView::class.java)
                var viewId = injectIdToView.value
                var view = activity.findViewById<View>(viewId)
                value.isAccessible = true
                value.set(activity,view)
            }
        }
    }


    @Throws(IllegalAccessException::class)
    fun injectIntentValue(activity: Activity) {
        val cls: Class<out Activity> = activity.javaClass
        //获得数据
        val intent = activity.intent
        val extras = intent.extras ?: return

        //获得此类所有的成员
        val declaredFields = cls.declaredFields
        for (field in declaredFields) {
            if (field.isAnnotationPresent(InjectValueToView::class.java)) {
                val autowired: InjectValueToView =
                    field.getAnnotation(InjectValueToView::class.java)
                //获得key
                val key =
                    if (TextUtils.isEmpty(autowired.value)) field.name else autowired.value
                if (extras.containsKey(key)) {
                    var obj = extras[key]
                    //获得数组单个元素类型
                    val type = field.type
                    val componentType = type.componentType
                    //当前属性是数组并且是 Parcelable（子类）数组
                    if (field.type.isArray &&
                        Parcelable::class.java.isAssignableFrom(componentType!!)
                    ) {
                        val objs = obj as Array<Any>?
                        //创建对应类型的数组并由objs拷贝
                        val objects =
                            Arrays.copyOf(
                                objs,
                                objs!!.size,
                                field.type as Class<out Array<Any>?>
                            )
                        obj = objects
                    }
                    field.isAccessible = true
                    try {
                        field[activity] = obj
                    } catch (e: IllegalAccessException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}