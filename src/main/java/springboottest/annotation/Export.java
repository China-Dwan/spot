package springboottest.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Export {
    String showHeader() default "";//导出栏位名称必须是唯一的
    boolean isExport() default true;
    int index();
}
