package springboottest.aop;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Sout {
    String s1() default "";
    String s2() default "";
}
