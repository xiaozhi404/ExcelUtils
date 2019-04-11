package cn.gzhu.test;

import cn.gzhu.test.pojo.ExamineeExcelModel;
import cn.gzhu.test.utils.ExcelUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public class MyApp {


    public static void main(String[] args) throws Exception {

        FileInputStream fileInputStream = new FileInputStream(new File("/Users/xiaozhi/Desktop/导入考生信息模版.xlsx"));

        List<ExamineeExcelModel> ExamineeExcelModels = ExcelUtils.covertExcel2Model(fileInputStream, ExamineeExcelModel.class);
        for (ExamineeExcelModel a : ExamineeExcelModels) {
            System.out.println(a);
        }


    }

}
