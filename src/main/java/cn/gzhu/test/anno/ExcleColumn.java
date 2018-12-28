package cn.gzhu.test.anno;

import cn.gzhu.test.constant.ExcelColumType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

/**
 * 描述：excle字段标题
 * excle的字段从0开始
 */
@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcleColumn {

    int index() default -1;

    ExcelColumType javaType();


}
