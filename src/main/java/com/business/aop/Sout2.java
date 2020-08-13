package com.business.aop;

import java.lang.annotation.*;

/**
 * @author Dang
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Sout2 {
    String s() default "";
}
