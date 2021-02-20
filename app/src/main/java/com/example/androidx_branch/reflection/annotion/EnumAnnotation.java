package com.example.androidx_branch.reflection.annotion;

import androidx.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author Yu
 * @Date 2021/2/20 14:50
 * @Description 枚举注解  这个注解可以帮助我们只能定义枚举
 */
@IntDef({1,2,4})
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface EnumAnnotation {
}
