package com.example.androidx_branch.weixing;

import androidx.annotation.IntDef;
import androidx.annotation.StringDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author Yu
 * @Date 2021/3/5 16:48
 * @Description 手势滑动结束后的动画类型
 */
@StringDef({"SLIDE_DOWN","SLIDE_UP"})
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface AnimalTypeAnnotation {
}
