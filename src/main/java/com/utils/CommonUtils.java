package com.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CommonUtils {

    public static <T> void con(List<T> list) {

        String decimalStr = BigDecimal.class.getTypeName();

        List<Field> fieldList = new ArrayList<>();

        if (list!=null && list.size()>0) {
            T t = list.get(0);
            Class clazz = t.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Type type = field.getGenericType();

                if (decimalStr.equals(type.getTypeName())) {
                    fieldList.add(field);
                }
            }
        }

        for (T t : list) {
            for (Field field : fieldList) {
                try {
                    BigDecimal o = (BigDecimal) field.get(t);
                    field.set(t, new BigDecimal("3.00"));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
