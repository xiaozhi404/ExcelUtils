package cn.gzhu.test.utils;


import cn.gzhu.test.anno.ExcleColumn;
import cn.gzhu.test.anno.ExcleColumnVerify;
import cn.gzhu.test.anno.ExcleSheet;
import cn.gzhu.test.exception.NotExcelException;
import cn.gzhu.test.exception.NullFileException;
import cn.gzhu.test.exception.RowNumBeyondException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ExcelUtils {

    public static <T> List<T> covertExcel2Model(FileInputStream file, Class<T> clazz) throws Exception {

        ExcleSheet excleSheet = clazz.getAnnotation(ExcleSheet.class);
        List result = new ArrayList<T>();
        Workbook wb = WorkbookFactory.create(file);
        Sheet sheet = wb.getSheetAt(0);

        if ((sheet.getLastRowNum()+1 - excleSheet.startIndex()) > excleSheet.maxRowNum()) {
            throw new RowNumBeyondException("导入的行数超过最大值:" + excleSheet.maxRowNum());
        }

        //初始化标题名和下标
        HashMap<Integer, String> indexWithTitle = new HashMap<>();
        Row titleRow = sheet.getRow(excleSheet.titleIndex());
        int titleCellNum = titleRow.getPhysicalNumberOfCells();
        for (int i = 0; i < titleCellNum; ++i) {
            indexWithTitle.put(i, titleRow.getCell(i).getStringCellValue());
        }

        for (Row row : sheet) {
            if (row.getRowNum() < excleSheet.startIndex() || isBlankRow(excleSheet.ignoreOnlyHaveNoRow(), row)) {
                continue;
            }
            T t = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                ExcleColumn excleColumn = field.getAnnotation(ExcleColumn.class);
                if (null == excleColumn) continue;
                int index = excleColumn.index();
                Cell cell = row.getCell(index);
                //excel导入时数字采用科学计数法，需要还原
                DecimalFormat format = new DecimalFormat("0");
                switch (excleColumn.javaType()) {
                    case STRING:
                        String stringVal;
                        if (CellType.NUMERIC == cell.getCellTypeEnum()) {
                            stringVal = format.format(cell.getNumericCellValue());
                        } else {
                            stringVal = cell.getStringCellValue();
                        }
                        MyBeanUtils.setProperty(t, field.getName(), stringVal);
                        break;
                    case INTEGER:
                        Integer intVal = Integer.parseInt(format.format(cell.getNumericCellValue()));
                        MyBeanUtils.setProperty(t, field.getName(), intVal);
                        break;
                    case LONG:
                        Long longVal = Long.parseLong(format.format(cell.getNumericCellValue()));
                        MyBeanUtils.setProperty(t, field.getName(), longVal);
                        break;
                    case DOUBLE:
                        Double doubleVal = Double.parseDouble(format.format(cell.getNumericCellValue()));
                        MyBeanUtils.setProperty(t, field.getName(), doubleVal);
                        break;
                    case DATE:
                        Date dateVal = cell.getDateCellValue();
                        MyBeanUtils.setProperty(t, field.getName(), dateVal);
                        break;
                }
                //校验
                ExcleColumnVerify excleColumnVerify = field.getAnnotation(ExcleColumnVerify.class);
                if (null != excleColumnVerify) {
                    ExcelColumnVerifyUtils.verity(MyBeanUtils.getProperty(t, field.getName()), row.getRowNum(), indexWithTitle.get(index), excleColumnVerify);
                }
            }
            result.add(t);
        }
        return result;
    }

    public static Boolean isBlankRow(int noIndex, Row row) {
        int cellNum = row.getPhysicalNumberOfCells();
        for (int i = 0; i < cellNum; ++i) {
            Cell c = row.getCell(i);
            if (i != noIndex && c != null && c.getCellTypeEnum() != CellType.BLANK) {
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
