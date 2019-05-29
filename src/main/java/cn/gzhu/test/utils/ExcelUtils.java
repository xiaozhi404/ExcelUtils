package cn.gzhu.test.utils;


import cn.gzhu.test.anno.ExcleColumn;
import cn.gzhu.test.anno.ExcleColumnVerify;
import cn.gzhu.test.anno.ExcleSheet;
import cn.gzhu.test.constant.ExcelColumType;
import cn.gzhu.test.exception.NotExcelException;
import cn.gzhu.test.exception.NullFileException;
import cn.gzhu.test.exception.RowNumBeyondException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
        //唯一性值，用index+字段的string值进行存储
        List<String> onlyContainer = new ArrayList<>();
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
                    Object propVal = MyBeanUtils.getProperty(t, field.getName());
                    ExcelColumnVerifyUtils.verity(propVal, row.getRowNum()+1, indexWithTitle.get(index), excleColumnVerify, onlyContainer, index);
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

    /**
     *导出excel
     */
    public static<T> void export(List<T> modelList, Object headModel) throws Exception {
        if (null == modelList || modelList.size() < 1) {
            return;
        }
        Class<T> clazz = (Class<T>) modelList.get(0).getClass();
        //获取模版
        ExcleSheet excleSheet = clazz.getAnnotation(ExcleSheet.class);
        String templateFileName = excleSheet.templateFileName();
        InputStream resourceAsStream = ExcelUtils.class.getResourceAsStream(templateFileName);
        Workbook wb = WorkbookFactory.create(resourceAsStream);
        Sheet sheet = wb.getSheetAt(0);
        //获取单元格样式
        CellStyle cellStyle = sheet.getRow(excleSheet.startIndex()).getCell(0).getCellStyle();
        Integer counter = excleSheet.startIndex();
        Map<Integer, DateFormat> indexWithDateFormat = new HashMap<>();
        //id生成器
        Double idCounter = 1d;



        for (T t: modelList) {
            //从最后一行开始写
            Row row = sheet.createRow(counter++);
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                ExcleColumn excleColumn = field.getAnnotation(ExcleColumn.class);
                if (null == excleColumn) continue;
                int index = excleColumn.index();
                //初始化时间字段的格式化器  index:dateFormat
                if (!"".equals(excleColumn.dateFormat())) {
                    indexWithDateFormat.put(index, new SimpleDateFormat("yyyy-MM-dd"));
                }
                Cell cell;
                switch (excleColumn.javaType()) {
                    case NONE:
                        cell = row.createCell(index);
                        cell.setCellStyle(cellStyle);
                        break;
                    case LIST:
                        String name = field.getName();
                        List<String> list = (ArrayList<String>) MyBeanUtils.getProperty(t, name);
                        for (int i = 0; i < list.size(); ++i) {
                            cell = row.createCell(index++);
                            cell.setCellStyle(cellStyle);
                            cell.setCellValue(list.get(i));
                        }
                        break;
                    case IDENTITY:
                            cell = row.createCell(index);
                            cell.setCellStyle(cellStyle);
                            cell.setCellValue(idCounter++);
                        break;
                        default:
                            cell = row.createCell(index);
                            cell.setCellStyle(cellStyle);
                            String fieldName = field.getName();
                            Object o = MyBeanUtils.getProperty(t, fieldName);
                            if (null== o) break;
                            String value;
                            if (excleColumn.javaType().equals(ExcelColumType.DATE)) {
                                DateFormat dateFormat = indexWithDateFormat.get(index);
                                value = dateFormat.format((Date) o);
                            }else {
                                value = o.toString();
                            }
                            cell.setCellValue(value);
                        break;
                }

            }
        }
        wb.write(new FileOutputStream("/Users/xiaozhi/Desktop/"+excleSheet.exportFileName() + excleSheet.exName()));
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
