package cn.gzhu.test.anno;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

/**
 * 描述：excle表
 */
@Target({TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcleSheet {

    //从哪一行开始读数据
    int startIndex();

    //最多导入多少条数据
    int maxRowNum() default Integer.MAX_VALUE;

    //是否导入空白行
    boolean importBlankRow() default true;
}
