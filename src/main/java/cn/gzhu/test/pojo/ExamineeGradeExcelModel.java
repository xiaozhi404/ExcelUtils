package cn.gzhu.test.pojo;

import cn.gzhu.test.anno.ExcleColumn;
import cn.gzhu.test.anno.ExcleSheet;
import cn.gzhu.test.constant.ExcelColumType;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 描述：考生excel模型
 */
@Data
@ExcleSheet(templateFileName = "/excel/导出考生成绩测试.xlsx", exportFileName = "target2", startIndex = 2)
public class ExamineeGradeExcelModel {

    @ExcleColumn(index = 0, javaType = ExcelColumType.IDENTITY)
    private Integer no;

    @ExcleColumn(index = 1, javaType = ExcelColumType.STRING)
    private String account;

    @ExcleColumn(index = 2, javaType = ExcelColumType.STRING)
    private String name;

    @ExcleColumn(index = 3, javaType = ExcelColumType.STRING)
    private String sex;

    @ExcleColumn(index = 4, javaType = ExcelColumType.STRING)
    private String identityNum;

    @ExcleColumn(index = 5, javaType = ExcelColumType.STRING)
    private String mobile;

    @ExcleColumn(index = 6, javaType = ExcelColumType.STRING)
    private String grade;

    @ExcleColumn(index = 7, javaType = ExcelColumType.STRING)
    private String school;

    @ExcleColumn(index = 8, javaType = ExcelColumType.STRING)
    private String institution;

    @ExcleColumn(index = 9, javaType = ExcelColumType.DATE, dateFormat = "yyyy-MM-dd HH:mm")
    private Date startTime;

    @ExcleColumn(index = 10, javaType = ExcelColumType.DATE, dateFormat = "yyyy-MM-dd HH:mm")
    private Date submitTime;

    @ExcleColumn(index = 11, javaType = ExcelColumType.STRING)
    private String timeCost;

    @ExcleColumn(index = 12, javaType = ExcelColumType.NONE)
    private String totalScore;

    //若是list，则从起始位置开始赋值
    @ExcleColumn(index = 13, javaType = ExcelColumType.LIST)
    private List<String> scores;

}