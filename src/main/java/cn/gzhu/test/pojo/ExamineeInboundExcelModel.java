package cn.gzhu.test.pojo;

import cn.gzhu.test.anno.ExcleColumn;
import cn.gzhu.test.anno.ExcleSheet;
import cn.gzhu.test.constant.ExcelColumType;
import lombok.Data;

import java.util.Date;

/**
 * 描述：考生excel模型
 */
@Data
@ExcleSheet(templateFileName = "/excel/src.xlsx", exportFileName = "target", startIndex = 1)
public class ExamineeInboundExcelModel {

    @ExcleColumn(index = 0, javaType = ExcelColumType.INTEGER)
    private Integer no;

    @ExcleColumn(index = 1, javaType = ExcelColumType.STRING)
    private String name;

    @ExcleColumn(index = 2, javaType = ExcelColumType.STRING)
    private String sex;

    @ExcleColumn(index = 3, javaType = ExcelColumType.STRING)
    private String identityNum;

    @ExcleColumn(index = 4, javaType = ExcelColumType.STRING)
    private String mobile;

    @ExcleColumn(index = 5, javaType = ExcelColumType.STRING)
    private String grade;

    @ExcleColumn(index = 6, javaType = ExcelColumType.STRING)
    private String school;

    @ExcleColumn(index = 7, javaType = ExcelColumType.STRING)
    private String institution;

    @ExcleColumn(index = 8, javaType = ExcelColumType.DATE, dateFormat = "yyyy-MM-dd HH:mm")
    private Date submitTime;

    @ExcleColumn(index = 9, javaType = ExcelColumType.STRING)
    private String account;

}