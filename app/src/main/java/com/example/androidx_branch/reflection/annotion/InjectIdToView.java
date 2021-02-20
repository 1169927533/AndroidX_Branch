package com.example.androidx_branch.reflection.annotion;

import androidx.annotation.IdRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author Yu
 * @Date 2021/2/20 14:57
 * @Description 通过反射给view绑定id
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectIdToView {
    @IdRes int value();
}
