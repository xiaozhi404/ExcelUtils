package cn.gzhu.test.utils;

import cn.gzhu.test.anno.ExcleColumnVerify;
import cn.gzhu.test.exception.ColumnNullException;
import cn.gzhu.test.exception.IDCardNoException;
import cn.gzhu.test.exception.PhoneNumException;
import lombok.val;

import java.util.regex.Pattern;

public class ExcelColumnVerifyUtils {

    public static void verity(Object value, Integer row, String titleName, ExcleColumnVerify excleColumnVerify) {

        if (null == value) return;

        if (excleColumnVerify.notNull() && (null == value || "" == value.toString())) {
            throw new ColumnNullException("第" + row + "行，" + titleName + "不能为空");
        }

        if (excleColumnVerify.isIDCardNo() && value.toString().length() != 18) {
            throw new IDCardNoException("第" + row + "行，" + titleName + "字段输入不合法");
        }

        val pattern = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
        val matcher = pattern.matcher(value.toString());
        if (excleColumnVerify.isPhoneNum() && (value.toString().length() != 11 || !matcher.matches())) {
            throw new PhoneNumException("第" + row + "行，" + titleName + "字段输入不合法");
        }
    }
}
