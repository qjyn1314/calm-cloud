package com.calm.gen.util;

import com.calm.common.exception.CalmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;


/**
 * 反射工具类
 */
public class FieldUtil {

    private static final Logger logger = LoggerFactory.getLogger(FieldUtil.class);

    public static void setValue(final Field field, final Object object, final String newValue) throws IllegalAccessException {
        if (object == null || field == null) {
            throw new CalmException("目标对象与字段不能为空！");
        }
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        // 获取字段类型
        Class<?> type = field.getType();
        String simpleName = type.getSimpleName();
        if (newValue == null || newValue.length() == 0) {
            field.set(object, null);
            return;
        }
        switch (simpleName) {
            case TypeSimpleNameConstant.TYPE_STRING:
                field.set(object, newValue);
                break;
            case TypeSimpleNameConstant.TYPE_INTEGER:
            case TypeSimpleNameConstant.TYPE_INT:
                field.set(object, Integer.valueOf(newValue));
                break;
            case TypeSimpleNameConstant.TYPE_DOUBLE:
                field.set(object, Double.valueOf(newValue));
                break;
            case TypeSimpleNameConstant.TYPE_DATE:
                field.set(object, DateTimeKit.parse(newValue));
                break;
            case TypeSimpleNameConstant.TYPE_BIGDECIMAL:
                field.set(object, new BigDecimal(newValue));
                break;
            default:
                field.set(object, newValue);
                break;
        }
    }

    /***
     * 将字段值转换成字符串，如果没有值返回空
     * @param field
     * @param entity
     * @return
     */
    public static String valueToString(Field field, Object entity) {
        if (field == null) {
            logger.error("FieldUtil-valueToString ：： Field must not null！entity : {}", entity);
            return null;
        }
        String fieldVal = null;
        field.setAccessible(true);
        String simpleName = field.getType().getSimpleName();
        try {
            if (TypeSimpleNameConstant.TYPE_DATE.equals(simpleName)) {
                Object date = field.get(entity);
                if (date != null) {
                    fieldVal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                }
            } else {
                fieldVal = field.get(entity).toString();
            }
        } catch (IllegalAccessException e) {
            logger.error("FieldUtil-valueToString ：：Field 转换值为字符串失败！Field : {}, entity : {}", field, entity, e);
        }
        return fieldVal;
    }

    public static class TypeSimpleNameConstant {
        public static final String TYPE_STRING = "String";
        public static final String TYPE_INTEGER = "Integer";
        public static final String TYPE_INT = "int";
        public static final String TYPE_DOUBLE = "Double";
        public static final String TYPE_DATE = "Date";
        public static final String TYPE_BIGDECIMAL = "BigDecimal";
    }

}
