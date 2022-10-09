package com.assignment.jitpay.jitpayassignment.utils;

import java.util.List;

public class StringUtils {

    public static boolean isNullOrEmptyString(String string) {
        if (null == string || "".equals(string) || "null".equals(string))
            return true;
        else
            return false;
    }
    public static boolean isNull(Object object) {
        if (null == object) {
            return true;
        }

        return false;
    }

    public static boolean isNullOrEmptyList(List list)

    {
        if ((list == null) || (list.size() == 0)) {
            return true;
        }
        return false;
    }
}
