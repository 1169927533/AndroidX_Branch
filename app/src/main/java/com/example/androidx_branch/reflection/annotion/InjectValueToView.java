package com.example.androidx_branch.reflection.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author Yu
 * @Date 2021/2/20 17:08
 * @Description 注解：
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectValueToView {
    String value();
}
