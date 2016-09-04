package bgg.com.myapplication.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 检验手机号码、邮箱
 *
 * @author huangbin
 */
public class ValidationUtil {
    private static final String TAG = "ValidationUtil";
    /**
     * 邮箱格式
     */
    public static final String EMAIL_EXPRESSION = "^([&'+\\-\\d=A-Z_a-z]+(?:\\.[&'+\\-\\d=A-Z_a-z]+)*)@((?:[\\dA-Za-z](?:[-\\dA-Za-z]*[\\dA-Za-z])?\\.)+[A-Za-z][-A-Za-z]*[A-Za-z])$";
    /**
     * 字母和数字、字符
     */
    public static final String PWD_EXPRESSION = "^[A-Za-z0-9!@#$%^&*]{6,12}$";
    /**
     * 只能是字母和数字下划线，且不能以数字打头
     */
    public static final String USERNAME_EXPRESSION = "^[a-zA-Z_][a-zA-Z0-9_]{4,24}$";
    /**
     * 中文
     */
    public static final String CHINESE_EXPRESSION = "^[\u4e00-\u9fa5]+$";
    /**
     * 汉字、英文
     */
    public static final String CN_AND_EN_EXPRESSION = "^[\u4e00-\u9fa5a-zA-Z]*$";
    /**
     * 汉字、英文、数字
     */
    public static final String CN_AND_EN_AND_NUM_EXPRESSION = "^[\u4e00-\u9fa5a-zA-Z\\d]*$";

    /**
     * 手机号码，不以0开头的11位数字
     */
    private static final Pattern PHONE_NUMBER = Pattern.compile("^[1-9][0-9]{10}$");

    /**
     * 可以输固话和手机号码的地方：3-13位数字加符号组合，符号包括"-""/"
     */
    private static final Pattern CN_TEL_NUMBER = Pattern.compile("^[-/0-9]{3,13}$");

    /**
     * 匹配手机号码的格式
     *
     * @param phoneNumber 手机号码
     * @return 是否匹配
     */
    public static boolean matchesPhoneNumber(CharSequence phoneNumber) {
        return PHONE_NUMBER.matcher(phoneNumber).matches();
    }

    /**
     * 匹配固定电话格式
     *
     * @param telNumber 固定电话
     * @return 是否匹配
     */
    public static boolean matchesTelNumber(CharSequence telNumber) {
        return CN_TEL_NUMBER.matcher(telNumber).matches();
    }

    /**
     * 匹配固定电话或手机号码的格式
     *
     * @param number 号码
     * @return 是否匹配
     */
    public static boolean matchesTelOrPhoneNumber(CharSequence number) {
        return matchesTelNumber(number) || matchesPhoneNumber(number);
    }

    /**
     * 传入"校验规则"和"待校验的string"来匹配校验的通用方法
     *
     * @param inputString 待校验的字符串
     * @param expression  正则表达式（校验规则）
     * @return true 匹配成功，否者false
     */
    public static boolean checkStr(String inputString, String expression) {
        if (inputString == null || inputString.equals("")) {
            return false;
        }
        if (expression == null || expression.equals("")) {
            return false;
        }
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputString);
        boolean flag = matcher.matches();
        return flag;
    }
}
