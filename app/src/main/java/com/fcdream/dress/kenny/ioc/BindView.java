package com.fcdream.dress.kenny.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by shmdu on 2017/8/31.
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindView {

    int id();

    boolean click() default false;

    String clickEvent() default "";
}
