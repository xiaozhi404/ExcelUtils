package cn.gzhu.test.pojo;

import cn.gzhu.test.anno.ExcleColumn;
import cn.gzhu.test.anno.ExcleColumnVerify;
import cn.gzhu.test.anno.ExcleSheet;
import cn.gzhu.test.constant.ExcelColumType;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 描述：考生excel模型
 */
@Data
@ExcleSheet(titleIndex = 1, startIndex = 2, importBlankRow = false, ignoreOnlyHaveNoRow = 0)
public class ExamineeExcelModel {

    @ExcleColumn(index = 1, javaType = ExcelColumType.STRING)
    private String name;

    @ExcleColumn(index = 2, javaType = ExcelColumType.STRING)
    private String sex;

    @ExcleColumn(index = 3, javaType = ExcelColumType.STRING)
    @ExcleColumnVerify(notNull = true, isIDCardNo = true)
    private String identityNum;

    @ExcleColumn(index = 4, javaType = ExcelColumType.STRING)
    @ExcleColumnVerify(notNull = true, isPhoneNum = true)
    private String mobile;

    @ExcleColumn(index = 5, javaType = ExcelColumType.STRING)
    private String grade;

    @ExcleColumn(index = 6, javaType = ExcelColumType.STRING)
    private String school;

    @ExcleColumn(index = 7, javaType = ExcelColumType.STRING)
    private String institution;

    @ExcleColumn(index = 8, javaType = ExcelColumType.DATE)
    private Date submitTime;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public String toString() {
        String dateString = "";
        if (null != submitTime) dateString = this.format.format(submitTime);
        return "ExamineeExcelModel{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", identityNum='" + identityNum + '\'' +
                ", mobile='" + mobile + '\'' +
                ", grade='" + grade + '\'' +
                ", school='" + school + '\'' +
                ", institution='" + institution + '\'' +
                ", submitTime=" + dateString +
                '}';
    }
}