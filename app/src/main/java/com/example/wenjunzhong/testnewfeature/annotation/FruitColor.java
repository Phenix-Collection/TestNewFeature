package com.example.wenjunzhong.testnewfeature.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * Created by wenjun.zhong on 2017/6/9.
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FruitColor {

    public enum Color{BLUE, GREEN, RED}

    Color fruitColor() default Color.GREEN;
}
