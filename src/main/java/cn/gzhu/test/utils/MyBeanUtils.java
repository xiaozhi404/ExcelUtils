package cn.gzhu.test.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public class MyBeanUtils {

    public static Object getProperty(Object obj, String propertyName) throws Exception{
        if (null == obj) {
            return null;
        }
        PropertyDescriptor descriptor = new PropertyDescriptor(propertyName, obj.getClass());
        Method readMethod = descriptor.getReadMethod();
        return readMethod.invoke(obj);
    }

    public static void setProperty(Object obj, String propertyName, Object value) throws Exception{
        if (null == obj) {
            return;
        }
        PropertyDescriptor descriptor = new PropertyDescriptor(propertyName, obj.getClass());
        Method writeMethod = descriptor.getWriteMethod();
        writeMethod.invoke(obj, value);
    }
}
