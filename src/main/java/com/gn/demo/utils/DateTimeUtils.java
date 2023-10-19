package com.gn.demo.utils;/*
 * @Description: 时间工具类
 * @Author: GuiNing
 * @Date: 2023/4/25 15:41
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class DateTimeUtils {


    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_PATTERN2 = "yyyyMMddHHmmss";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String MONTH_PATTERN = "yyyy-MM";

    public static String formatDate(Date date, String pattern) {
        if (Objects.isNull(pattern)) {
            pattern = DateTimeUtils.DATE_PATTERN;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);

    }
}
