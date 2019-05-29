package cn.gzhu.test.pojo;

import cn.gzhu.test.anno.ExcleColumn;
import cn.gzhu.test.constant.ExcelColumType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 描述：考生记录excel标题头
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamineeGradeExcelHeadModel {

    @ExcleColumn(index = 13, titleRow = 0, javaType = ExcelColumType.MERGEHEAD)
    private Map<String, Integer> mergeHeads;

    @ExcleColumn(index = 13, titleRow = 1, javaType = ExcelColumType.HEAD)
    private List<String> heads;

}