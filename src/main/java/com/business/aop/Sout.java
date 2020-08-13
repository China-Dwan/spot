package com.business.aop;

import java.lang.annotation.*;

/**
 * @author Dang
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Sout {
    String s1() default "";
    String s2() default "";
}
