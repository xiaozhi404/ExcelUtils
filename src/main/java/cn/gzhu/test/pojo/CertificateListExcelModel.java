package cn.gzhu.test.pojo;

import cn.gzhu.test.anno.ExcleColumn;
import cn.gzhu.test.anno.ExcleSheet;
import cn.gzhu.test.constant.ExcelColumType;
import lombok.Data;

/**
 * 描述：证书名单实体类
 */
@Data
@ExcleSheet(startIndex = 5, importBlankRow = false)
public class CertificateListExcelModel {

    /**
     * 序号
     */
    @ExcleColumn(index = 0, javaType = ExcelColumType.INTEGER)
    private Integer sNum;

    /**
     * 获奖人名
     */
    @ExcleColumn(index = 1, javaType = ExcelColumType.STRING)
    private String name;

    /**
     * 考号
     */
    @ExcleColumn(index = 2, javaType = ExcelColumType.INTEGER)
    private Integer testNum;

    /**
     * 指导老师
     */
    @ExcleColumn(index = 3, javaType = ExcelColumType.STRING)
    private String teacherName;

    /**
     * 学校
     */
    @ExcleColumn(index = 4, javaType = ExcelColumType.STRING)
    private String schoolName;

}