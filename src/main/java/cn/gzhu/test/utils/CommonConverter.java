package cn.gzhu.test.utils;


import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 描述：通用vo,dto转化器
 * 作用：将po转化成vo, 将dto转化成po,po与vo的list转换
 * 规范：1.po和vo，dto的属性名称一致，类型一致
 *      2.日期类型处理方法：po的日期类型为Date, 与前台互相交流的单位为秒
 *      3.po的属性数量>=vo的属性数量 dto的属性数量<=vo
 *      4.若不符合规范3，则需要在程序进行控制
 * @author caixianzhi
 */
public class CommonConverter {

    public static <T> T convertPoToVO(Object po, Class<T> voClass) {
        try {
            if (null == po) {
                return null;
            }

            T vo = voClass.newInstance();
            //1.获取vobean描述器
            BeanInfo voBeanInfo = Introspector.getBeanInfo(vo.getClass());
            PropertyDescriptor[] voPropertyDescriptors = voBeanInfo.getPropertyDescriptors();
            //2.将po的属性设置在vo中
            for (PropertyDescriptor voProp: voPropertyDescriptors) {
                String voPropName = voProp.getName();
                //排除类本身有的class属性
                if (!"class".equals(voPropName)) {
                    //获取po对应的属性描述器,若属性在po没有,就不设置在vo
                    PropertyDescriptor poProp;
                    try {
                        poProp = new PropertyDescriptor(voPropName, po.getClass());
                    } catch (Exception e) {
                        continue;
                    }
                    //将po属性的值设置在vo中
                    Object poPropVal = poProp.getReadMethod().invoke(po);
                    //判断类型是否相同
                    Class<?> poPropType = poProp.getPropertyType();
                    Class<?> voPropType = voProp.getPropertyType();
                    //若不相同则需要转化
                    if (!poPropType.equals(voPropType) && null!= poPropVal) {
                        //若是date,则需要转成long秒
                        if (poPropType.equals(Date.class)) {
                            Date date = (Date)poPropVal;
                            poPropVal = date.getTime()/1000;
                        }
                        //LocalDateTime类型,则需要转成long秒
                        if (poPropType.equals(LocalDateTime.class)) {
                            LocalDateTime localDateTime = (LocalDateTime)poPropVal;
                            poPropVal = localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
                        }
                        //。。。。其它类型待扩展
                    }
                    //将po值设置进vo
                    voProp.getWriteMethod().invoke(vo, poPropVal);
                }
            }
            return vo;
        } catch (Exception e) {
           return null;
        }

    }

    public static <T> List<T> convertPoListToVOList(List poList, Class<T> voClass) {

        try {
            if (null == poList || poList.size()==0) {
                return null;
            }
            List<T> voList = new ArrayList<T>();
            for (Object po : poList) {
                T vo = convertPoToVO(po, voClass);
                voList.add(vo);
            }
            return voList;
        } catch (Exception e) {
            return null;
        }

    }

    public static <T> T convertDTOToPo(Object dto, Class<T> poClass) {
        try {
            if (null == dto) {
                return null;
            }

            T po = poClass.newInstance();
            //1.获取dto的属性描述器
            BeanInfo dtoBeanInfo = Introspector.getBeanInfo(dto.getClass());
            PropertyDescriptor[] dtoPropertyDescriptors = dtoBeanInfo.getPropertyDescriptors();

            //2.将dto的属性设置在po中
            for (PropertyDescriptor dtoProp: dtoPropertyDescriptors) {
                String dtoPropName = dtoProp.getName();
                //排除类本身有的class属性
                if (!"class".equals(dtoPropName)) {
                    //获取po对应的属性描述器,若dto的属性在po没有就不设置
                    PropertyDescriptor poProp;
                    try {
                        poProp = new PropertyDescriptor(dtoPropName, poClass);
                    } catch (Exception e) {
                        continue;
                    }
                    //将dto属性的值设置在po中
                    Object dtoPropVal = dtoProp.getReadMethod().invoke(dto);
                    //判断类型是否相同
                    Class<?> poPropType = poProp.getPropertyType();
                    Class<?> dtoPropType = dtoProp.getPropertyType();
                    //若不相同则需要转化
                    if (!poPropType.equals(dtoPropType)) {
                        //若是date,则需要将long秒转成date
                        if (poPropType.equals(Date.class)) {
                            Date date = new Date((Long) dtoPropVal*1000);
                            dtoPropVal = date;
                        }
                        //。。。。其它类型待扩展
                    }
                    //将dto值设置进po
                    poProp.getWriteMethod().invoke(po, dtoPropVal);
                }
            }
            return po;
        } catch (Exception e) {
            return null;
        }

    }

    public static <T> List<T> convertDTOListToPoList(List dtoList, Class<T> poClass) {
        try {
            if (null == dtoList || dtoList.size()==0) {
                return null;
            }
            List<T> poList = new ArrayList<T>();
            for (Object dto : dtoList) {
                T po = convertDTOToPo(dto, poClass);
                poList.add(po);
            }
            return poList;
        } catch (Exception e) {
            return null;
        }
    }


}
