package cn.gzhu.test;

import cn.gzhu.test.pojo.CertificateListExcelModel;
import cn.gzhu.test.utils.ExcelUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public class MyApp {


    public static void main(String[] args) throws Exception {

        FileInputStream fileInputStream = new FileInputStream(new File("/Users/xiaozhi/Desktop/blank.xlsx"));

        List<CertificateListExcelModel> certificateListExcelModels = ExcelUtils.covertExcel2Model(fileInputStream, CertificateListExcelModel.class);
        for (CertificateListExcelModel a : certificateListExcelModels) {
            System.out.println(a);
        }

    }

}
