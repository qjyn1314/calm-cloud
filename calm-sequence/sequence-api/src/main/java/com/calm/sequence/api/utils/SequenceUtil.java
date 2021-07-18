package com.calm.sequence.api.utils;

import cn.hutool.core.date.DateUtil;

import java.text.DecimalFormat;
import java.text.Format;

/**
 * <p>
 * explain:
 * </p>
 *
 * @author wangjunming
 * @since 2021/4/10 14:08
 */
public class SequenceUtil {

    public static final String DATE_FORMAT = "yyyyMMdd";
    public static final Format NUMBER_FORMAT = new DecimalFormat("0000");
    public static final Format NUMBER_FORMAT_00 = new DecimalFormat("000000");
    public static final Format NUMBER_FORMAT_0000 = new DecimalFormat("00000000");
    public static final String dateTimeFormat = "yyyyMMddHH";
    public static final String dateTimeHourFormat = "yyyyMMddHHmm";
    public static final String dateTimeHourMinuteFormat = "yyyyMMddHHmmss";

    public static String getDate() {
        return DateUtil.format(DateUtil.date(), dateTimeHourFormat);
    }

    public static String getSequenceCodeByDate(Long sequence, Integer length) {
        return getSequenceCodeByDateLength(sequence, length);
    }


    public static String getSequenceCodeByDateLength(Long sequence, Integer length) {
        switch (length){
            case 6:return getDate() + NUMBER_FORMAT_00.format(sequence);
            case 8:return getDate() + NUMBER_FORMAT_0000.format(sequence);
            default:return getDate() + NUMBER_FORMAT.format(sequence);
        }
    }



}
