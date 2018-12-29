# ExcelUtils
注解式的通用Excel导入工具类

For example：将如下excel转成model list
  
![image](https://github.com/xiaozhi404/readme_pic/raw/master/1.png)

一.绑定Excel模型
```java  
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
```
二.调用工具类进行导入
```java
public class MyApp {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(new File("/Users/xiaozhi/Desktop/blank.xlsx"));
        List<CertificateListExcelModel> certificateListExcelModels = ExcelUtils.covertExcel2Model(fileInputStream, CertificateListExcelModel.class);
        for (CertificateListExcelModel a : certificateListExcelModels) {
            System.out.println(a);
        }

    }
}
```
三.会进行如下通用的异常校验，抛出对应的异常.
```java
NotExcelException
NullFileException
RowNumBeyondException
```

