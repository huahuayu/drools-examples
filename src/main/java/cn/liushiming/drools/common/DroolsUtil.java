package cn.liushiming.drools.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description
 * @Author shiming
 * @Date 2020/3/26 4:41 PM
 * @Version 1.0
 **/
public final class DroolsUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(DroolsUtil.class);

    /**
     * 比较数字a是否等于b
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean numberEqual(Object a, Object b) {
        BigDecimal bigA = objectToBigDecimal(a);
        BigDecimal bigB = objectToBigDecimal(b);
        return bigA.equals(bigB);
    }

    /**
     * 比较数字a是否大于b
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean numberGreaterThan(Object a, Object b) {
        BigDecimal bigA = objectToBigDecimal(a);
        BigDecimal bigB = objectToBigDecimal(b);
        return bigA.compareTo(bigB) == 1;
    }

    /**
     * 比较数字a是否小于b
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean numberLessThan(Object a, Object b) {
        BigDecimal bigA = objectToBigDecimal(a);
        BigDecimal bigB = objectToBigDecimal(b);
        return bigA.compareTo(bigB) == -1;
    }

    /**
     * 比较数字a是否大于等于b
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean numberGreaterOrEqualThan(Object a, Object b) {
        return !numberLessThan(a, b);
    }

    /**
     * 比较数字a是否小于等于b
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean numberLessOrEqualThan(Object a, Object b) {
        return !numberGreaterThan(a, b);
    }

    /**
     * 比较日期a是否大于日期b
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean dateGreaterThan(Object a, Object b) {
        return dateCompare(a, b) > 0;
    }

    /**
     * 比较日期a是否小于日期b
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean dateLessThan(Object a, Object b) {
        return dateCompare(a, b) < 0;
    }

    /**
     * 比较日期a是否等于日期b
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean dateEquals(Object a, Object b) {
        return dateCompare(a, b) == 0;
    }

    /**
     * 比较日期a是否大于等于日期b
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean dateGreaterThanOrEqual(Object a, Object b) {
        return dateCompare(a, b) > 0 || dateCompare(a, b) == 0;
    }

    /**
     * 比较日期a是否小于等于日期b
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean dateLessThanOrEqual(Object a, Object b) {
        return dateCompare(a, b) < 0 || dateCompare(a, b) == 0;
    }

    /**
     * Objcet转换为BigDecimal
     *
     * @param value 数字类型的object
     * @return BigDecimal数字
     */
    private static BigDecimal objectToBigDecimal(Object value) {
        BigDecimal ret = null;
        if (value != null) {
            if (value instanceof BigDecimal) {
                ret = (BigDecimal) value;
            } else if (value instanceof String) {
                try {
                    ret = new BigDecimal((String) value);
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("字符串 " + value + " 无法转换为数字");
                }
            } else if (value instanceof BigInteger) {
                ret = new BigDecimal((BigInteger) value);
            } else if (value instanceof Number) {
                ret = new BigDecimal(value.toString());
            } else {
                throw new ClassCastException("无法转换 [" + value + "] from class " + value.getClass() + " into a BigDecimal.");
            }
        } else {
            throw new IllegalArgumentException("非法输入，null值无法转换为数字");
        }

        return ret;
    }

    /**
     * 比较两个日期的大小
     *
     * @param a
     * @param b
     * @return 0-相等，大于0 - a日期>b日期，小于0 - a日期<b日期
     */
    private static Integer dateCompare(Object a, Object b) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date aDate = null;
        Date bDate = null;
        try {
            if (a instanceof String) {
                aDate = simpleDateFormat.parse(a.toString());
            } else {
                aDate = (Date) a;
            }
            if (b instanceof String) {
                bDate = simpleDateFormat.parse(b.toString());
            } else {
                bDate = (Date) b;
            }
            return aDate.compareTo(bDate);
        } catch (ParseException e) {
            LOGGER.error("日期格式不对：{}", e.getMessage());
        }

        return null;
    }

}
