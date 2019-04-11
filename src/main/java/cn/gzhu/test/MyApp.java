package cn.gzhu.test;

import cn.gzhu.test.service.BatchListWrapper;

import java.util.ArrayList;

public class MyApp {


    public static void main(String[] args) throws Exception {

//        FileInputStream fileInputStream = new FileInputStream(new File("/Users/xiaozhi/Desktop/导入考生信息模版.xlsx"));
//
//        List<ExamineeExcelModel> ExamineeExcelModels = ExcelUtils.covertExcel2Model(fileInputStream, ExamineeExcelModel.class);
//        for (ExamineeExcelModel a : ExamineeExcelModels) {
//            System.out.println(a);
//        }

        ArrayList<Integer> ids = new ArrayList<>();
        for (int i = 0; i < 100; ++i) {
            ids.add(i);
        }
        BatchListWrapper<Integer> re = new BatchListWrapper<>(ids, 0);
        while (re.hasNextBatch()) {
            System.out.println(re.next());
            System.out.println("=================");
        }


    }

}
