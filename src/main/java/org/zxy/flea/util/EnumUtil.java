package org.zxy.flea.util;

import org.zxy.flea.converter.EnumInterface;

public class EnumUtil {

    public static <T extends EnumInterface> T getByCode(Integer code, Class<T> enumClass) {
        for (T each: enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }

        return null;
    }
}
