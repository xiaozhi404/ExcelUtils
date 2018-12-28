package cn.gzhu.test.utils;


import cn.gzhu.test.anno.ExcleColumn;
import cn.gzhu.test.anno.ExcleSheet;
import cn.gzhu.test.exception.NotExcelException;
import cn.gzhu.test.exception.NullFileException;
import cn.gzhu.test.exception.RowNumBeyondException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils {

    public static <T> List<T> covertExcel2Model(FileInputStream file, Class<T> clazz) throws Exception {
        ExcleSheet excleSheet = clazz.getAnnotation(ExcleSheet.class);
        List result = new ArrayList<T>();
        Workbook wb = WorkbookFactory.create(file);
        Sheet sheet = wb.getSheetAt(0);

        if ((sheet.getLastRowNum()+1 - excleSheet.startIndex()) > excleSheet.maxRowNum()) {
            throw new RowNumBeyondException("导入的行数超过设置的最大值");
        }

        for (Row row : sheet) {
            if (row.getRowNum() < excleSheet.startIndex()) {
                continue;
            }
            T t = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                ExcleColumn excleColumn = field.getAnnotation(ExcleColumn.class);
                int index = excleColumn.index();
                Cell cell = row.getCell(index);
                switch (excleColumn.javaType()) {
                    case STRING:
                        String stringValue = cell.getStringCellValue();
                        MyBeanUtils.setProperty(t, field.getName(), stringValue);
                        break;
                    case INTEGER:
                        Double intValue = cell.getNumericCellValue();
                        MyBeanUtils.setProperty(t, field.getName(), intValue.intValue());
                        break;
                }
            }
            if (excleSheet.importBlankRow()) {
                result.add(t);
            } else {
                if (!isBlankRow(row)) {
                    result.add(t);
                }
            }
        }
        return result;
    }

    public static Boolean isBlankRow(Row row) {
        int cellNum = row.getPhysicalNumberOfCells();
        for (int i = 0; i < cellNum; ++i) {
            Cell c = row.getCell(i);
            if (c != null && c.getCellTypeEnum() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }

    public static void checkExcleFile(File file) {
        if (null == file) {
            throw new NullFileException();
        }
        //判断是否为excel文件
        String filename = "";
        if (!filename.endsWith(".xls") && !filename.endsWith(".xlsx")) {
            throw new NotExcelException();
        }
    }
}
