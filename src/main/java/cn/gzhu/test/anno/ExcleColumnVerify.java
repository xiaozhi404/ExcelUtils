package cn.gzhu.test.anno;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

/**
 * 描述：校验excle字段
 */
@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcleColumnVerify {

    //校验非空
    boolean notNull() default false;

    //校验手机号
    boolean isPhoneNum() default false;

    //校验身份证号
    boolean isIDCardNo() default false;
}
